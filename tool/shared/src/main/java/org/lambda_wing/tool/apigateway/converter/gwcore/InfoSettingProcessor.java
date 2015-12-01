package org.lambda_wing.tool.apigateway.converter.gwcore;

import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.lambda.core.util.StringUtils;
import org.lambda_wing.tool.apigateway.converter.ConvertContext;
import org.lambda_wing.tool.apigateway.converter.ConverterUtils;
import org.lambda_wing.tool.apigateway.converter.SwaggerProcessor;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertErrors;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Info;
import io.swagger.models.Swagger;

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
