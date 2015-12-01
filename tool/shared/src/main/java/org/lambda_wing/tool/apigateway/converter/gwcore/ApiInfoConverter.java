package org.lambda_wing.tool.apigateway.converter.gwcore;

import org.lambda_wing.api_gw.core.ApiInfo;
import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.lambda.core.util.StringUtils;
import org.lambda_wing.tool.apigateway.converter.ClassToSwaggerConverter;
import org.lambda_wing.tool.apigateway.converter.ConvertContext;
import org.lambda_wing.tool.apigateway.converter.ConverterUtils;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertErrors;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Info;
import io.swagger.models.Swagger;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Created by makotan on 2015/11/29.
 */
public class ApiInfoConverter implements ClassToSwaggerConverter {

    @Override
    public Class<? extends Annotation> scanAnnotation() {
        return ApiInfo.class;
    }

    @Override
    public boolean useMethodScan() {
        return false;
    }

    @Override
    public Either<SwaggerConvertErrors,Swagger> convert(Class<?> klass, SwaggerConvertInfo info, Either<SwaggerConvertErrors, Swagger> swaggerEither, ConvertContext context) {
        if (swaggerEither.isLeft()) {
            return swaggerEither;
        }
        Swagger swagger = swaggerEither.getRight();
        ApiInfo apiInfo = klass.getAnnotation(ApiInfo.class);

        ConverterUtils.setCheckVal(swagger::basePath , apiInfo.basePath() , "" , info::getBasePath , StringUtils::isEmpty);

        Info swaggerInfo = new Info();
        ConverterUtils.setCheckVal(swaggerInfo::description , apiInfo.description() , "" , info::getDescription , StringUtils::isEmpty);
        ConverterUtils.setCheckVal(swaggerInfo::title , apiInfo.title() , StringUtils::isEmpty);
        ConverterUtils.setCheckVal(swaggerInfo::version , apiInfo.version() , "" , info::getVersion , StringUtils::isEmpty);
        swagger.info(swaggerInfo);

        ConverterUtils.setConvertVal(swagger::schemes , apiInfo.scheme() , ConverterUtils::convertScheme , ConverterUtils::isEmpty);
        ConverterUtils.setConvertVal(swagger::produces , apiInfo.produces() , Arrays::asList , ConverterUtils::isEmpty);
        ConverterUtils.setConvertVal(swagger::consumes , apiInfo.consumes() , Arrays::asList , ConverterUtils::isEmpty);

        return Either.right(swagger);
    }

}
