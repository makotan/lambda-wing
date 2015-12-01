package org.lambda_wing.tool;


import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.CreateAliasResult;
import com.amazonaws.services.lambda.model.CreateFunctionResult;
import com.amazonaws.services.lambda.model.GetAliasResult;
import com.amazonaws.services.lambda.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.model.UpdateAliasResult;
import org.lambda_wing.tool.model.LambdaAliasRegister;
import org.lambda_wing.tool.model.LambdaAliasRegisterResult;
import org.lambda_wing.tool.model.LambdaRegisterResult;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by makotan on 2015/10/26.
 */
public class LambdaAliasTest {

    @Test
    public void createAliasTest() {
        AWSLambda lambda = mock(AWSLambda.class);
        LambdaAlias alias = Mockito.spy(LambdaAlias.class);
        doReturn(lambda).when(alias).getAWSLambda(any());


        GetAliasResult callResult = new GetAliasResult();
        when(lambda.getAlias(any())).thenThrow(new ResourceNotFoundException("")).thenReturn(callResult);


        CreateAliasResult createResult = new CreateAliasResult();
        when(lambda.createAlias(any())).thenReturn(createResult);

        UpdateAliasResult updateResult = new UpdateAliasResult();
        when(lambda.updateAlias(any())).thenReturn(updateResult);


        LambdaAliasRegister register = new LambdaAliasRegister();
        register.registerList = new ArrayList<>();
        LambdaRegisterResult rr = new LambdaRegisterResult();
        rr.createFunctionResult = new CreateFunctionResult().withFunctionName("test").withVersion("$LATEST");
        register.registerList.add(rr);

        List<LambdaAliasRegisterResult> lambdaAliasRegisterResults = alias.registerAlias(register);
        assertThat(lambdaAliasRegisterResults.size() , is(1));

        verify(lambda).createAlias(any());
    }

    @Test
    public void updateAliasTest() {
        AWSLambda lambda = mock(AWSLambda.class);
        LambdaAlias alias = Mockito.spy(LambdaAlias.class);
        doReturn(lambda).when(alias).getAWSLambda(any());


        GetAliasResult callResult = new GetAliasResult();
        when(lambda.getAlias(any())).thenReturn(callResult);


        CreateAliasResult createResult = new CreateAliasResult();
        when(lambda.createAlias(any())).thenReturn(createResult);

        UpdateAliasResult updateResult = new UpdateAliasResult();
        when(lambda.updateAlias(any())).thenReturn(updateResult);


        LambdaAliasRegister register = new LambdaAliasRegister();
        register.registerList = new ArrayList<>();
        LambdaRegisterResult rr = new LambdaRegisterResult();
        rr.createFunctionResult = new CreateFunctionResult().withFunctionName("test").withVersion("$LATEST");
        register.registerList.add(rr);

        List<LambdaAliasRegisterResult> lambdaAliasRegisterResults = alias.registerAlias(register);
        assertThat(lambdaAliasRegisterResults.size() , is(1));

        verify(lambda).updateAlias(any());
    }


}
