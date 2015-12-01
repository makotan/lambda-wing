package org.lambda_wing.sample1.one;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * Created by makotan on 2015/10/24.
 */
public class LambdaHandler {
    @org.lambda_wing.lambda.core.LambdaHandler()
    public String call(int i , Context context) {
        return "";
    }
}
