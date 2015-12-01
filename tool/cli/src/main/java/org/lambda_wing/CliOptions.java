package org.lambda_wing;

import org.lambda_wing.lambda.core.exception.LambdaWingException;
import org.lambda_wing.lambda.core.util.Either;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * Created by makotan on 2015/10/25.
 */
public class CliOptions {
    public enum Service {
        lambda,
        apigw
    }

    @Option(name = "--service", aliases = "-s" , usage = "select target service" , required = true)
    public Service service;

    @Option(name = "--profile" , aliases = "-p" , usage = "credentials profile")
    public String profile = "default";



    public enum command_old {
        deployLambda,
        assignAlias,
        dropLambda
    };

    @Option(name = "--command", usage = "execute command. default deployLambda")
    public command_old basicCommandOld = command_old.deployLambda;
    @Option(name = "--path" , usage = "deploy jar path")
    public File jarPath;
    @Option(name = "--region" , usage = "lambda deploy region")
    public String region;
    @Option(name = "--role" , usage = "lambda execute role. not deploy role")
    public String role;
    @Option(name = "--s3Bucket" , usage = "lambda deploy s3 bucket")
    public String s3Bucket;
    @Option(name = "--s3Key" , usage = "lambda deploy full key name. ex)xxxx/yyyy.jar")
    public String s3Key;
    @Option(name = "--publishVersion" , usage = "lambda publish version. see lambda manual")
    public boolean publishVersion = false;
    @Option(name = "--basePackage" , usage = "Lambda function package name")
    public String basePackage;
    @Option(name = "--aliasName" , usage = "Lambda alias name")
    public String aliasName;
    @Option(name = "--outputJson" , usage = "function result output json")
    public File outputJson;
    @Option(name = "--outputDump" , usage = "function result output dump file")
    public File outputDump;
    @Option(name = "--inputDump" , usage = "function result input dump file")
    public File inputDump;

    public static Either<LambdaWingException,CliOptions> parseArgument(String ... args) {
        CliOptions options = new CliOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            return Either.right(options);
        } catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return Either.left(new LambdaWingException(e));
        }
    }

    @Override
    public String toString() {
        return "CliOptions{" +
                "basicCommand=" + basicCommandOld +
                ", jarPath=" + jarPath +
                ", profile='" + profile + '\'' +
                ", region='" + region + '\'' +
                ", role='" + role + '\'' +
                ", s3Bucket='" + s3Bucket + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", publishVersion=" + publishVersion +
                ", basePackage='" + basePackage + '\'' +
                ", aliasName='" + aliasName + '\'' +
                '}';
    }
}
