package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.List;
import java.util.Map;

/**
 * Created by makotan on 2015/11/06.
 */
public class ResourcesMethodModel {
    public String methodName;
    public MethodRequestModel requestModel;
    public List<MethodResponseModel> responseModels;
    // status code , response model
    public Map<Integer,MethodResponseModel> methodResponseModelMap;

    public List<IntegrationResponseModel> integrationResponseModels;
    // status code , IntegrationResponseModel
    public Map<Integer,IntegrationResponseModel> integrationResponseModelMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourcesMethodModel that = (ResourcesMethodModel) o;

        if (methodName != null ? !methodName.equals(that.methodName) : that.methodName != null) return false;
        if (requestModel != null ? !requestModel.equals(that.requestModel) : that.requestModel != null) return false;
        if (responseModels != null ? !responseModels.equals(that.responseModels) : that.responseModels != null)
            return false;
        if (methodResponseModelMap != null ? !methodResponseModelMap.equals(that.methodResponseModelMap) : that.methodResponseModelMap != null)
            return false;
        if (integrationResponseModels != null ? !integrationResponseModels.equals(that.integrationResponseModels) : that.integrationResponseModels != null)
            return false;
        return !(integrationResponseModelMap != null ? !integrationResponseModelMap.equals(that.integrationResponseModelMap) : that.integrationResponseModelMap != null);

    }

    @Override
    public int hashCode() {
        int result = methodName != null ? methodName.hashCode() : 0;
        result = 31 * result + (requestModel != null ? requestModel.hashCode() : 0);
        result = 31 * result + (responseModels != null ? responseModels.hashCode() : 0);
        result = 31 * result + (methodResponseModelMap != null ? methodResponseModelMap.hashCode() : 0);
        result = 31 * result + (integrationResponseModels != null ? integrationResponseModels.hashCode() : 0);
        result = 31 * result + (integrationResponseModelMap != null ? integrationResponseModelMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourcesMethodModel{" +
                "methodName='" + methodName + '\'' +
                ", requestModel=" + requestModel +
                ", responseModels=" + responseModels +
                ", methodResponseModelMap=" + methodResponseModelMap +
                ", integrationResponseModels=" + integrationResponseModels +
                ", integrationResponseModelMap=" + integrationResponseModelMap +
                '}';
    }
}
