package com.makotan.libs.lambda.wing.tool.model;

/**
 * Created by makotan on 2015/10/26.
 */
public class LambdaRegisterInfo {
    public String profile;
    public String region;
    public String functionName;
    public String role;
    public String handler;
    public int timeout;
    public int memory;
    public String s3Bucket;
    public String s3Key;
    public boolean publishVersion;

    public LambdaRegisterInfo copy() {
        LambdaRegisterInfo info = new LambdaRegisterInfo();
        info.profile = profile;
        info.region = region;
        info.role = role;
        info.s3Bucket = s3Bucket;
        info.s3Key = s3Key;
        info.publishVersion = publishVersion;
        return info;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "profile='" + profile + '\'' +
                ", region='" + region + '\'' +
                ", functionName='" + functionName + '\'' +
                ", role='" + role + '\'' +
                ", handler='" + handler + '\'' +
                ", timeout=" + timeout +
                ", memory=" + memory +
                ", s3Bucket='" + s3Bucket + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", publishVersion=" + publishVersion +
                '}';
    }
}
