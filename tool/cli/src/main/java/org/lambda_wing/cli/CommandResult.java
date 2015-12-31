package org.lambda_wing.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makotan on 2015/12/27.
 */
public class CommandResult {
    List<CliCommand> commands = new ArrayList<>();
    List<CommandOption> commandOptions = new ArrayList<>();
    String[] arg;
}
