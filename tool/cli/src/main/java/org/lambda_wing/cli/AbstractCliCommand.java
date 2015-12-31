package org.lambda_wing.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makotan on 2015/12/30.
 */
public class AbstractCliCommand implements CliCommand {
    public String command;
    public List<OptionParser> parsers = new ArrayList<>();
    public List<CliUsage> usages = new ArrayList<>();

    protected AbstractCliCommand(String command) {
        this.command = command;
    }

    @Override
    public List<OptionParser> getOptionParserList() {
        return parsers;
    }

    @Override
    public List<CliUsage> getUsageList() {
        return usages;
    }

    @Override
    public boolean isCommand(String arg) {
        return command.equalsIgnoreCase(arg);
    }

    @Override
    public String getCommand() {
        return command;
    }
}
