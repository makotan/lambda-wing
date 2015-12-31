package org.lambda_wing;

import org.lambda_wing.apigw.WingServiceApiGateway;
import org.lambda_wing.cli.CliCommand;
import org.lambda_wing.cli.CliExecCommand;
import org.lambda_wing.cli.CliParseError;
import org.lambda_wing.cli.CommandParseInfo;
import org.lambda_wing.cli.CommandResult;
import org.lambda_wing.cli.CommandTrasition;
import org.lambda_wing.cli.OptionParser;
import org.lambda_wing.lambda.LambdaAssignOption;
import org.lambda_wing.lambda.LambdaDeployOption;
import org.lambda_wing.lambda.LambdaDropOption;
import org.lambda_wing.lambda.WingServiceLambda;
import org.lambda_wing.lambda.core.util.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        CommandParseInfo info = new CommandParseInfo();

        info.getRootCliCommand()
                .add(new OptionParser.SimpleOptionParser("--profile" , true))
                .add(new OptionParser.SimpleOptionParser("--region" , true))
        ;

        WingServiceLambda wingServiceLambda = new WingServiceLambda();
        info.addTrasitionList(new CommandTrasition(info.getRootCliCommand(),wingServiceLambda));

        LambdaAssignOption lambdaAssignOption = new LambdaAssignOption();
        info.addTrasitionList(new CommandTrasition(wingServiceLambda,lambdaAssignOption));

        LambdaDeployOption lambdaDeployOption = new LambdaDeployOption();
        info.addTrasitionList(new CommandTrasition(wingServiceLambda,lambdaDeployOption));
        LambdaDropOption lambdaDropOption = new LambdaDropOption();
        info.addTrasitionList(new CommandTrasition(wingServiceLambda,lambdaDropOption));

        WingServiceApiGateway wingServiceApiGateway = new WingServiceApiGateway();
        info.addTrasitionList(new CommandTrasition(info.getRootCliCommand(),wingServiceApiGateway));

        Either<CliParseError, CommandResult> parse = info.parse(args);
        parse.processRight(cr -> {
            CliCommand cliCommand = cr.getCommands().get(cr.getCommands().size() - 1);
            if (cliCommand instanceof CliExecCommand) {
                ((CliExecCommand)cliCommand).execute(cr);
            }
        });

        parse.processLeft(err -> {
            System.err.println(err.toString());
        });

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
