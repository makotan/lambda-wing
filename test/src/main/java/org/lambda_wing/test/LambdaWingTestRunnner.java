package org.lambda_wing.test;

import org.lambda_wing.lambda.core.LambdaHandler;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.HashMap;
import java.util.Map;


public class LambdaWingTestRunnner  extends BlockJUnit4ClassRunner {
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public LambdaWingTestRunnner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
    }

    @Override
    public void runChild(FrameworkMethod method, RunNotifier notifier) {
        LambdaHandler annotation = method.getAnnotation(LambdaHandler.class);
        Map<String, String> getenv = new HashMap<>();
        if (annotation != null) {
            String functionName = getFunctionName(method, annotation);
            backupEnv(getenv , "AWS_REGION" , "us-west-2");
            backupEnv(getenv , "AWS_LAMBDA_FUNCTION_MEMORY_SIZE" , ""+annotation.mem());
            backupEnv(getenv , "AWS_DEFAULT_REGION" , "us-west-2");
            backupEnv(getenv , "AWS_LAMBDA_LOG_GROUP_NAME" , "/aws/lambda/" + functionName);
            backupEnv(getenv , "AWS_LAMBDA_LOG_STREAM_NAME" , "");
            backupEnv(getenv , "AWS_LAMBDA_FUNCTION_VERSION" , "test");
            backupEnv(getenv , "AWS_LAMBDA_FUNCTION_NAME" ,  functionName);
            backupEnv(getenv , "LAMBDA_RUNTIME_LOAD_TIME" , "" + System.currentTimeMillis());
        }
        try {
            super.runChild(method, notifier);
        } finally {
            restoreEnv(getenv);
        }
    }
    
    void backupEnv(Map<String, String> getenv , String key , String newEnv) {
        getenv.put(key , System.getProperty(key));
        System.setProperty(key, newEnv);
    }
    
    void restoreEnv(Map<String, String> getenv) {
        getenv.entrySet().forEach(e -> {
            if (e.getValue() == null) {
                System.clearProperty(e.getKey());
            } else {
                System.setProperty(e.getKey() , e.getValue());
            }
        });
        
    }
    
    String getFunctionName(FrameworkMethod method , LambdaHandler annotation) {
        if (annotation.value().isEmpty()) {
            return method.getName();
        } else {
            return annotation.value();
        }
            
    }
}
