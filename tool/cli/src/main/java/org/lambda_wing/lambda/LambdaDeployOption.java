package org.lambda_wing.lambda;

import org.lambda_wing.AbstractCliOptions;
import org.lambda_wing.CliOptions;
import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaDeployOption extends AbstractCliOptions<LambdaDeployOption> {


    @Option(name = "--service", aliases = "-s" , usage = "select target service" , required = true)
    public CliOptions.Service service;

    @Option(name = "--profile" , aliases = "-p" , usage = "credentials profile")
    public String profile;


    @Option(name = "--action", aliases = "-a" , usage = "select service action" , required = true)
    public LambdaBasicOption.Action action;


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
    protected LambdaDeployOption create() {
        return new LambdaDeployOption();
    }
}
