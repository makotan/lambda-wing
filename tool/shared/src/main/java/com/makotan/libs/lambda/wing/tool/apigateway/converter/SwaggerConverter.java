package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.ApiInfoConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.RestMethodConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.RestResourceConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/11/21.
 */
public class SwaggerConverter {
    Logger log = LoggerFactory.getLogger(getClass());

    List<ClassToSwaggerConverter> classToSwaggerConverters = new ArrayList<>();
    List<MethodToSwaggerConverter> methodToSwaggerConverters = new ArrayList<>();

    public SwaggerConverter() {
        classToSwaggerConverters.add(new ApiInfoConverter());
        classToSwaggerConverters.add(new RestResourceConverter());
        methodToSwaggerConverters.add(new RestMethodConverter());
    }

    public Either<SwaggerConvertErrors,Swagger> convert(SwaggerConvertInfo info) {
        Swagger swagger = new Swagger();
        Either<SwaggerConvertErrors,Swagger> either = Either.right(swagger);
        ConvertContext context = new ConvertContext();

        Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .addClassLoader(getClass().getClassLoader())
                        .filterInputsBy(name -> name.startsWith(info.basePackage))
                        .forPackages(info.basePackage)
                        .addScanners(new TypeAnnotationsScanner())
                );


        classToSwaggerConverters.forEach(conv -> {
            Class<? extends Annotation> anno = conv.scanAnnotation();
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(anno);
            if (typesAnnotatedWith.isEmpty()) {
                return;
            }
            ConverterUtils.reduce(typesAnnotatedWith.stream() , either
                    ,(swgTypeInEither , type) -> {
                        Either<SwaggerConvertErrors, Swagger> swgTypeOutEither = conv.convert(type, info, swgTypeInEither, context);
                        if (conv.useMethodScan()) {
                            return ConverterUtils.reduce(methodToSwaggerConverters.stream()
                                    , swgTypeOutEither
                                    , (swgMethodInEither , mconv) -> {
                                        Set<Method> methods = Arrays.stream(type.getMethods()).filter(m -> m.getAnnotation(mconv.scanAnnotation()) != null).collect(Collectors.toSet());
                                        return ConverterUtils.reduce(methods.stream() ,
                                                swgMethodInEither,
                                                (swgMethodEither , method) -> {
                                                    return mconv.convert(type,method,info,swgMethodEither,context);
                                                });
                                    });
                        } else {
                            return swgTypeOutEither;
                        }
                    });
        });
        return Either.right(swagger);
    }

    public void outputFile(Swagger swagger , File swaggerFile) throws IOException {
        List<String> outList = Arrays.asList(Json.pretty(swagger));
        java.nio.file.Path path = swaggerFile.toPath();
        path.getParent().toFile().mkdirs();
        if (swaggerFile.exists()) {
            swaggerFile.delete();
        }
        java.nio.file.Path file = Files.createFile(path);
        Files.write(file , outList , Charset.defaultCharset(),  StandardOpenOption.WRITE);
    }

}
