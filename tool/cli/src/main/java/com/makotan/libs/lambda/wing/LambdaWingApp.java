package com.makotan.libs.lambda.wing;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.makotan.libs.lambda.wing.core.exception.LambdaWingException;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.aws.ToolAWSCredentialsProviderChain;

/**
 * Created by makotan on 2015/10/25.
 */
public class LambdaWingApp {
    // sample execute args
    // --command deployLambda --profile bassar --region us-west-2 --role arn:aws:iam::1234567890:role/lambda-poweruser --s3Bucket makotan.be-deploy --s3Key deploy/dev/sample1-0.0.1-SNAPSHOT.jar --path sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar

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
    }

    AmazonS3 createS3(CliOptions options) {
        return new AmazonS3Client(new ToolAWSCredentialsProviderChain(options.profile));
    }

    void copyJarToS3(CliOptions options,AmazonS3 s3) {
        PutObjectRequest putRequest = new PutObjectRequest(options.s3Bucket,options.s3Key , options.jarPath);
        s3.putObject(putRequest);
    }

}
