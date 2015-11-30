package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makotan on 2015/11/16.
 */
public class AmazonApiGatewayIntegration {
    public String type;
    public String uri;
    public String httpMethod;
    public String credentials;
    public Map<String,String> requestTemplates = new HashMap<>();
    public Map<String,String> requestParameters = new HashMap<>();
    public String cacheNamespace;
    public List<String> cacheKeyParameters = new ArrayList<>();
    public Map<String,AmazonApiGatewayIntegrationResponse> responses = new HashMap<>();

    public AmazonApiGatewayIntegration type(String type) {
        this.type = type;
        return this;
    }

    public AmazonApiGatewayIntegration uri(String uri) {
        this.uri = uri;
        return this;
    }

    public AmazonApiGatewayIntegration httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public AmazonApiGatewayIntegration credentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

    public AmazonApiGatewayIntegration requestTemplates(Map<String, String> requestTemplates) {
        this.requestTemplates = requestTemplates;
        return this;
    }

    public AmazonApiGatewayIntegration addRequestTemplates(String mime , String template) {
        if (this.requestTemplates == null) {
            this.requestTemplates = new HashMap<>();
        }
        this.requestTemplates.put(mime , template);
        return this;
    }

    public AmazonApiGatewayIntegration requestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
        return this;
    }

    public AmazonApiGatewayIntegration addRequestParameters(String integration , String method) {
        if (this.requestParameters == null) {
            this.requestParameters = new HashMap<>();
        }
        this.requestParameters.put(integration, method);
        return this;
    }

    public AmazonApiGatewayIntegration cacheNamespace(String cacheNamespace) {
        this.cacheNamespace = cacheNamespace;
        return this;
    }

    public AmazonApiGatewayIntegration cacheKeyParameters(List<String> cacheKeyParameters) {
        this.cacheKeyParameters = cacheKeyParameters;
        return this;
    }

    public AmazonApiGatewayIntegration addCacheKeyParameters(String cacheKeyParameter) {
        if (this.cacheKeyParameters == null) {
            this.cacheKeyParameters = new ArrayList<>();
        }
        this.cacheKeyParameters.add(cacheKeyParameter);
        return this;
    }

    public AmazonApiGatewayIntegration responses(Map<String, AmazonApiGatewayIntegrationResponse> responses) {
        this.responses = responses;
        return this;
    }

    public AmazonApiGatewayIntegration addResponses(String searchKey, AmazonApiGatewayIntegrationResponse response) {
        if (this.responses == null) {
            this.responses = new HashMap<>();
        }
        this.responses.put(searchKey, response);
        return this;
    }
}
