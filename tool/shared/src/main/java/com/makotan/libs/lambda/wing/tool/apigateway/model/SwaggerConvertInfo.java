package com.makotan.libs.lambda.wing.tool.apigateway.model;

import com.makotan.libs.lambda.wing.core.util.StringUtils;

/**
 * Created by makotan on 2015/11/21.
 */
public class SwaggerConvertInfo {
    public String basePackage;
    public String lambdaAilas;
    public String awsAccountId;

    public String title;
    public String version;
    public String host;
    public String description = "";
    public String basePath = "";


    public String getBasePackage() {
        return basePackage;
    }

    public String getLambdaAilas() {
        return lambdaAilas;
    }

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public String getHost() {
        return host;
    }

    public String getDescription() {
        return description;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getAwsAccountId() {
        return awsAccountId;
    }
}
