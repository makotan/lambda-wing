package org.lambda_wing.cli;

import org.lambda_wing.lambda.core.util.Either;

/**
 * Created by makotan on 2015/12/31.
 */
public interface CliExecCommand {
    Either<CliParseError,CommandResult> validate(CommandResult commandResult);
    void execute(CommandResult commandResult);
}
