package org.lambda_wing.cli;

import java.util.List;

/**
 * Created by makotan on 2015/12/27.
 */
public interface CliCommand {
    List<OptionParser> getOptionParserList();
    List<CliUsage> getUsageList();
    boolean isCommand(String arg);
    String getCommand();
}
