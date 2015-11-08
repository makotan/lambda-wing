package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.Map;

/**
 * Created by makotan on 2015/11/06.
 */
public class IntegrationResponseModel {
    public String lambdaErrorRegex;
    public int methodResponseStatus = 200;
    public ApiDataModel outputModel;
    public boolean defaultMapping;
    // Header , path
    public Map<String,String> headerMappings;
    // contenttype , MappingTemplate
    public Map<String,MappingTemplate> mappingTemplates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegrationResponseModel that = (IntegrationResponseModel) o;

        if (methodResponseStatus != that.methodResponseStatus) return false;
        if (defaultMapping != that.defaultMapping) return false;
        if (lambdaErrorRegex != null ? !lambdaErrorRegex.equals(that.lambdaErrorRegex) : that.lambdaErrorRegex != null)
            return false;
        if (outputModel != null ? !outputModel.equals(that.outputModel) : that.outputModel != null) return false;
        if (headerMappings != null ? !headerMappings.equals(that.headerMappings) : that.headerMappings != null)
            return false;
        return !(mappingTemplates != null ? !mappingTemplates.equals(that.mappingTemplates) : that.mappingTemplates != null);

    }

    @Override
    public int hashCode() {
        int result = lambdaErrorRegex != null ? lambdaErrorRegex.hashCode() : 0;
        result = 31 * result + methodResponseStatus;
        result = 31 * result + (outputModel != null ? outputModel.hashCode() : 0);
        result = 31 * result + (defaultMapping ? 1 : 0);
        result = 31 * result + (headerMappings != null ? headerMappings.hashCode() : 0);
        result = 31 * result + (mappingTemplates != null ? mappingTemplates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IntegrationResponseModel{" +
                "lambdaErrorRegex='" + lambdaErrorRegex + '\'' +
                ", methodResponseStatus=" + methodResponseStatus +
                ", outputModel=" + outputModel +
                ", defaultMapping=" + defaultMapping +
                ", headerMappings=" + headerMappings +
                ", mappingTemplates=" + mappingTemplates +
                '}';
    }
}
