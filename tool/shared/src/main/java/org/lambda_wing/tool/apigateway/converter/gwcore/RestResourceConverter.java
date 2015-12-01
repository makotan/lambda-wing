package org.lambda_wing.tool.apigateway.converter.gwcore;

import org.lambda_wing.api_gw.core.RestResource;
import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.tool.apigateway.converter.ClassToSwaggerConverter;
import org.lambda_wing.tool.apigateway.converter.ConvertContext;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertErrors;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertInfo;
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
    public Either<SwaggerConvertErrors, Swagger> convert(Class<?> klass, SwaggerConvertInfo info, Either<SwaggerConvertErrors, Swagger>  swaggerEither, ConvertContext context) {
        if (swaggerEither.isLeft()) {
            return swaggerEither;
        }
        Swagger swagger = swaggerEither.getRight();
        RestResource restResource = klass.getAnnotation(RestResource.class);
        String path = restResource.value();
        Path swgPath = new Path();
        swagger.path(path , swgPath);
        context.putContext(RestResource.class,swgPath);
        return Either.right(swagger);
    }
}
