package com.makotan.libs.lambda.wing.tool.model;

import com.amazonaws.services.lambda.model.CreateAliasResult;
import com.amazonaws.services.lambda.model.UpdateAliasResult;

/**
 * Created by makotan on 2015/10/27.
 */
public class LambdaAliasRegisterResult extends LambdaRegisterResult {
    public UpdateAliasResult updateAliasResult;
    public CreateAliasResult createAliasResult;
}
