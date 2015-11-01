package com.makotan.libs.lambda.wing.tool;


import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by makotan on 2015/10/29.
 */
public class ApiGatewayModelUtilTest {

    @Test
    public void test() {
        HandlerFinder finder = new HandlerFinder();
        Set<Method> methods = finder.find("com.makotan.sample.lambda.wing.one");
        ApiGatewayModelUtil util = new ApiGatewayModelUtil();
        Map<String, String> jsonSchemaMap = util.createFunctionModels(methods);
        System.out.println(jsonSchemaMap);
    }
}
