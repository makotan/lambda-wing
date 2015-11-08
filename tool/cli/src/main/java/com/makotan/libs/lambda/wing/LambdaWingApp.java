package com.makotan.libs.lambda.wing;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makotan.libs.lambda.wing.core.exception.LambdaWingException;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.core.util.StringUtils;
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
import java.time.Instant;
import java.time.LocalDateTime;
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
        try {
            if (options.basicCommand == CliOptions.command.deployLambda) {
                deployLambda(options);
            } else if (options.basicCommand == CliOptions.command.assignAlias) {
                assignAlias(options);
            } else if (options.basicCommand == CliOptions.command.dropLambda) {
                dropLambda(options);
            } else {
                logger.error("unknown command");
            }
        } catch (Exception e) {
            logger.error("options " + options , e);
        }
    }

    void assignAlias(CliOptions options) {
        WingDump wingDump = loadDump(options);
        List<? extends LambdaRegisterResult> rl = wingDump.lambdaAliasRegisterResults == null ? wingDump.registerList : wingDump.lambdaAliasRegisterResults;
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
        wingDump.lambdaAliasRegisterResults = alias.registerAlias(rg);
        outputDump(wingDump , options);
        logger.info("success assign alias");

    }


    void deployLambda(CliOptions options) {
        AmazonS3 s3 = createS3(options);
        copyJarToS3(options,s3);
        HandlerFinder finder = new HandlerFinder();
        
        try {
            WingDump wingDump = new WingDump();
            Set<Method> methods = finder.find(options.jarPath.toURI().toURL(), options.basePackage);
            logger.info("find {} method" , methods.size());
            LambdaHandlerUtil register = new LambdaHandlerUtil();
            LambdaRegisterInfo info = convertTo(options);
            wingDump.registerList = register.register(info, methods);

            if (StringUtils.isNotEmpty(options.aliasName)) {
                LambdaAlias alias = new LambdaAlias();
                LambdaAliasRegister rg = new LambdaAliasRegister();
                rg.aliasName = options.aliasName;
                rg.profile = options.profile;
                rg.region = options.region;
                rg.registerList = wingDump.registerList;
                wingDump.lambdaAliasRegisterResults = alias.registerAlias(rg);
            }
            
            outputDump(wingDump, options);
            
            logger.info("register {} method" , methods.size());
        } catch (MalformedURLException e) {
            throw new LambdaWingException(e);
        }
    }

    void dropLambda(CliOptions options) {
        WingDump wingDump = loadDump(options);

        LambdaHandlerUtil util = new LambdaHandlerUtil();
        List<? extends LambdaRegisterResult> rl = wingDump.lambdaAliasRegisterResults == null ? wingDump.registerList : wingDump.lambdaAliasRegisterResults;
        LambdaRegisterInfo info = new LambdaRegisterInfo();
        info.profile = options.profile;
        info.region = options.region;

        util.dropFunction(rl,info);

        logger.info("success drop lambdas");
    }

    void outputDump(WingDump dump , CliOptions options) {
        if (options.outputDump != null) {
            dump.cliOptions = options;
            try (OutputStream os = new FileOutputStream(options.outputDump);
                 XMLEncoder encoder = new XMLEncoder(os)
            ) {
                encoder.writeObject(dump);
            } catch (IOException ex) {
                logger.error("output dump " + options.outputDump ,ex);
            }
        }
    }

    WingDump loadDump(CliOptions options) {
        try (InputStream is = new FileInputStream(options.inputDump);
             XMLDecoder decoder = new XMLDecoder(is)
        ){
            WingDump dump = (WingDump) decoder.readObject();
            Instant instant = Instant.ofEpochSecond(dump.dumpTimestamp);
            LocalDateTime ldt = LocalDateTime.from(instant);
            logger.info("WingDump output time:%s" ,  ldt.toString());
            return dump;
        } catch (IOException ex) {
            logger.error("output josn " + options.inputDump ,ex);
            throw new LambdaWingException(ex);
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
