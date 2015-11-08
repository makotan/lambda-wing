package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.Map;

/**
 * Created by makotan on 2015/11/06.
 */
public class MethodRequestModel {
    public String authorizationType;
    public boolean apiKeyRequired = false;
    // QueryString , useCache
    public Map<String,Boolean> urlQueryStringParameters;
    // RequestHeader, useCache
    public Map<String,Boolean> httpRequestHeaders;
    // contentType , dataModel
    public Map<String,ApiDataModel> requestModels;

    public IntegrationRequestModel integrationRequestModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodRequestModel that = (MethodRequestModel) o;

        if (apiKeyRequired != that.apiKeyRequired) return false;
        if (authorizationType != null ? !authorizationType.equals(that.authorizationType) : that.authorizationType != null)
            return false;
        if (urlQueryStringParameters != null ? !urlQueryStringParameters.equals(that.urlQueryStringParameters) : that.urlQueryStringParameters != null)
            return false;
        if (httpRequestHeaders != null ? !httpRequestHeaders.equals(that.httpRequestHeaders) : that.httpRequestHeaders != null)
            return false;
        if (requestModels != null ? !requestModels.equals(that.requestModels) : that.requestModels != null)
            return false;
        return !(integrationRequestModel != null ? !integrationRequestModel.equals(that.integrationRequestModel) : that.integrationRequestModel != null);

    }

    @Override
    public int hashCode() {
        int result = authorizationType != null ? authorizationType.hashCode() : 0;
        result = 31 * result + (apiKeyRequired ? 1 : 0);
        result = 31 * result + (urlQueryStringParameters != null ? urlQueryStringParameters.hashCode() : 0);
        result = 31 * result + (httpRequestHeaders != null ? httpRequestHeaders.hashCode() : 0);
        result = 31 * result + (requestModels != null ? requestModels.hashCode() : 0);
        result = 31 * result + (integrationRequestModel != null ? integrationRequestModel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MethodRequestModel{" +
                "authorizationType='" + authorizationType + '\'' +
                ", apiKeyRequired=" + apiKeyRequired +
                ", urlQueryStringParameters=" + urlQueryStringParameters +
                ", httpRequestHeaders=" + httpRequestHeaders +
                ", requestModels=" + requestModels +
                ", integrationRequestModel=" + integrationRequestModel +
                '}';
    }
}
