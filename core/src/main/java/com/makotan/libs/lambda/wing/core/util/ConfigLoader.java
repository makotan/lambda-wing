package com.makotan.libs.lambda.wing.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by makotan on 2015/10/21.
 */
public class ConfigLoader {
    static ConfigLoader instance = new ConfigLoader();

    final Properties properties = new Properties();

    public ConfigLoader() {
        String functionVersion = System.getenv("AWS_LAMBDA_FUNCTION_VERSION");
        String functionName = System.getenv("AWS_LAMBDA_FUNCTION_NAME");
        loadFunctionProperties("/" ,functionName , functionVersion);
    }

    // テスト用
    public ConfigLoader(Class<?> testClass , String functionName) {
        loadFunctionProperties("/" ,functionName , "test");
        if (testClass != null) {
            String name = testClass.getCanonicalName();
            loadFunctionProperties("/" + name + "/", functionName, "test");

            name = testClass.getCanonicalName().replaceAll("\\.", "/");
            loadFunctionProperties("/" + name + "/", functionName, "test");

            name = testClass.getPackage().getName() + "/" + testClass.getSimpleName();
            loadFunctionProperties("/" + name + "/", functionName, "test");
        }
    }

    void loadFunctionProperties(String prefix, String functionName , String functionVersion) {
        loadFromResource(prefix+"application.properties");
        loadFromResource(prefix+"application_"+functionVersion+".properties");
        loadFromResource(prefix+functionName+".properties");
        loadFromResource(prefix+functionName+"_"+functionVersion+".properties");

    }

    void loadFromResource(String name) {
        try (InputStream inputStream = Object.class.getResourceAsStream(name)) {
            if (inputStream == null) {
                return;
            }
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            System.out.println( ConfigLoader.class.getName() + " not load " + name + " " + e.toString());
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
