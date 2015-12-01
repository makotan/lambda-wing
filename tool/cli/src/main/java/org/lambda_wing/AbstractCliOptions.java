package org.lambda_wing;

import org.lambda_wing.lambda.core.exception.LambdaWingException;
import org.lambda_wing.lambda.core.util.Either;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Created by makotan on 2015/11/30.
 */
public abstract class AbstractCliOptions<OP extends AbstractCliOptions> {


    protected abstract OP create();

    public Either<LambdaWingException,OP> parseArgument(String ... args) {
        OP options = create();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            return Either.right(options);
        } catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return Either.left(new LambdaWingException(e));
        }
    }

}
