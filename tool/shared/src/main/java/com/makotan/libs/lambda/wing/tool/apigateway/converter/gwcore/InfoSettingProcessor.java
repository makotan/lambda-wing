package com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore;

import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.core.util.StringUtils;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.ConvertContext;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.ConverterUtils;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.SwaggerProcessor;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Info;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by makotan on 2015/11/30.
 */
public class InfoSettingProcessor extends SwaggerProcessor {
    public Either<SwaggerConvertErrors,Swagger> processor(SwaggerConvertInfo info, Either<SwaggerConvertErrors,Swagger> swaggerEither, ConvertContext context) {
        return swaggerEither.flatMap(swagger -> {
            ConverterUtils.setCheckVal(swagger::basePath , info.getBasePath() , StringUtils::isEmpty);

            Info swaggerInfo = new Info();
            ConverterUtils.setCheckVal(swaggerInfo::description , info.getDescription() , StringUtils::isEmpty);
            ConverterUtils.setCheckVal(swaggerInfo::title , info.getTitle() , StringUtils::isEmpty);
            ConverterUtils.setCheckVal(swaggerInfo::version , info.getVersion() , StringUtils::isEmpty);
            swagger.info(swaggerInfo);
            return Either.right(swagger);
        }).flatMap(swagger -> super.processor(info,Either.right(swagger),context));
    }

}
