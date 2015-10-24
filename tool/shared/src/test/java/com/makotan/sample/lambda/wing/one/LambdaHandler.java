package com.makotan.sample.lambda.wing.one;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * Created by makotan on 2015/10/24.
 */
public class LambdaHandler {
    @com.makotan.libs.lambda.wing.core.LambdaHandler()
    public String call(int i , Context context) {
        return "";
    }
}
