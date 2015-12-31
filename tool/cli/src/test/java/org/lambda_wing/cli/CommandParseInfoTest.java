package org.lambda_wing.cli;



import org.junit.Before;
import org.junit.Test;
import org.lambda_wing.lambda.core.util.Either;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by makotan on 2015/12/30.
 */
public class CommandParseInfoTest {

    public static class LambdaCommand extends AbstractCliCommand {
        public LambdaCommand() {
            super("lambda");
        }
    }

    public static class LambdaDeployCommand extends AbstractCliCommand {
        public LambdaDeployCommand() {
            super("deploy");
        }
    }

    public static class LambdaDropCommand extends AbstractCliCommand {
        public LambdaDropCommand() {
            super("drop");
        }
    }

    public static class ApiGatewayCommand extends AbstractCliCommand {
        public ApiGatewayCommand() {
            super("ApiGateway".toLowerCase());
        }
    }

    CommandParseInfo info = new CommandParseInfo();

    @Before
    public void setup() {
        info = new CommandParseInfo();

        info.getRootCliCommand().getOptionParserList().add(new OptionParser.SimpleOptionParser("--reagion", true));
        LambdaCommand lambdaCommand = new LambdaCommand();
        LambdaDeployCommand lambdaDeployCommand = new LambdaDeployCommand();
        lambdaDeployCommand.getOptionParserList().add(new OptionParser.SimpleOptionParser("--publishVersion", false));
        LambdaDropCommand lambdaDropCommand = new LambdaDropCommand();
        ApiGatewayCommand apiGatewayCommand = new ApiGatewayCommand();

        info.addTrasitionList(
                new CommandTrasition(info.getRootCliCommand() , lambdaCommand)
        );
        info.addTrasitionList(
                new CommandTrasition(lambdaCommand , lambdaDeployCommand)
        );
        info.addTrasitionList(
                new CommandTrasition(lambdaCommand , lambdaDropCommand)
        );
        info.addTrasitionList(
                new CommandTrasition(info.getRootCliCommand() , apiGatewayCommand));
    }


    @Test
    public void zeroArgParseTest() {
        String[] args = {};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isLeft());
    }

    @Test
    public void LambdaArgParseTest() {
        String[] args = {"lambda"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isRight());
    }

    @Test
    public void LambdaDeployArgParseTest() {
        String[] args = {"lambda","deploy"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isRight());
        assertThat(parseResult.getRight().commands.size() , is(3));
    }

    @Test
    public void LambdaMissArgParseTest() {
        String[] args = {"lambda","miss"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isLeft());
    }

    @Test
    public void GlobalParamParseTest() {
        String[] args = {"--reagion" , "japan"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isLeft());
    }

    @Test
    public void GlobalParamLambdaArgParseTest() {
        String[] args = {"--reagion" , "japan" , "lambda"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isRight());
        assertThat(parseResult.getRight().commandOptions.size(), is(1));
    }

    @Test
    public void LambdaArgGlobalParamParseTest() {
        String[] args = {"lambda" , "--reagion" , "japan"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isRight());
        assertThat(parseResult.getRight().commandOptions.size(), is(1));
    }

    @Test
    public void LambdaDeployArgGlobalParamParseTest() {
        String[] args = {"lambda","deploy" , "--reagion" , "japan" , "--publishVersion"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isRight());
        assertThat(parseResult.getRight().commands.size() , is(3));
    }

    @Test
    public void DeployParamLambdaDeployArgParseTest() {
        String[] args = { "--publishVersion" , "lambda", "deploy" , "--reagion" , "japan"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isRight());
        assertThat(parseResult.getRight().commands.size() , is(3));
    }

    @Test
    public void DeployParamLambdaDropArgParseTest() {
        String[] args = { "lambda", "drop" , "--reagion" , "japan" ,  "--publishVersion"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isLeft());
    }

    @Test
    public void DeployParamLambdaParseTest() {
        String[] args = { "lambda",  "--reagion" , "japan" ,  "--publishVersion"};
        Either<CliParseError, CommandResult> parseResult = info.parse(args);

        assertTrue(parseResult.isLeft());
    }

}
