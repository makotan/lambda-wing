package org.lambda_wing.tool.apigateway.converter;

import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertErrors;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Swagger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by makotan on 2015/11/29.
 */
public class SwaggerConverterTest {

    @Test
    public void converter() throws IOException {
        SwaggerConverter converter = new SwaggerConverter();
        SwaggerConvertInfo info = new SwaggerConvertInfo();
        info.basePackage = "org.lambda_wing.sample1.one";
        info.host = "api2.makotan.com";
        info.title = "swgcon";
        info.awsAccountId = "1234567890";
        Either<SwaggerConvertErrors, Swagger> convert = converter.convert(info);

        converter.outputFile(convert.getRight() , new File("logs/json/swagger.json"));

    }
}
