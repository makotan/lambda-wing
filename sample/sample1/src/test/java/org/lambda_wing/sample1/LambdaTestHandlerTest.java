package org.lambda_wing.sample1;

import com.amazonaws.services.lambda.runtime.Context;
import org.lambda_wing.test.LambdaWingTestRunnner;
import org.lambda_wing.test.MockContext;
import org.lambda_wing.lambda.core.LambdaHandler;
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
