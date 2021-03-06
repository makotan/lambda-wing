package com.makotan.sample.lambda.wing;

import com.amazonaws.services.lambda.runtime.Context;
import com.makotan.libs.lambda.wing.core.LambdaHandler;

/**
 * User: makotan
 * Date: 2015/11/04
 * Time: 21:47
 */
public class LambdaTestHandler {
    public static class Req {
        public String req;
    }
    
    public static class Res {
        public String res;
    }
    
    @LambdaHandler(value = "TestHandler" , mem = 512 , time = 60)
    public Res call(Req req , Context context) {
        Res res = new Res();
        res.res = req.req;
        return res;
    }
    
}
