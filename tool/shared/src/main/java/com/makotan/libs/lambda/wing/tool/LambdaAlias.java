package com.makotan.libs.lambda.wing.tool;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.CreateAliasRequest;
import com.amazonaws.services.lambda.model.CreateAliasResult;
import com.amazonaws.services.lambda.model.GetAliasRequest;
import com.amazonaws.services.lambda.model.GetAliasResult;
import com.amazonaws.services.lambda.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.model.UpdateAliasRequest;
import com.amazonaws.services.lambda.model.UpdateAliasResult;
import com.makotan.libs.lambda.wing.core.util.Tuple2;
import com.makotan.libs.lambda.wing.tool.aws.ToolAWSCredentialsProviderChain;
import com.makotan.libs.lambda.wing.tool.model.LambdaAliasRegister;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterInfo;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/10/26.
 */
public class LambdaAlias {
    Logger logger = LoggerFactory.getLogger(getClass());


    public static class AliasRegisterResult {
        public LambdaRegisterResult lambdaRegisterResult;
        public UpdateAliasResult updateAliasResult;
        public CreateAliasResult createAliasResult;
    }

    public List<AliasRegisterResult> registerAlias(LambdaAliasRegister register) {
        AWSLambda lambda = getAWSLambda(register);
        return register.registerList.stream()
                .map(rr -> register(rr, lambda, register))
                .collect(Collectors.toList());
    }

    AWSLambda getAWSLambda(LambdaAliasRegister info) {
        AWSLambda lambda = new AWSLambdaClient(new ToolAWSCredentialsProviderChain(info.profile));
        if (info.region != null && ! info.region.isEmpty()) {
            lambda.setRegion(Region.getRegion(Regions.fromName(info.region)));
        }
        return lambda;
    }

    AliasRegisterResult register(LambdaRegisterResult rr , AWSLambda lambda, LambdaAliasRegister register) {
        AliasRegisterResult result = new AliasRegisterResult();
        result.lambdaRegisterResult = rr;

        String functionName = null;
        String functionVersion = null;

        if (rr.createFunctionResult != null) {
            functionName = rr.createFunctionResult.getFunctionName();
            functionVersion = rr.createFunctionResult.getVersion();
        }

        if (rr.updateFunctionCodeResult != null) {
            functionName = rr.updateFunctionCodeResult.getFunctionName();
            functionVersion = rr.updateFunctionCodeResult.getVersion();

        }

        try {
            logger.info("register alias {}  function {} version {}" , register.aliasName , functionName , functionVersion);
            lambda.getAlias(new GetAliasRequest()
                            .withFunctionName(functionName)
                            .withName(register.aliasName)
            );
            try {
                logger.debug("update alias {}  function {} version {}", register.aliasName, functionName, functionVersion);
                result.updateAliasResult = lambda.updateAlias(new UpdateAliasRequest()
                                .withFunctionName(functionName)
                                .withFunctionVersion(functionVersion)
                                .withName(register.aliasName)
                );
            } catch (Exception ex) {
                logger.error("update alias", ex);
            }
        } catch (ResourceNotFoundException e) {
            logger.debug("create alias {}  function {} version {}" , register.aliasName , functionName , functionVersion);
            try {
                result.createAliasResult = lambda.createAlias(new CreateAliasRequest()
                                .withFunctionName(functionName)
                                .withFunctionVersion(functionVersion)
                                .withName(register.aliasName)
                );
            } catch (Exception ex) {
                logger.error("create alias", ex);
            }
        }
        return result;

    }
}
