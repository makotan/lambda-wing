package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Swagger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by makotan on 2015/11/29.
 */
public interface MethodToSwaggerConverter {

    Class<? extends Annotation> scanAnnotation();

    Either<SwaggerConvertErrors,Swagger> convert(Class<?> klass, Method method, SwaggerConvertInfo info, Either<SwaggerConvertErrors, Swagger> swaggerEither, ConvertContext context);
}
