package com.makotan.sample.lambda.wing.one;

import com.amazonaws.services.apigateway.model.NotFoundException;
import com.makotan.libs.lambda.wing.api_gw.core.LambdaBackend;
import com.makotan.libs.lambda.wing.api_gw.core.RequestBodyMapping;
import com.makotan.libs.lambda.wing.api_gw.core.RequestParameterMapping;
import com.makotan.libs.lambda.wing.api_gw.core.ResponseMapping;
import com.makotan.libs.lambda.wing.api_gw.core.ResponseParameterMapping;
import com.makotan.libs.lambda.wing.api_gw.core.ResponseTemplateMapping;
import com.makotan.libs.lambda.wing.api_gw.core.RestMethod;
import com.makotan.libs.lambda.wing.api_gw.core.RestResource;

/**
 * Created by makotan on 2015/11/01.
 */
@RestResource("/")
public interface ApiGateway {

    @RestMethod(httpMethod = RestMethod.HttpMethod.POST)
    @LambdaBackend(functionName = "handleRequest")
    @RequestBodyMapping(requestMappingTemplate = "#set($inputRoot = $input.path('$'))\n" +
            "{\n" +
            "  \"firstName\" : \"${inputRoot.firstName}\",\n" +
            "  \"lastName\" : \"${inputRoot.lastName}\"\n" +
            "}")
    @RequestParameterMapping(in = RequestParameterMapping.InType.header , name = "x-makotan-v1" , integrationParam = "integration.request.header.x-makotan-v1" , methodParam = "method.request.header.x-makotan-v1")
    @ResponseMapping( responseClass = SimpleApiGatewayResponse.class ,
            parameter = {@ResponseParameterMapping(methodParam = "method.response.header.x-makotan-v2" , integrationParam = "integration.response.header.x-makotan-v2")},
            template = {@ResponseTemplateMapping(template = "#set($inputRoot = $input.path('$'))\n" +
                    "{\n" +
                    "  \"fullName\" : \"${inputRoot.fullName}\"\n" +
                    "}") ,
            }
    )
    @ResponseMapping( statusCode = 404, searchKey = "NotFoundException" , responseClass = SimpleApiGatewayErrorResponse.class ,
            parameter = {@ResponseParameterMapping(methodParam = "method.response.header.x-makotan-v2" , integrationParam = "integration.response.header.x-makotan-v2")},
            template = {@ResponseTemplateMapping(template = "#set($inputRoot = $input.path('$'))\n" +
                    "{\n" +
                    "  \"message\" : \"${inputRoot.error.headMessage}\"\n" +
                    "}") ,
            }
    )
    SimpleApiGatewayResponse getFullName(SimpleApiGatewayRequest request);

    @RestMethod(httpMethod = RestMethod.HttpMethod.PUT)
    @LambdaBackend(functionName = "handleRequest")
    @ResponseMapping( statusCode = 404, searchKey = "NotFoundException" , responseClass = SimpleApiGatewayErrorResponse.class )
    SimpleApiGatewayResponse simple(SimpleApiGatewayRequest request);

}
