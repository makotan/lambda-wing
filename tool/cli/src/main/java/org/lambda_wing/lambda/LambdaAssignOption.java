package org.lambda_wing.lambda;


import org.lambda_wing.cli.AbstractCliCommand;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaAssignOption extends AbstractCliCommand {

    public LambdaAssignOption() {
        super("assign");
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
