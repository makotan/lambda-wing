package org.lambda_wing.cli;

import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.lambda.core.util.Tuple2;

import java.util.Queue;

/**
 * Created by makotan on 2015/12/27.
 */
public interface OptionParser {
    Either<CliParseError,CommandResult> parseOption(String arg,Queue<String> args, CommandResult result);

    boolean canOption(String param);

    class SimpleOptionParser implements OptionParser {
        private String longParam;
        private String shortParam;
        private boolean hasParam;

        public SimpleOptionParser(String longParam, String shortParam, boolean hasParam) {
            this.longParam = longParam;
            this.shortParam = shortParam;
            this.hasParam = hasParam;
        }

        public SimpleOptionParser(String longParam, boolean hasParam) {
            this.longParam = longParam;
            this.shortParam = null;
            this.hasParam = hasParam;
        }

        @Override
        public Either<CliParseError, CommandResult> parseOption(String arg,Queue<String> args, CommandResult result) {
            if (canOption(arg)) {
                args.poll();
                if (hasParam) {
                    result.commandOptions.add(new CommandOption(longParam , args.poll()));
                } else {
                    result.commandOptions.add(new CommandOption(longParam));
                }
            }
            return Either.right(result);
        }

        @Override
        public boolean canOption(String param) {
            return param.equals(longParam) || param.equals(shortParam);
        }
    }
}
