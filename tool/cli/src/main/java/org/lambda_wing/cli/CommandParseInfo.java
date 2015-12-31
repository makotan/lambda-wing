package org.lambda_wing.cli;

import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.lambda.core.util.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by makotan on 2015/12/27.
 */
public class CommandParseInfo {

    final List<CommandTrasition> trasitionList = new ArrayList<>();


    public CommandParseInfo() {

    }

    public CommandParseInfo addTrasitionList(CommandTrasition trasition) {
        trasitionList.add(trasition);
        return this;
    }

    public static class RootCliCommand implements CliCommand {
        final List<OptionParser> rootOptionParserList = new ArrayList<>();
        final List<CliUsage> rootCliUsageList = new ArrayList<>();

        @Override
        public List<OptionParser> getOptionParserList() {
            return rootOptionParserList;
        }

        @Override
        public List<CliUsage> getUsageList() {
            return rootCliUsageList;
        }

        @Override
        public boolean isCommand(String arg) {
            return arg != null && arg.isEmpty(); // cli的にはヒットさせない
        }

        @Override
        public String getCommand() {
            return "";
        }

        @Override
        public boolean equals(Object command) {
            return command != null && command instanceof RootCliCommand;
        }
    }

    final RootCliCommand root = new RootCliCommand();

    public RootCliCommand getRootCliCommand() {
        return root;
    }

    List<CommandTrasition> getNextTrasitionList(CliCommand command) {
        return trasitionList.stream()
                .filter(t -> t.prevCliCommand.isCommand(command.getCommand()))
                .collect(Collectors.toList());
    }



    public Either<CliParseError,CommandResult> parse(String[] args) {
        Either<CliParseError, CommandResult> parse = parse(root, new LinkedList<String>(Arrays.asList(args)), Either.right(new CommandResult()));
        if (parse.isRight()) {
            return validateParse(parse.getRight());
        } else {
            return parse;
        }
    }

    private Either<CliParseError, CommandResult> validateParse(CommandResult result) {
        for (CommandOption co : result.commandOptions) {
            boolean hit = false;
            for (CliCommand cli : result.commands) {
                for (OptionParser op : cli.getOptionParserList()) {
                    if (op.canOption(co.getParam())) {
                        hit = true;
                    }
                }
            }
            if (!hit) {
                return createCliParseError("unknown option " + co.getParam() , result);
            }
        }
        return Either.right(result);
    }

    Either<CliParseError,CommandResult> parse(CliCommand current, Queue<String> args,Either<CliParseError,CommandResult> resultEither) {
        if (resultEither.isLeft()) {
            return resultEither;
        }
        CommandResult commandResult = resultEither.getRight();
        if (commandResult.commands.isEmpty()) {
            commandResult.commands.add(root);
        }
        String arg = args.peek();
        if (arg == null) {
            if (commandResult.commands.size() == 1) {
                return createParseError(current , "");
            }
            return resultEither;
        }

        List<CommandTrasition> nextTrasitionList = getNextTrasitionList(current);
        if (arg.startsWith("-")) {

            int size = args.size();
            parseOption(arg, current, args, resultEither);
            if (size == args.size()) {
                return createCliParseError("unknown option " + arg, commandResult);
            }
            return parse(current, args, resultEither);
        } else if (current instanceof CliExecCommand) {
            commandResult.args.add(args.poll());
            return parse(current, args,resultEither);
        } else {
            args.poll();
            // Commandの設定
            Optional<CommandTrasition> trasition = nextTrasitionList.stream()
                    .filter(ct -> ct.nextCliCommand.isCommand(arg))
                    .findFirst();
            if (trasition.isPresent()) {
                commandResult.commands.add(trasition.get().nextCliCommand);
                return parse(trasition.get().nextCliCommand, args,resultEither);
            } else {
                // usage回収retun
                return createUnknownParseError(current , arg);
            }
        }
    }

    Either<CliParseError,CommandResult> parseOption(String arg , CliCommand current, Queue<String> args,Either<CliParseError,CommandResult> resultEither) {
        int size = args.size();
        CommandResult commandResult = resultEither.getRight();
        current.getOptionParserList().stream()
                .forEach(p -> {
                    p.parseOption(arg , args , commandResult);
                });
        if (size == args.size()) {
            // 遡る
            commandResult.commands.stream()
                    .forEach(cmd -> {
                        if (cmd != current) {
                            cmd.getOptionParserList().stream()
                                    .forEach(p -> {
                                        p.parseOption(arg , args , commandResult);
                                    });
                        }
                    });
            if (size == args.size()) {
                // さらに下も探す
                List<CommandTrasition> nextTrasitionList = getNextTrasitionList(current);
                parseChildOption(arg , nextTrasitionList, args,commandResult,size);
                if (size == args.size()) {
                    return createCliParseError("unknown option " + arg , commandResult );
                }
            }
        }
        return Either.right(commandResult);
    }

    Either<CliParseError,CommandResult> parseChildOption(String arg , List<CommandTrasition> nextTrasitionList, Queue<String> args,CommandResult commandResult,int size) {
        if (size != args.size()) {
            return Either.right(commandResult);
        }
        if (nextTrasitionList.isEmpty()) {
            return Either.right(commandResult);
        }
        nextTrasitionList.forEach(ct -> {
            ct.nextCliCommand.getOptionParserList().forEach(p -> {
                p.parseOption(arg , args , commandResult);
            });
            parseChildOption(arg , getNextTrasitionList(ct.nextCliCommand), args,commandResult,size);
        });
        return createCliParseError("unknown option " + arg , commandResult );
    }

    Either<CliParseError,CommandResult> createCliParseError(String message , CommandResult commandResult , String ... args) {
        CliParseError parseError = new CliParseError();
        StringBuilder sb = new StringBuilder(message);
        sb.append("\n").append(Arrays.toString(args));
        commandResult.commands.forEach(cliCommand -> {
            sb.append(cliCommand.getCommand());
            parseError.usages.addAll(cliCommand.getUsageList());
        });
        parseError.message = sb.toString();
        return Either.left(parseError);

    }

    Either<CliParseError,CommandResult> createParseError(CliCommand current , String message) {
        List<CommandTrasition> nextTrasitionList = getNextTrasitionList(current);
        CliParseError parseError = new CliParseError();
        StringBuilder sb = new StringBuilder(message);
        nextTrasitionList.forEach(nt -> {
            sb.append(nt.nextCliCommand.getCommand()).append(" ");
            parseError.usages.addAll(nt.nextCliCommand.getUsageList());
        });
        parseError.message = sb.toString();
        return Either.left(parseError);
    }


    Either<CliParseError,CommandResult> createUnknownParseError(CliCommand current , String arg) {
        return createParseError(current , "unknown command [" + arg + "] ");
    }

}
