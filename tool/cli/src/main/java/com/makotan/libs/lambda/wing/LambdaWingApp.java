package com.makotan.libs.lambda.wing;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.makotan.libs.lambda.wing.core.exception.LambdaWingException;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.HandlerFinder;
import com.makotan.libs.lambda.wing.tool.RegisterLambdaHandler;
import com.makotan.libs.lambda.wing.tool.aws.ToolAWSCredentialsProviderChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Created by makotan on 2015/10/25.
 */
public class LambdaWingApp {
    Logger logger = LoggerFactory.getLogger(getClass());
    // sample execute args
    // --command deployLambda --profile bassar --region us-west-2 --role arn:aws:iam::1234567890:role/lambda-poweruser --basePackage com.makotan.libs.lambda.wing.sample --s3Bucket makotan.be-deploy --s3Key deploy/dev/sample1-0.0.1-SNAPSHOT.jar --path sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar

    public static void main(String[] args) throws Exception {
        LambdaWingApp app = new LambdaWingApp();
        Either<LambdaWingException, CliOptions> cliOptionsEither = CliOptions.parseArgument(args);
        cliOptionsEither.flatMap(op -> {
            System.out.println(op);
            app.execute(op);
            return Either.right("step1");
        });
    }

    public void execute(CliOptions options) {
        AmazonS3 s3 = createS3(options);
        copyJarToS3(options,s3);
        HandlerFinder finder = new HandlerFinder();
        try {
            Set<Method> methods = finder.find(options.jarPath.toURI().toURL(), options.basePackage);
            logger.info("find {} method" , methods.size());
            RegisterLambdaHandler register = new RegisterLambdaHandler();
            RegisterLambdaHandler.RegisterInfo info = convertTo(options);
            register.register(info , methods);
            logger.info("register {} method" , methods.size());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    RegisterLambdaHandler.RegisterInfo convertTo(CliOptions options) {
        RegisterLambdaHandler.RegisterInfo info = new RegisterLambdaHandler.RegisterInfo();
        info.profile = options.profile;
        info.publishVersion = options.publishVersion;
        info.region = options.region;
        info.role = options.role;
        info.s3Bucket = options.s3Bucket;
        info.s3Key = options.s3Key;
        return info;
    }

    AmazonS3 createS3(CliOptions options) {
        return new AmazonS3Client(new ToolAWSCredentialsProviderChain(options.profile));
    }

    void copyJarToS3(CliOptions options,AmazonS3 s3) {
        PutObjectRequest putRequest = new PutObjectRequest(options.s3Bucket,options.s3Key , options.jarPath);
        s3.putObject(putRequest);
    }

}
