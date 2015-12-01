package org.lambda_wing.tool;


import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by makotan on 2015/10/29.
 */
public class ApiGatewayModelUtilTest {

    @Test
    public void test() {
        HandlerFinder finder = new HandlerFinder();
        Set<Method> methods = finder.find("org.lambda_wing.sample1.one");
        ApiGatewayModelUtil util = new ApiGatewayModelUtil();
        Map<String, String> jsonSchemaMap = util.createFunctionModels(methods);
        System.out.println(jsonSchemaMap);
    }
}
