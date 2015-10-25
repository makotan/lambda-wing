package com.makotan.libs.lambda.wing.tool.model;

import com.amazonaws.services.lambda.model.CreateFunctionResult;
import com.amazonaws.services.lambda.model.GetFunctionResult;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeResult;

import java.lang.reflect.Method;

/**
 * Created by makotan on 2015/10/26.
 */
public class LambdaRegisterResult {
    public Method method;
    public GetFunctionResult result = null;
    public CreateFunctionResult createFunctionResult = null;
    public UpdateFunctionCodeResult updateFunctionCodeResult = null;
}
