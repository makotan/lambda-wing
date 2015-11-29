package com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore;

import com.makotan.libs.lambda.wing.api_gw.core.RestResource;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.ClassToSwaggerConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.ConvertContext;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

import java.lang.annotation.Annotation;

/**
 * Created by makotan on 2015/11/29.
 */
public class RestResourceConverter implements ClassToSwaggerConverter {
    @Override
    public Class<? extends Annotation> scanAnnotation() {
        return RestResource.class;
    }

    @Override
    public boolean useMethodScan() {
        return true;
    }

    @Override
    public Either<SwaggerConvertErrors, Swagger> convert(Class<?> klass, SwaggerConvertInfo info, Swagger swagger, ConvertContext context) {
        RestResource restResource = klass.getAnnotation(RestResource.class);
        String path = restResource.value();
        Path swgPath = new Path();
        swagger.path(path , swgPath);
        context.putContext(RestResource.class,swgPath);
        return Either.right(swagger);
    }
}
