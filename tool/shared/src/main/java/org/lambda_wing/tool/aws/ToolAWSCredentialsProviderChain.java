package org.lambda_wing.tool.aws;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * Created by makotan on 2015/10/24.
 */
public class ToolAWSCredentialsProviderChain extends AWSCredentialsProviderChain {
    public ToolAWSCredentialsProviderChain(String profile) {
        super(new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider(),
                profile == null ? new ProfileCredentialsProvider() : new ProfileCredentialsProvider(profile),
                new InstanceProfileCredentialsProvider());
    }
    public ToolAWSCredentialsProviderChain() {
        super(new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider(),
                new ProfileCredentialsProvider(),
                new InstanceProfileCredentialsProvider());
    }
}