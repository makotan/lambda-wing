package com.makotan.sample.lambda.wing;

import com.makotan.libs.lambda.win.test.LambdaWingTestRunnner;
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
        // TODO ちゃんとサンプルのテスト書く
        System.out.println("test");
    }
}
