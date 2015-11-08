package com.makotan.libs.lambda.wing;

import com.makotan.libs.lambda.wing.tool.model.LambdaAliasRegisterResult;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterResult;

import java.util.List;

/**
 * User: makotan
 * Date: 2015/11/08
 */
public class WingDump {
    public long dumpTimestamp = System.currentTimeMillis();
    public CliOptions cliOptions;
    public List<LambdaRegisterResult> registerList;
    public List<LambdaAliasRegisterResult> lambdaAliasRegisterResults;
}
