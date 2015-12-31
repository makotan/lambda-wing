package org.lambda_wing.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makotan on 2015/12/27.
 */
public class CommandTrasition {
    CliCommand prevCliCommand;
    CliCommand nextCliCommand;
    List<OptionParser> parsers = new ArrayList<>();
    List<CliUsage> optionUsageList = new ArrayList<>();

    public CommandTrasition(CliCommand prevCliCommand , CliCommand nextCliCommand) {
        this.prevCliCommand = prevCliCommand;
        this.nextCliCommand = nextCliCommand;
    }

    public CommandTrasition withOptionParser(OptionParser parser) {
        parsers.add(parser);
        return this;
    }

    public CommandTrasition withUsage(CliUsage usage) {
        optionUsageList.add(usage);
        return this;
    }
}
