package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.List;
import java.util.Map;

/**
 * Created by makotan on 2015/11/06.
 */
public class ApiGatewayModel {
    public String name;

    public List<ResourcesModel> resourcesModels;
    public Map<String,ResourcesModel> resourcesNameModelMap;

    public List<ApiDataModel> apiDataModels;
    public Map<String,ApiDataModel> apiDataModelnameMap;
    public Map<Class<?>,ApiDataModel> apiDataModelClassMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiGatewayModel that = (ApiGatewayModel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (resourcesModels != null ? !resourcesModels.equals(that.resourcesModels) : that.resourcesModels != null)
            return false;
        if (resourcesNameModelMap != null ? !resourcesNameModelMap.equals(that.resourcesNameModelMap) : that.resourcesNameModelMap != null)
            return false;
        if (apiDataModels != null ? !apiDataModels.equals(that.apiDataModels) : that.apiDataModels != null)
            return false;
        if (apiDataModelnameMap != null ? !apiDataModelnameMap.equals(that.apiDataModelnameMap) : that.apiDataModelnameMap != null)
            return false;
        return !(apiDataModelClassMap != null ? !apiDataModelClassMap.equals(that.apiDataModelClassMap) : that.apiDataModelClassMap != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (resourcesModels != null ? resourcesModels.hashCode() : 0);
        result = 31 * result + (resourcesNameModelMap != null ? resourcesNameModelMap.hashCode() : 0);
        result = 31 * result + (apiDataModels != null ? apiDataModels.hashCode() : 0);
        result = 31 * result + (apiDataModelnameMap != null ? apiDataModelnameMap.hashCode() : 0);
        result = 31 * result + (apiDataModelClassMap != null ? apiDataModelClassMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiGatewayModel{" +
                "name='" + name + '\'' +
                ", resourcesModels=" + resourcesModels +
                ", resourcesNameModelMap=" + resourcesNameModelMap +
                ", apiDataModels=" + apiDataModels +
                ", apiDataModelnameMap=" + apiDataModelnameMap +
                ", apiDataModelClassMap=" + apiDataModelClassMap +
                '}';
    }
}
