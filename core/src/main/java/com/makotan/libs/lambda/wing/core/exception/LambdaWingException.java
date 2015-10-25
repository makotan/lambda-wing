package com.makotan.libs.lambda.wing.core.exception;

import java.io.PrintStream;
import java.util.Optional;

/**
 * Created by makotan on 2015/09/19.
 */
public class LambdaWingException extends RuntimeException {
    Optional<String> message = Optional.empty();
    Optional<Throwable> cause = Optional.empty();

    public LambdaWingException() {
        super();
    }
    public LambdaWingException(String message) {
        super(message);
        this.message = Optional.of(message);
    }
    public LambdaWingException(String message, Throwable cause) {
        super(message, cause);
        this.message = Optional.of(message);
        this.cause = Optional.of(cause);
    }
    public LambdaWingException(Throwable cause) {
        super(cause);
        this.cause = Optional.of(cause);
    }
    protected LambdaWingException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = Optional.of(message);
        this.cause = Optional.of(cause);
    }

    public String getMessage() {
        return message.orElseGet(super::getMessage);
    }

    public String getLocalizedMessage() {
        return message.orElseGet(super::getLocalizedMessage);
    }
    public synchronized Throwable getCause() {
        return cause.orElseGet(super::getCause);
    }
    public String toString() {
        return message.orElseGet(super::toString);
    }
    public void printStackTrace(PrintStream s) {
        if (cause.isPresent()) {
            cause.get().printStackTrace(s);
        } else {
            super.printStackTrace(s);
        }
    }
    public StackTraceElement[] getStackTrace() {
        if (cause.isPresent()) {
            return cause.get().getStackTrace();
        } else {
            return super.getStackTrace();
        }
    }

}
