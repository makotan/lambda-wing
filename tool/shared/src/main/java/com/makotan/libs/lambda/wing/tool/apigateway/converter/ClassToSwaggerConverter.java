package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Swagger;

import java.lang.annotation.Annotation;

/**
 * Created by makotan on 2015/11/29.
 */
public interface ClassToSwaggerConverter {

    Class<? extends Annotation> scanAnnotation();

    boolean useMethodScan();

    Either<SwaggerConvertErrors,Swagger> convert(Class<?> klass, SwaggerConvertInfo info, Swagger swagger, ConvertContext context);

}
