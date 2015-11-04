package com.makotan.sample.lambda.wing;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.makotan.libs.lambda.win.test.LambdaWingTestRunnner;
import com.makotan.libs.lambda.win.test.MockContext;
import com.makotan.libs.lambda.wing.core.LambdaHandler;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * User: makotan
 * Date: 2015/11/04
 * Time: 21:49
 */
@RunWith(LambdaWingTestRunnner.class)
public class LambdaTestHandlerTest {
    
    @Test
    @LambdaHandler(value = "TestHandler" , mem = 512 , time = 60)
    public void test() {
        LambdaTestHandler handler = new LambdaTestHandler();
        LambdaTestHandler.Req req = new LambdaTestHandler.Req();
        Context context = new MockContext();
        LambdaTestHandler.Res call = handler.call(req, context);
    }
    
    @Test
    public void test2() {
        LambdaTestHandler handler = new LambdaTestHandler();
        LambdaTestHandler.Req req = new LambdaTestHandler.Req();
        Context context = new MockContext();
        LambdaTestHandler.Res call = handler.call(req, context);
    }
}
