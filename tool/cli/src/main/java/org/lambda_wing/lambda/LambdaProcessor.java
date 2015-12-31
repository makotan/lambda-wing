package org.lambda_wing.lambda;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.lambda_wing.WingDump;
import org.lambda_wing.lambda.core.exception.LambdaWingException;
import org.lambda_wing.lambda.core.util.StringUtils;
import org.lambda_wing.tool.HandlerFinder;
import org.lambda_wing.tool.LambdaAlias;
import org.lambda_wing.tool.LambdaHandlerUtil;
import org.lambda_wing.tool.aws.ToolAWSCredentialsProviderChain;
import org.lambda_wing.tool.model.LambdaAliasRegister;
import org.lambda_wing.tool.model.LambdaAliasRegisterResult;
import org.lambda_wing.tool.model.LambdaRegisterInfo;
import org.lambda_wing.tool.model.LambdaRegisterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/11/30.
 */
public class LambdaProcessor {
    Logger logger = LoggerFactory.getLogger(getClass());
/*
    public void assignAlias(LambdaAssignOption options) {
        WingDump wingDump = loadDump(options.inputDump);
        List<? extends LambdaRegisterResult> rl = wingDump.lambdaAliasRegisterResults == null ? wingDump.registerList : wingDump.lambdaAliasRegisterResults;
        LambdaAlias alias = new LambdaAlias();
        LambdaAliasRegister rg = new LambdaAliasRegister();
        rg.aliasName = options.aliasName;
        rg.profile = "";//options.baseOption.profile;
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
        outputDump(wingDump , options.outputDump);
        logger.info("success assign alias");

    }


    public void deployLambda(LambdaDeployOption options) {
        AmazonS3 s3 = createS3(options.baseOption.profile);
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
                rg.profile = options.baseOption.profile;
                rg.region = options.region;
                rg.registerList = wingDump.registerList;
                wingDump.lambdaAliasRegisterResults = alias.registerAlias(rg);
            }

            outputDump(wingDump, options.outputDump);

            logger.info("register {} method" , methods.size());
        } catch (MalformedURLException e) {
            throw new LambdaWingException(e);
        }
    }

    public void dropLambda(LambdaDropOption options) {
        WingDump wingDump = loadDump(options.inputDump);

        LambdaHandlerUtil util = new LambdaHandlerUtil();
        List<? extends LambdaRegisterResult> rl = wingDump.registerList;
        LambdaRegisterInfo info = new LambdaRegisterInfo();
        info.profile = options.baseOption.profile;
        info.region = options.region;

        util.dropFunction(rl,info);

        logger.info("success drop lambdas");
    }

    void outputDump(WingDump dump , File outputDump) {
        if (outputDump != null) {
            try (OutputStream os = new FileOutputStream(outputDump);
                 XMLEncoder encoder = new XMLEncoder(os)
            ) {
                encoder.writeObject(dump);
            } catch (IOException ex) {
                logger.error("output dump " + outputDump ,ex);
            }
        }
    }

    WingDump loadDump(File inputDump) {
        try (InputStream is = new FileInputStream(inputDump);
             XMLDecoder decoder = new XMLDecoder(is)
        ){
            WingDump dump = (WingDump) decoder.readObject();
            Instant instant = Instant.ofEpochMilli(dump.dumpTimestamp);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            logger.info("WingDump output datetime: {}" ,  dateTime.toString());
            return dump;
        } catch (IOException ex) {
            logger.error("output josn " + inputDump ,ex);
            throw new LambdaWingException(ex);
        } catch (Exception ex) {
            throw new LambdaWingException(ex);
        }
    }

    LambdaRegisterInfo convertTo(LambdaDeployOption options) {
        LambdaRegisterInfo info = new LambdaRegisterInfo();
        info.profile = options.baseOption.profile;
        info.publishVersion = options.publishVersion;
        info.region = options.region;
        info.role = options.role;
        info.s3Bucket = options.s3Bucket;
        info.s3Key = options.s3Key;
        return info;
    }

    AmazonS3 createS3(String profile) {
        return new AmazonS3Client(new ToolAWSCredentialsProviderChain(profile));
    }

    void copyJarToS3(LambdaDeployOption options,AmazonS3 s3) {
        PutObjectRequest putRequest = new PutObjectRequest(options.s3Bucket,options.s3Key , options.jarPath);
        s3.putObject(putRequest);
    }
*/
}
