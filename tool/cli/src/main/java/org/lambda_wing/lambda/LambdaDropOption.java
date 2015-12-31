package org.lambda_wing.lambda;


import org.lambda_wing.cli.AbstractCliCommand;
import org.lambda_wing.cli.CliExecCommand;
import org.lambda_wing.cli.CliParseError;
import org.lambda_wing.cli.CommandResult;
import org.lambda_wing.lambda.core.util.Either;

import java.io.File;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaDropOption extends AbstractCliCommand  implements CliExecCommand {

    public LambdaDropOption() {
        super("drop");
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

    @Option(name = "--action", aliases = "-a" , usage = "select service action" , required = true)
    public LambdaAction action;

    @Option(name = "--region" , usage = "lambda deploy region")
    public String region;


    @Option(name = "--inputDump" , usage = "function result input dump file", required = true)
    public File inputDump;

    @Override
    public void execute(CliOptions options, String... args) throws CmdLineException {
        baseOption = options;
        new CmdLineParser(this).parseArgument(args);
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "LambdaDropOption{" +
                "baseOption=" + baseOption +
                ", action=" + action +
                ", region='" + region + '\'' +
                ", inputDump=" + inputDump +
                '}';
    }
    */
}
