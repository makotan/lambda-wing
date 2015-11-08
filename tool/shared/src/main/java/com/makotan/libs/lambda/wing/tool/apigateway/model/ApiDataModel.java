package com.makotan.libs.lambda.wing.tool.apigateway.model;

/**
 * Created by makotan on 2015/11/06.
 */
public class ApiDataModel {
    public String modelName;
    public String contentType;
    public Class<?> modelClass;
    public String jsonSchema;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiDataModel that = (ApiDataModel) o;

        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (modelClass != null ? !modelClass.equals(that.modelClass) : that.modelClass != null) return false;
        return !(jsonSchema != null ? !jsonSchema.equals(that.jsonSchema) : that.jsonSchema != null);

    }

    @Override
    public int hashCode() {
        int result = modelName != null ? modelName.hashCode() : 0;
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (modelClass != null ? modelClass.hashCode() : 0);
        result = 31 * result + (jsonSchema != null ? jsonSchema.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiDataModel{" +
                "modelName='" + modelName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", modelClass=" + modelClass +
                ", jsonSchema='" + jsonSchema + '\'' +
                '}';
    }
}
