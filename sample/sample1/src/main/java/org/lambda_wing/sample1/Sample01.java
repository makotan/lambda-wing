package org.lambda_wing.sample1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.lambda_wing.lambda.core.LambdaHandler;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by makotan on 2015/10/17.
 */
public class Sample01 implements RequestHandler<SimpleLambdaRequest,SimpleLambdaResponse> {
    static {
        System.out.println("Sample01 static call");
    }

    public Sample01() {
        System.out.println("Sample01 constructor call");
    }

    @Override
    @LambdaHandler
    public SimpleLambdaResponse handleRequest(SimpleLambdaRequest request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("RemainingTimeInMillis:" + context.getRemainingTimeInMillis());
        logger.log("AwsRequestId:" + context.getAwsRequestId());
        logger.log("FunctionName:" + context.getFunctionName());
        logger.log("LogGroupName:" + context.getLogGroupName());
        logger.log("LogStreamName:" + context.getLogStreamName());
        logger.log("ClientContext:" + context.getClientContext());
        logger.log("FunctionVersion:" + context.getFunctionVersion());
        logger.log("InvokedFunctionArn:" + context.getInvokedFunctionArn());
        logger.log("MemoryLimitInMB:" + context.getMemoryLimitInMB());
        logger.log("availableProcessors:" + Runtime.getRuntime().availableProcessors());
        logger.log("freeMemory:" + Runtime.getRuntime().freeMemory());
        logger.log("maxMemory:" + Runtime.getRuntime().maxMemory());
        logger.log("totalMemory:" + Runtime.getRuntime().totalMemory());
        logger.log("env:" + System.getenv());
        logger.log("Properties:" + System.getProperties());


        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                logger.log(" Index:" + networkInterface.getIndex());
                logger.log(" DisplayName:" + networkInterface.getDisplayName());
                logger.log(" Name:" + networkInterface.getName());
                logger.log(" InetAddresses:" + networkInterface.getInetAddresses());
                logger.log(" InterfaceAddresses:" + networkInterface.getInterfaceAddresses());
                logger.log(" Parent:" + networkInterface.getParent());
                logger.log(" SubInterfaces:" + networkInterface.getSubInterfaces());
                logger.log(" str:" + networkInterface.toString());
                try {
                    logger.log(" MTU:" + networkInterface.getMTU());
                    logger.log(" getHardwareAddress:" + Arrays.toString(networkInterface.getHardwareAddress()));
                    logger.log(" isLoopback:" + networkInterface.isLoopback());
                } catch (SocketException e) {
                    logger.log(e.toString());
                }

            }
        } catch (SocketException e) {
            logger.log(e.toString());
        }

        SimpleLambdaResponse res = new SimpleLambdaResponse();
        res.fullName = request.firstName + " " + request.lastName;
        res.status = "ok";
        logger.log("RemainingTimeInMillis:" + context.getRemainingTimeInMillis());
        return res;
    }


}
