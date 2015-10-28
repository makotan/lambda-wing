package com.makotan.libs.lambda.wing.tool.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by makotan on 2015/10/26.
 */
public class LambdaAliasRegister implements Serializable {
    public String profile;
    public String region;
    public String aliasName;
    public List<LambdaRegisterResult> registerList;

}
