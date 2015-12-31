package org.lambda_wing.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makotan on 2015/12/27.
 */
public class CommandResult {
    List<CliCommand> commands = new ArrayList<>();
    List<CommandOption> commandOptions = new ArrayList<>();
    List<String> args = new ArrayList<>();

    public List<CliCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<CliCommand> commands) {
        this.commands = commands;
    }

    public List<CommandOption> getCommandOptions() {
        return commandOptions;
    }

    public void setCommandOptions(List<CommandOption> commandOptions) {
        this.commandOptions = commandOptions;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}

