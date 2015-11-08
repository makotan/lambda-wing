package com.makotan.libs.lambda.wing.tool.apigateway.model;

import java.util.Map;

/**
 * Created by makotan on 2015/11/06.
 */
public class IntegrationRequestLambdaModel  extends IntegrationRequestModel{
    public String lambdaRegion;
    public String function;
    public Boolean invokeWithCallerCredentials;
    public boolean credentialsCache;
    public Map<String,Object> mappingTemplates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegrationRequestLambdaModel that = (IntegrationRequestLambdaModel) o;

        if (credentialsCache != that.credentialsCache) return false;
        if (lambdaRegion != null ? !lambdaRegion.equals(that.lambdaRegion) : that.lambdaRegion != null) return false;
        if (function != null ? !function.equals(that.function) : that.function != null) return false;
        if (invokeWithCallerCredentials != null ? !invokeWithCallerCredentials.equals(that.invokeWithCallerCredentials) : that.invokeWithCallerCredentials != null)
            return false;
        return !(mappingTemplates != null ? !mappingTemplates.equals(that.mappingTemplates) : that.mappingTemplates != null);

    }

    @Override
    public int hashCode() {
        int result = lambdaRegion != null ? lambdaRegion.hashCode() : 0;
        result = 31 * result + (function != null ? function.hashCode() : 0);
        result = 31 * result + (invokeWithCallerCredentials != null ? invokeWithCallerCredentials.hashCode() : 0);
        result = 31 * result + (credentialsCache ? 1 : 0);
        result = 31 * result + (mappingTemplates != null ? mappingTemplates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IntegrationRequestLambdaModel{" +
                "lambdaRegion='" + lambdaRegion + '\'' +
                ", function='" + function + '\'' +
                ", invokeWithCallerCredentials=" + invokeWithCallerCredentials +
                ", credentialsCache=" + credentialsCache +
                ", mappingTemplates=" + mappingTemplates +
                '}';
    }
}
