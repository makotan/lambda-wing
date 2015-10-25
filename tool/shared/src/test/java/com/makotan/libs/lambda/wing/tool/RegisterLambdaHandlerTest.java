package com.makotan.libs.lambda.wing.tool;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.CreateFunctionResult;
import com.amazonaws.services.lambda.model.GetFunctionResult;
import com.amazonaws.services.lambda.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeResult;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterInfo;
import com.makotan.libs.lambda.wing.tool.model.LambdaRegisterResult;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by makotan on 2015/10/24.
 */
public class RegisterLambdaHandlerTest {


    @Test
    public void createRegisterInfoTest() {
        RegisterLambdaHandler handler = new RegisterLambdaHandler();
        LambdaRegisterInfo info = new LambdaRegisterInfo();
        HandlerFinder finder = new HandlerFinder();
        Set<Method> methods = finder.find("com.makotan.sample.lambda.wing.one");
        LambdaRegisterInfo lambdaRegisterInfo = handler.createRegisterInfo(info, methods.iterator().next());
        assertThat(lambdaRegisterInfo.handler , is("com.makotan.sample.lambda.wing.one.LambdaHandler:call"));
        assertThat(lambdaRegisterInfo.functionName , is("call"));
    }

    @Test
    public void newRegisterHandler() {
        AWSLambda lambda = mock(AWSLambda.class);
        RegisterLambdaHandler handler = spy(RegisterLambdaHandler.class);
        doReturn(lambda).when(handler).getAWSLambda(any());

        GetFunctionResult callResult = new GetFunctionResult();
        when(lambda.getFunction(any())).thenThrow(new ResourceNotFoundException("")).thenReturn(callResult);

        CreateFunctionResult createResult = new CreateFunctionResult();
        when(lambda.createFunction(any())).thenReturn(createResult);

        UpdateFunctionCodeResult updateResult = new UpdateFunctionCodeResult();
        when(lambda.updateFunctionCode(any())).thenReturn(updateResult);

        LambdaRegisterInfo info = new LambdaRegisterInfo();
        HandlerFinder finder = new HandlerFinder();
        Set<Method> methods = finder.find("com.makotan.sample.lambda.wing.one");
        List<LambdaRegisterResult> resultList = handler.register(info, methods);
        assertThat(resultList.size(),is(1));
        assertThat(resultList.get(0).result,is(callResult));

        verify(lambda).createFunction(any());

    }

    @Test
    public void updateRegisterHandler() {
        AWSLambda lambda = mock(AWSLambda.class);
        RegisterLambdaHandler handler = spy(RegisterLambdaHandler.class);
        doReturn(lambda).when(handler).getAWSLambda(any());

        GetFunctionResult callResult = new GetFunctionResult();
        when(lambda.getFunction(any())).thenReturn(callResult).thenReturn(callResult);

        CreateFunctionResult createResult = new CreateFunctionResult();
        when(lambda.createFunction(any())).thenReturn(createResult);

        UpdateFunctionCodeResult updateResult = new UpdateFunctionCodeResult();
        when(lambda.updateFunctionCode(any())).thenReturn(updateResult);

        LambdaRegisterInfo info = new LambdaRegisterInfo();
        HandlerFinder finder = new HandlerFinder();
        Set<Method> methods = finder.find("com.makotan.sample.lambda.wing.one");
        List<LambdaRegisterResult> resultList = handler.register(info, methods);
        assertThat(resultList.size(),is(1));
        assertThat(resultList.get(0).result,is(callResult));

        verify(lambda).updateFunctionCode(any());
    }
}
