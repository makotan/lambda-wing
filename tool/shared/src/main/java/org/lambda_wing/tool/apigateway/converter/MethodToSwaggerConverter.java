package org.lambda_wing.tool.apigateway.converter;

import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertErrors;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertInfo;
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
