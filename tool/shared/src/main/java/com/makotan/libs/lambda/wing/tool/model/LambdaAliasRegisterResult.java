package com.makotan.libs.lambda.wing.tool.model;

import com.amazonaws.services.lambda.model.CreateAliasResult;
import com.amazonaws.services.lambda.model.UpdateAliasResult;

import java.io.Serializable;

/**
 * Created by makotan on 2015/10/27.
 */
public class LambdaAliasRegisterResult extends LambdaRegisterResult  implements Serializable {
    public UpdateAliasResult updateAliasResult;
    public CreateAliasResult createAliasResult;

    public LambdaRegisterResult convertLambdaRegisterResult() {
        return this;
    }
}
