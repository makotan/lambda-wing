package org.lambda_wing.tool.apigateway.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by makotan on 2015/11/16.
 */
public class AmazonApiGatewayIntegrationResponse {
    public String statusCode;
    public Map<String,String> responseParameters = new HashMap<>();
    public Map<String,String> responseTemplates = new HashMap<>();

    public AmazonApiGatewayIntegrationResponse statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public AmazonApiGatewayIntegrationResponse responseParameters(Map<String, String> responseParameters) {
        this.responseParameters = responseParameters;
        return this;
    }

    public AmazonApiGatewayIntegrationResponse addResponseParameters(String method , String integration) {
        if (this.responseParameters == null) {
            this.responseParameters = new HashMap<>();
        }
        this.responseParameters.put(method, integration);
        return this;
    }

    public AmazonApiGatewayIntegrationResponse responseTemplates(Map<String, String> responseTemplates) {
        this.responseTemplates = responseTemplates;
        return this;
    }

    public AmazonApiGatewayIntegrationResponse addResponseTemplates(String mime , String template) {
        if (this.responseTemplates == null) {
            this.responseTemplates = new HashMap<>();
        }
        this.responseTemplates.put(mime, template);
        return this;
    }
}
