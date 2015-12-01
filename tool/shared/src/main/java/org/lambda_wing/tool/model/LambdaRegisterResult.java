package org.lambda_wing.tool.model;

import com.amazonaws.services.lambda.model.CreateFunctionResult;
import com.amazonaws.services.lambda.model.GetFunctionResult;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by makotan on 2015/10/26.
 */
public class LambdaRegisterResult  implements Serializable {
    public GetFunctionResult result = null;
    public CreateFunctionResult createFunctionResult = null;
    public UpdateFunctionCodeResult updateFunctionCodeResult = null;
}
