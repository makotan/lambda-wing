package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.List;

/**
 * Created by makotan on 2015/11/06.
 */
public class ResourcesModel {
    public String path;

    public List<ResourcesMethodModel> resourcesMethodModels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourcesModel that = (ResourcesModel) o;

        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return !(resourcesMethodModels != null ? !resourcesMethodModels.equals(that.resourcesMethodModels) : that.resourcesMethodModels != null);

    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (resourcesMethodModels != null ? resourcesMethodModels.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourcesModel{" +
                "path='" + path + '\'' +
                ", resourcesMethodModels=" + resourcesMethodModels +
                '}';
    }
}
