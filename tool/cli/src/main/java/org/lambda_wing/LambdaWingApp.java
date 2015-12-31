package org.lambda_wing;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.lambda_wing.apigw.WingServiceApiGateway;
import org.lambda_wing.lambda.LambdaBasicOption;
import org.lambda_wing.lambda.WingServiceLambda;
import org.lambda_wing.lambda.core.exception.LambdaWingException;
import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.lambda.core.util.StringUtils;
import org.lambda_wing.tool.HandlerFinder;
import org.lambda_wing.tool.LambdaAlias;
import org.lambda_wing.tool.LambdaHandlerUtil;
import org.lambda_wing.tool.aws.ToolAWSCredentialsProviderChain;
import org.lambda_wing.tool.model.LambdaAliasRegister;
import org.lambda_wing.tool.model.LambdaAliasRegisterResult;
import org.lambda_wing.tool.model.LambdaRegisterInfo;
import org.lambda_wing.tool.model.LambdaRegisterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/10/25.
 */
public class LambdaWingApp {
    Logger logger = LoggerFactory.getLogger(getClass());
    // sample execute args
    // --command deployLambda --profile bassar --region us-west-2 --role arn:aws:iam::1234567890:role/lambda-poweruser --basePackage com.makotan.libs.lambda.wing.sample --s3Bucket makotan.be-deploy --s3Key deploy/dev/sample1-0.0.1-SNAPSHOT.jar --path sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar

    public static void main(String[] args) throws Exception {
        LambdaWingApp app = new LambdaWingApp();
        try {
            app.execute(args);
        } catch (Throwable th) {
            System.err.println(th.toString());
        }
    }

    public void execute(String[] args) {
        /*
        CliOptions option = new CliOptions();
        CmdLineParser cmdLineParser = new CmdLineParser(option);
        try {
            cmdLineParser.parseArgument(args);
            if (option.service == CliOptions.ServiceType.lambda) {
                WingServiceLambda lambda = new WingServiceLambda();
                lambda.execute(option , option.arguments);
            } else if (option.service == CliOptions.ServiceType.apigateway) {
                WingServiceApiGateway apiGateway = new WingServiceApiGateway();
                apiGateway.execute(option , option.arguments);
            } else {
                cmdLineParser.printUsage(System.err);
            }

        } catch (CmdLineException e) {
            System.err.println("use cli options");

            e.getParser().printUsage(System.err);
        }
        */
    }
}
