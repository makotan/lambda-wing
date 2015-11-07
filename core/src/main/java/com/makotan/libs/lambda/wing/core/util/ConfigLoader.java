package com.makotan.libs.lambda.wing.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ConfigLoader<p/>
 * functionNameとバージョン(alias)を元にconfigファイルを読み込んで設定する<br>
 * Handlerでの初期化方法はHandlerのクラスで↓を記述。あとは普通のpropertiesとして操作可能<br>
 * <code>
 *     final Properties properties = ConfigLoader.getInstance().getProperties();
 * </code>
 * <p/>
 * UnitTestで書くときはこんな感じにすると、ConfigLoaderTestの設定をfunction"func"でバージョンは"test"(これは固定)として読込<br>
 *
 * <code>
 *     ConfigLoader loader = new ConfigLoader(ConfigLoaderTest.class,"func");
 * </code>
 * 読込のルール<p/>
 * <pre>
 *     /application.properties
 *     /application_version..properties
 *     /function.properties
 *     /function_version..properties
 * </pre>
 */
public class ConfigLoader {
    static ConfigLoader instance = new ConfigLoader();

    final Properties properties = new Properties();
    private static String versionName;
    boolean updateVersion;

    public ConfigLoader() {
        String functionVersion = getEnv("AWS_LAMBDA_FUNCTION_VERSION");
        String functionName = getEnv("AWS_LAMBDA_FUNCTION_NAME");
        updateVersion = versionName == null || !versionName.equals(functionVersion);
        versionName = functionVersion;
        loadFunctionProperties("/" ,functionName , functionVersion);
    }

    String getEnv(String key) {
        return System.getProperty(key , System.getenv(key));
    }
    
    // テスト用
    public ConfigLoader(Class<?> testClass , String functionName) {
        loadFunctionProperties("/" ,functionName , "test");
        if (testClass != null) {
            loadFunctionProperties(classNameToPath(testClass), functionName, "test");

            loadFunctionProperties(classNameToSlashPath(testClass), functionName, "test");

            loadFunctionProperties(classNameToPackageAndSlashPath(testClass), functionName, "test");
        }
    }

    String classNameToPath(Class<?> testClass) {
        String name = testClass.getCanonicalName();
        return "/" + name + "/";
    }

    String classNameToSlashPath(Class<?> testClass) {
        String name = testClass.getCanonicalName().replaceAll("\\.", "/");
        return "/" + name + "/";
    }

    String classNameToPackageAndSlashPath(Class<?> testClass) {
        String name = testClass.getPackage().getName() + "/" + testClass.getSimpleName();
        return "/" + name + "/";
    }

    boolean loadFunctionProperties(String prefix, String functionName , String functionVersion) {
        boolean ret = loadFromResource(prefix+"application.properties");
        if (functionVersion != null && ! functionVersion.isEmpty()) {
            ret = ret | loadFromResource(prefix + "application_" + functionVersion + ".properties");
        }
        if (functionName != null && ! functionName.isEmpty()) {
            ret = ret | loadFromResource(prefix + functionName + ".properties");
            if (functionVersion != null && ! functionVersion.isEmpty()) {
                ret = ret | loadFromResource(prefix + functionName + "_" + functionVersion + ".properties");
            }
        }
        return ret;
    }

    boolean loadFromResource(String name) {
        try (InputStream inputStream = Object.class.getResourceAsStream(name)) {
            if (inputStream == null) {
                return false;
            }
            properties.load(inputStream);
            inputStream.close();
            return true;
        } catch (IOException e) {
            System.out.println( ConfigLoader.class.getName() + " not load " + name + " " + e.toString());
            return false;
        }
    }

    static ConfigLoader checkInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        } else {
            String functionVersion = instance.getEnv("AWS_LAMBDA_FUNCTION_VERSION");
            if (! versionName.equals(functionVersion)) {
                instance = new ConfigLoader();
            }
        }
        return instance;
    }
    
    public static ConfigLoader getInstance() {
        instance = checkInstance();
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    public boolean isUpdateVersion() {
        return updateVersion;
    }
}
