package com.makotan.libs.lambda.win.test;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * User: makotan
 * Date: 2015/11/05
 * Time: 7:30
 */
public class MockContext implements Context {
    Logger logger;
    String awsRequestId = UUID.randomUUID().toString();
    String logGroupName = getEnv("AWS_LAMBDA_LOG_GROUP_NAME");
    String logStreamName = getEnv("AWS_LAMBDA_LOG_STREAM_NAME");
    String functionName = getEnv("AWS_LAMBDA_FUNCTION_NAME");
    CognitoIdentity identity;
    ClientContext clientContext;
    LambdaLogger lambdaLogger = new LambdaLogger() {
        @Override
        public void log(String string) {
            logger.debug(string);
        }
    };

    String getEnv(String key) {
        return System.getProperty(key , System.getenv(key));
    }
    
    
    public MockContext() {
        logger = LoggerFactory.getLogger("lambda");
    }

    public MockContext(Class<?> klass) {
        logger = LoggerFactory.getLogger(klass);
    }
    
    @Override
    public String getAwsRequestId() {
        return awsRequestId;
    }

    @Override
    public String getLogGroupName() {
        return logGroupName;
    }

    @Override
    public String getLogStreamName() {
        return logStreamName;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public String getFunctionVersion() {
        return null;
    }

    @Override
    public String getInvokedFunctionArn() {
        return null;
    }

    @Override
    public CognitoIdentity getIdentity() {
        return identity;
    }

    @Override
    public ClientContext getClientContext() {
        return clientContext;
    }

    @Override
    public int getRemainingTimeInMillis() {
        long remainingTime = System.currentTimeMillis() - Long.parseLong(getEnv("LAMBDA_RUNTIME_LOAD_TIME"));
        return (int) remainingTime;
    }

    @Override
    public int getMemoryLimitInMB() {
        return Integer.parseInt(getEnv("AWS_LAMBDA_FUNCTION_MEMORY_SIZE"));
    }

    @Override
    public LambdaLogger getLogger() {
        return lambdaLogger;
    }

    public void setIdentity(CognitoIdentity identity) {
        this.identity = identity;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }
}
