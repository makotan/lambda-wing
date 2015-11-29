package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makotan on 2015/11/16.
 */
public class AmazonApigatewayIntegration {
    public String type;
    public String uri;
    public String httpMethod;
    public String credentials;
    public Map<String,String> requestTemplates = new HashMap<>();
    public Map<String,String> requestParameters = new HashMap<>();
    public String cacheNamespace;
    public List<String> cacheKeyParameters = new ArrayList<>();
    public Map<String,AmazonApiGatewayIntegrationResponse> responses = new HashMap<>();

    public AmazonApigatewayIntegration type(String type) {
        this.type = type;
        return this;
    }

    public AmazonApigatewayIntegration uri(String uri) {
        this.uri = uri;
        return this;
    }

    public AmazonApigatewayIntegration httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public AmazonApigatewayIntegration credentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

    public AmazonApigatewayIntegration requestTemplates(Map<String, String> requestTemplates) {
        this.requestTemplates = requestTemplates;
        return this;
    }

    public AmazonApigatewayIntegration addRequestTemplates(String mime , String template) {
        if (this.requestTemplates == null) {
            this.requestTemplates = new HashMap<>();
        }
        this.requestTemplates.put(mime , template);
        return this;
    }

    public AmazonApigatewayIntegration requestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
        return this;
    }

    public AmazonApigatewayIntegration addRequestParameters(String integration , String method) {
        if (this.requestParameters == null) {
            this.requestParameters = new HashMap<>();
        }
        this.requestParameters.put(integration, method);
        return this;
    }

    public AmazonApigatewayIntegration cacheNamespace(String cacheNamespace) {
        this.cacheNamespace = cacheNamespace;
        return this;
    }

    public AmazonApigatewayIntegration cacheKeyParameters(List<String> cacheKeyParameters) {
        this.cacheKeyParameters = cacheKeyParameters;
        return this;
    }

    public AmazonApigatewayIntegration addCacheKeyParameters(String cacheKeyParameter) {
        if (this.cacheKeyParameters == null) {
            this.cacheKeyParameters = new ArrayList<>();
        }
        this.cacheKeyParameters.add(cacheKeyParameter);
        return this;
    }

    public AmazonApigatewayIntegration responses(Map<String, AmazonApiGatewayIntegrationResponse> responses) {
        this.responses = responses;
        return this;
    }

    public AmazonApigatewayIntegration addResponses(String searchKey, AmazonApiGatewayIntegrationResponse response) {
        if (this.responses == null) {
            this.responses = new HashMap<>();
        }
        this.responses.put(searchKey, response);
        return this;
    }
}
