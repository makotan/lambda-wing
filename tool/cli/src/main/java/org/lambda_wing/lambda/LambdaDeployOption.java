package org.lambda_wing.lambda;


import org.lambda_wing.cli.AbstractCliCommand;
import org.lambda_wing.cli.CliExecCommand;
import org.lambda_wing.cli.CliParseError;
import org.lambda_wing.cli.CommandResult;
import org.lambda_wing.cli.OptionParser;
import org.lambda_wing.lambda.core.util.Either;

import java.io.File;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaDeployOption extends AbstractCliCommand  implements CliExecCommand {

    public LambdaDeployOption() {
        super("deploy");
        /*
        add(new OptionParser.SimpleOptionParser("--path" , true));
        add(new OptionParser.SimpleOptionParser("--role" , true));
        add(new OptionParser.SimpleOptionParser("--s3Bucket" , true));
        add(new OptionParser.SimpleOptionParser("--s3Key" , true));
        add(new OptionParser.SimpleOptionParser("--basePackage" , true));
        */

        add(new OptionParser.SimpleOptionParser("--region" , true));
        add(new OptionParser.SimpleOptionParser("--publishVersion" , false));
        add(new OptionParser.SimpleOptionParser("--aliasName" , true));
        add(new OptionParser.SimpleOptionParser("--outputJson" , true));
        add(new OptionParser.SimpleOptionParser("--outputDump" , true));

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

    @Option(name = "--path" , usage = "deploy jar path" , required = true)
    public File jarPath;
    @Option(name = "--region" , usage = "lambda deploy region")
    public String region;
    @Option(name = "--role" , usage = "lambda execute role. not deploy role")
    public String role;
    @Option(name = "--s3Bucket" , usage = "lambda deploy s3 bucket" , required = true)
    public String s3Bucket;
    @Option(name = "--s3Key" , usage = "lambda deploy full key name. ex)xxxx/yyyy.jar" , required = true)
    public String s3Key;
    @Option(name = "--publishVersion" , usage = "lambda publish version. see lambda manual")
    public boolean publishVersion = false;
    @Option(name = "--basePackage" , usage = "Lambda function package name" , required = true)
    public String basePackage;
    @Option(name = "--aliasName" , usage = "Lambda alias name")
    public String aliasName;
    @Option(name = "--outputJson" , usage = "function result output json")
    public File outputJson;

    @Option(name = "--outputDump" , usage = "function result output dump file")
    public File outputDump;


    @Override
    public void execute(CliOptions options, String... args) throws CmdLineException {
        baseOption = options;
        new CmdLineParser(this).parseArgument(args);
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "LambdaDeployOption{" +
                "baseOption=" + baseOption +
                ", jarPath=" + jarPath +
                ", region='" + region + '\'' +
                ", role='" + role + '\'' +
                ", s3Bucket='" + s3Bucket + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", publishVersion=" + publishVersion +
                ", basePackage='" + basePackage + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", outputJson=" + outputJson +
                ", outputDump=" + outputDump +
                '}';
    }
    */
}
