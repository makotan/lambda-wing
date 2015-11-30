package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by makotan on 2015/11/30.
 */
public abstract class SwaggerProcessor {
    public Either<SwaggerConvertErrors,Swagger> processor(SwaggerConvertInfo info, Either<SwaggerConvertErrors,Swagger> swaggerEither, ConvertContext context) {
        return swaggerEither.flatMap(swagger -> {
            if (swagger.getPaths() == null || swagger.getPaths().isEmpty()) {
                return Either.right(swagger);
            }
            Stream<Map.Entry<String, Path>> stream = swagger.getPaths().entrySet().stream();
            return ConverterUtils.foldLeft(stream , swaggerEither
                    , (either, entry) -> processor(entry.getKey(),entry.getValue(),info, either, context));
        });
    }

    public Either<SwaggerConvertErrors,Swagger> processor(String pathUrl,Path path,SwaggerConvertInfo info, Either<SwaggerConvertErrors, Swagger> swaggerEither, ConvertContext context) {
        return swaggerEither.flatMap(swagger -> {
            if (path.getOperations() == null || path.getOperations().isEmpty()) {
                return Either.right(swagger);
            }
            return ConverterUtils.foldLeft(path.getOperations().stream(),swaggerEither
                    , (either , op) -> processor(op,path,info,either,context));
        });
    }

    public Either<SwaggerConvertErrors,Swagger> processor(Operation operation,Path path, SwaggerConvertInfo info, Either<SwaggerConvertErrors, Swagger> swaggerEither, ConvertContext context) {
        return swaggerEither;
    }

}
