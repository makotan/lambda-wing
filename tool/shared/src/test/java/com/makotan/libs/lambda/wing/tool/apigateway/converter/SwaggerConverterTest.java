package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by makotan on 2015/11/29.
 */
public class SwaggerConverterTest {

    @Test
    public void converter() throws IOException {
        SwaggerConverter converter = new SwaggerConverter();
        SwaggerConvertInfo info = new SwaggerConvertInfo();
        info.basePackage = "com.makotan.sample.lambda.wing.one";
        info.host = "api2.makotan.com";
        info.title = "swgcon";
        info.awsAccountId = "1234567890";
        Either<SwaggerConvertErrors, Swagger> convert = converter.convert(info);
        outputSwagger(convert.getRight());

    }
    public void outputSwagger(Swagger swagger) throws IOException {
        Json.prettyPrint(swagger);
        List<String> outList = Arrays.asList(Json.pretty(swagger));
        java.nio.file.Path path = Paths.get("swagger.json");
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
        java.nio.file.Path file = Files.createFile(path);
        Files.write(file , outList , Charset.defaultCharset(),  StandardOpenOption.WRITE);
    }
}
