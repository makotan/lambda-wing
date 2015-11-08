package com.makotan.libs.lambda.wing.tool.apigateway.model;

/**
 * Created by makotan on 2015/11/06.
 */
public class MappingTemplate {
    public boolean inputPassthrough;
    public String template;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MappingTemplate that = (MappingTemplate) o;

        if (inputPassthrough != that.inputPassthrough) return false;
        return !(template != null ? !template.equals(that.template) : that.template != null);

    }

    @Override
    public int hashCode() {
        int result = (inputPassthrough ? 1 : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MappingTemplate{" +
                "inputPassthrough=" + inputPassthrough +
                ", template='" + template + '\'' +
                '}';
    }
}
