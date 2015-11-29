package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.google.common.collect.Collections2;
import com.google.common.io.CharStreams;
import com.makotan.libs.lambda.wing.api_gw.core.*;
import com.makotan.libs.lambda.wing.core.LambdaHandler;
import com.makotan.libs.lambda.wing.core.exception.LambdaWingException;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.core.util.StringUtils;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.ApiInfoConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.RestMethodConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.RestResourceConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.model.AmazonApiGatewayAuth;
import com.makotan.libs.lambda.wing.tool.apigateway.model.AmazonApiGatewayIntegrationResponse;
import com.makotan.libs.lambda.wing.tool.apigateway.model.AmazonApigatewayIntegration;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Info;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefResponse;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
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
            typesAnnotatedWith.forEach(type -> {
                conv.convert(type, info , swagger,context);
                if (conv.useMethodScan()) {
                    methodToSwaggerConverters.forEach(mconv -> {
                        Set<Method> methods = Arrays.stream(type.getMethods()).filter(m -> m.getAnnotation(mconv.scanAnnotation()) != null).collect(Collectors.toSet());
                        methods.forEach(method -> {
                            mconv.convert(type,method,info,swagger,context);
                        });
                    });
                }
            });
        });
        return Either.right(swagger);
    }

}
