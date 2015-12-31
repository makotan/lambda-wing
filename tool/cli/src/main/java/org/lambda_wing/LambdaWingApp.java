package org.lambda_wing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
