package org.lambda_wing.cli;

/**
 * Created by makotan on 2015/12/27.
 */
public class CommandOption {
    String param;
    String value = null;

    public CommandOption(String param , String value) {
        this.param = param;
        this.value = value;
    }
    public CommandOption(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
