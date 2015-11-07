package com.makotan.libs.lambda.wing;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makotan.libs.lambda.wing.core.exception.LambdaWingException;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.tool.HandlerFinder;
import com.makotan.libs.lambda.wing.tool.LambdaAlias;
import com.makotan.libs.lambda.wing.tool.LambdaHandlerUtil;
import com.makotan.libs.lambda.wing.tool.aws.ToolAWSCredentialsProviderChain;
import com.makotan.libs.lambda.wing.tool.model.LambdaAliasRegister;
import com.makotan.libs.lambda.wing.tool.model.LambdaAliasRegisterResult;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterInfo;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if(options.basicCommand == CliOptions.command.deployLambda) {
            deployLambda(options);
        } else if(options.basicCommand == CliOptions.command.assignAlias) {
            assignAlias(options);
        } else if (options.basicCommand == CliOptions.command.dropLambda) {
            dropLambda(options);
        } else {
            logger.error("unknown command");
        }
    }

    @SuppressWarnings (value="unchecked")
    List<? extends LambdaRegisterResult> convertLambdaRegisterResult(Object decoderObject) {
        return (List<? extends LambdaRegisterResult>)decoderObject;
    }

    void assignAlias(CliOptions options) {
        try (InputStream is = new FileInputStream(options.inputDump);
             XMLDecoder decoder = new XMLDecoder(is)
        ){

            List<? extends LambdaRegisterResult> rl = convertLambdaRegisterResult(decoder.readObject());
            LambdaAlias alias = new LambdaAlias();
            LambdaAliasRegister rg = new LambdaAliasRegister();
            rg.aliasName = options.aliasName;
            rg.profile = options.profile;
            rg.region = options.region;
            rg.registerList = rl.stream().map(lrr -> {
                if (lrr != null && lrr instanceof LambdaAliasRegisterResult) {
                    logger.debug("lrr is alias");
                    return ((LambdaAliasRegisterResult)lrr).convertLambdaRegisterResult();
                } else {
                    logger.debug("lrr is lrr");
                    return (LambdaRegisterResult)lrr;
                }
            }).collect(Collectors.toList());
            List<LambdaAliasRegisterResult> lambdaAliasRegisterResults = alias.registerAlias(rg);
            outputDump(lambdaAliasRegisterResults , options);
            logger.info("success assign alias");
        } catch (IOException ex) {
            logger.error("output josn " + options.inputDump ,ex);
        }

    }


    void deployLambda(CliOptions options) {
        AmazonS3 s3 = createS3(options);
        copyJarToS3(options,s3);
        HandlerFinder finder = new HandlerFinder();
        try {
            Set<Method> methods = finder.find(options.jarPath.toURI().toURL(), options.basePackage);
            logger.info("find {} method" , methods.size());
            LambdaHandlerUtil register = new LambdaHandlerUtil();
            LambdaRegisterInfo info = convertTo(options);
            List<LambdaRegisterResult> registerList = register.register(info, methods);
            if (options.aliasName != null && ! options.aliasName.isEmpty()) {
                LambdaAlias alias = new LambdaAlias();
                LambdaAliasRegister rg = new LambdaAliasRegister();
                rg.aliasName = options.aliasName;
                rg.profile = options.profile;
                rg.region = options.region;
                rg.registerList = registerList;
                List<LambdaAliasRegisterResult> lambdaAliasRegisterResults = alias.registerAlias(rg);
                outputLambdaRegisterResult(lambdaAliasRegisterResults, options);
            } else {
                outputLambdaRegisterResult(registerList, options);
            }
            logger.info("register {} method" , methods.size());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    void dropLambda(CliOptions options) {
        try (InputStream is = new FileInputStream(options.inputDump);
             XMLDecoder decoder = new XMLDecoder(is)
        ){
            LambdaHandlerUtil util = new LambdaHandlerUtil();
            List<? extends LambdaRegisterResult> rl = convertLambdaRegisterResult(decoder.readObject());
            LambdaRegisterInfo info = new LambdaRegisterInfo();
            info.profile = options.profile;
            info.region = options.region;

            util.dropFunction(rl,info);

            logger.info("success drop lambdas");
        } catch (IOException ex) {
            logger.error("output josn " + options.inputDump ,ex);
        }

    }

    <R extends LambdaRegisterResult >void outputLambdaRegisterResult(List<R> resultList,CliOptions options) {
        if (options.outputJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(options.outputJson, resultList);
            } catch (IOException ex) {
                logger.error("output josn " + options.outputJson ,ex);
            }
        }
        outputDump(resultList , options);
    }

    void outputDump(Object obj , CliOptions options) {
        if (options.outputDump != null) {
            try (OutputStream os = new FileOutputStream(options.outputDump);
                 XMLEncoder encoder = new XMLEncoder(os)
            ) {
                encoder.writeObject(obj);
            } catch (IOException ex) {
                logger.error("output dump " + options.outputDump ,ex);
            }
        }
    }

    LambdaRegisterInfo convertTo(CliOptions options) {
        LambdaRegisterInfo info = new LambdaRegisterInfo();
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
