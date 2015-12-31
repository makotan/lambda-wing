package org.lambda_wing.lambda;


import org.lambda_wing.cli.AbstractCliCommand;
import org.lambda_wing.cli.CliExecCommand;
import org.lambda_wing.cli.CliParseError;
import org.lambda_wing.cli.CommandResult;
import org.lambda_wing.cli.OptionParser;
import org.lambda_wing.lambda.core.util.Either;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaAssignOption extends AbstractCliCommand implements CliExecCommand {

    public LambdaAssignOption() {
        super("assign");
    }

    @Override
    public Either<CliParseError, CommandResult> validate(CommandResult commandResult) {
        return Either.right(commandResult);
    }

    @Override
    public void execute(CommandResult commandResult) {

    }

/*
    public CliOptions baseOption;

    @Option(name = "--region" , usage = "lambda deploy region")
    public String region;

    @Option(name = "--aliasName" , usage = "Lambda alias name")
    public String aliasName;

    @Option(name = "--inputDump" , usage = "function result input dump file" , required = true)
    public File inputDump;

    @Option(name = "--outputDump" , usage = "function result output dump file")
    public File outputDump;


    @Override
    public void execute(CliOptions options, String... args) throws CmdLineException {
        baseOption = options;
        CmdLineParser cmdLineParser = new CmdLineParser(this);
        cmdLineParser.parseArgument(options.arguments);
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "LambdaAssignOption{" +
                "baseOption=" + baseOption +
                ", region='" + region + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", inputDump=" + inputDump +
                ", outputDump=" + outputDump +
                '}';
    }
    */
}
