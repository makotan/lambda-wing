package org.lambda_wing.lambda;

import org.lambda_wing.AbstractCliOptions;
import org.lambda_wing.CliOptions;
import org.kohsuke.args4j.Option;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaBasicOption extends AbstractCliOptions<LambdaBasicOption> {

    @Override
    protected LambdaBasicOption create() {
        return new LambdaBasicOption();
    }

    public enum Action {
        deploy,
        assign,
        drop
    }

    @Option(name = "--service", aliases = "-s" , usage = "select target service" , required = true)
    public CliOptions.Service service;

    @Option(name = "--profile" , aliases = "-p" , usage = "credentials profile")
    public String profile = "default";

    @Option(name = "--action", aliases = "-a" , usage = "select service action" , required = true)
    public Action action;

}
