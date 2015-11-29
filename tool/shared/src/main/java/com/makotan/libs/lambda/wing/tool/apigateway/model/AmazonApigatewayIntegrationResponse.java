package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by makotan on 2015/11/16.
 */
public class AmazonApigatewayIntegrationResponse {
    public String statusCode;
    public Map<String,String> responseParameters = new HashMap<>();
    public Map<String,String> responseTemplates = new HashMap<>();

    public AmazonApigatewayIntegrationResponse statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public AmazonApigatewayIntegrationResponse responseParameters(Map<String, String> responseParameters) {
        this.responseParameters = responseParameters;
        return this;
    }

    public AmazonApigatewayIntegrationResponse addResponseParameters(String method , String integration) {
        if (this.responseParameters == null) {
            this.responseParameters = new HashMap<>();
        }
        this.responseParameters.put(method, integration);
        return this;
    }

    public AmazonApigatewayIntegrationResponse responseTemplates(Map<String, String> responseTemplates) {
        this.responseTemplates = responseTemplates;
        return this;
    }

    public AmazonApigatewayIntegrationResponse addResponseTemplates(String mime , String template) {
        if (this.responseTemplates == null) {
            this.responseTemplates = new HashMap<>();
        }
        this.responseTemplates.put(mime, template);
        return this;
    }
}
