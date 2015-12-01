package org.lambda_wing.lambda;

import org.lambda_wing.AbstractCliOptions;
import org.lambda_wing.CliOptions;
import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaDropOption extends AbstractCliOptions<LambdaDropOption> {
    @Option(name = "--service", aliases = "-s" , usage = "select target service" , required = true)
    public CliOptions.Service service;

    @Option(name = "--profile" , aliases = "-p" , usage = "credentials profile")
    public String profile;


    @Option(name = "--action", aliases = "-a" , usage = "select service action" , required = true)
    public LambdaBasicOption.Action action;

    @Option(name = "--region" , usage = "lambda deploy region")
    public String region;


    @Option(name = "--inputDump" , usage = "function result input dump file", required = true)
    public File inputDump;

    @Override
    protected LambdaDropOption create() {
        return new LambdaDropOption();
    }
}
