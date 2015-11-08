package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.List;
import java.util.Map;

/**
 * Created by makotan on 2015/11/06.
 */
public class MethodResponseModel {
    public int statusCode = 200;
    public List<String> responseHeaders;
    // contentType , DataModel
    public Map<String,ApiDataModel> contentTypeResponseModels;

    public IntegrationResponseModel integrationResponseModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodResponseModel that = (MethodResponseModel) o;

        if (statusCode != that.statusCode) return false;
        if (responseHeaders != null ? !responseHeaders.equals(that.responseHeaders) : that.responseHeaders != null)
            return false;
        if (contentTypeResponseModels != null ? !contentTypeResponseModels.equals(that.contentTypeResponseModels) : that.contentTypeResponseModels != null)
            return false;
        return !(integrationResponseModel != null ? !integrationResponseModel.equals(that.integrationResponseModel) : that.integrationResponseModel != null);

    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (responseHeaders != null ? responseHeaders.hashCode() : 0);
        result = 31 * result + (contentTypeResponseModels != null ? contentTypeResponseModels.hashCode() : 0);
        result = 31 * result + (integrationResponseModel != null ? integrationResponseModel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MethodResponseModel{" +
                "statusCode=" + statusCode +
                ", responseHeaders=" + responseHeaders +
                ", contentTypeResponseModels=" + contentTypeResponseModels +
                ", integrationResponseModel=" + integrationResponseModel +
                '}';
    }
}
