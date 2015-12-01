package org.lambda_wing;

import org.lambda_wing.tool.model.LambdaAliasRegisterResult;
import org.lambda_wing.tool.model.LambdaRegisterResult;

import java.util.List;

/**
 * User: makotan
 * Date: 2015/11/08
 */
public class WingDump {
    public long dumpTimestamp = System.currentTimeMillis();
    public List<LambdaRegisterResult> registerList;
    public List<LambdaAliasRegisterResult> lambdaAliasRegisterResults;
}
