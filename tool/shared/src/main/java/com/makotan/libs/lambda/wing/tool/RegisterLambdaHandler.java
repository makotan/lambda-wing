package com.makotan.libs.lambda.wing.tool;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.*;
import com.amazonaws.services.lambda.model.Runtime;
import com.makotan.libs.lambda.wing.core.LambdaHandler;
import com.makotan.libs.lambda.wing.tool.aws.ToolAWSCredentialsProviderChain;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterInfo;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/10/24.
 */
public class RegisterLambdaHandler {
    Logger logger = LoggerFactory.getLogger(getClass());
    // aws lambda  --profile bassar --region us-west-2 create-function --function-name sample1 --runtime java8 --role arn:aws:iam::1234567890:role/lambda-poweruser --handler com.makotan.libs.lambda.wing.sample.Sample01 --zip-file fileb://sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar --timeout 15 --memory-size 512

    public List<LambdaRegisterResult> register(LambdaRegisterInfo info, Set<Method> methodSet) {
        AWSLambda awsLambda = getAWSLambda(info);
        Set<String> handlerSet = new HashSet<>();
        return methodSet.stream()
                .map(m -> {
                    LambdaRegisterResult result = new LambdaRegisterResult();
                    try {
                        LambdaRegisterInfo ri = createRegisterInfo(info, m);
                        if (handlerSet.contains(ri.handler)) {
                            return null;
                        }
                        result = registerLambda(ri, awsLambda);
                        handlerSet.add(ri.handler);
                    } catch (Exception e) {
                        // NOT PROCESS
                    }
                    return result;
                }).filter(rr -> rr != null)
                .collect(Collectors.toList());
    }

    LambdaRegisterInfo createRegisterInfo(LambdaRegisterInfo info, Method method) {
        LambdaRegisterInfo ri = info.copy();
        LambdaHandler lambdaHandler = method.getAnnotation(LambdaHandler.class);
        String funcName = lambdaHandler.value().isEmpty() ? method.getName() : lambdaHandler.value();
        ri.functionName = funcName;
        ri.timeout = lambdaHandler.time();
        ri.memory = lambdaHandler.mem();
        ri.handler = method.getDeclaringClass().getCanonicalName() + ":" + method.getName();
        return ri;
    }

    AWSLambda getAWSLambda(LambdaRegisterInfo info) {
        AWSLambda lambda = new AWSLambdaClient(new ToolAWSCredentialsProviderChain(info.profile));
        if (info.region != null && ! info.region.isEmpty()) {
            lambda.setRegion(Region.getRegion(Regions.fromName(info.region)));
        }
        return lambda;
    }

    LambdaRegisterResult registerLambda(LambdaRegisterInfo info, AWSLambda lambda) {
        LambdaRegisterResult ret = new LambdaRegisterResult();
        logger.info("register: function:" + info.functionName + "/handler:" + info.handler);
        logger.debug("register info :{}", info);
        try {

            getFunction(lambda,info.functionName);
            logger.debug("update function");
            try {
                ret.updateFunctionCodeResult = updateFunction(lambda, info);
            } catch (Exception ex) {
                logger.warn("update function" , ex);
                throw ex;
            }


        } catch (ResourceNotFoundException notEx) {
            logger.debug("create function");
            try {
                ret.createFunctionResult = createFunction(lambda, info);
            } catch (Exception ex) {
                logger.warn("create function" , ex);
                throw ex;
            }

        } catch (Exception ex) {
            logger.warn("get function",ex);
        }


        try {
            ret.result = getFunction(lambda,info.functionName);
        } catch (Exception ex) {
            logger.warn("get function",ex);
            throw ex;
        }
        return ret;
    }

    GetFunctionResult getFunction(AWSLambda lambda, String functionName) {
        return lambda.getFunction(new GetFunctionRequest().withFunctionName(functionName));
    }

    CreateFunctionResult createFunction(AWSLambda lambda,LambdaRegisterInfo info) {
        return lambda.createFunction(
                new CreateFunctionRequest()
                        .withFunctionName(info.functionName)
                        .withMemorySize(info.memory)
                        .withRuntime(Runtime.Java8)
                        .withTimeout(info.timeout)
                        .withRole(info.role)
                        .withCode(new FunctionCode()
                                        .withS3Bucket(info.s3Bucket)
                                        .withS3Key(info.s3Key)
                        )
                        .withHandler(info.handler)
                        .withPublish(info.publishVersion)
        );
    }

    UpdateFunctionCodeResult updateFunction(AWSLambda lambda, LambdaRegisterInfo info) {
        return lambda.updateFunctionCode(
                new UpdateFunctionCodeRequest()
                .withFunctionName(info.functionName)
                .withS3Bucket(info.s3Bucket)
                .withS3Key(info.s3Key)
                .withPublish(info.publishVersion)
        );
    }
}
