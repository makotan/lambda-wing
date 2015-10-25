package com.makotan.libs.lambda.wing.tool;

import com.makotan.libs.lambda.wing.core.LambdaHandler;
import org.reflections.Reflections;
import org.reflections.adapters.JavaReflectionAdapter;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * Created by makotan on 2015/10/23.
 */
public class HandlerFinder {
    public Set<Method> find(String basePackage) {
        Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .addClassLoader(getClass().getClassLoader())
                        .filterInputsBy(name -> name.startsWith(basePackage))
                        .forPackages(basePackage)
                        .addScanners(new MethodAnnotationsScanner())
        );
        return reflections.getMethodsAnnotatedWith(LambdaHandler.class);
    }

    public Set<Method> find(URL url,String basePackage) {
        Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .addClassLoader(URLClassLoader.newInstance(new URL[]{url}))
                        .addUrls(url)
                        .filterInputsBy(name -> name.startsWith(basePackage))
                        .forPackages(basePackage)
                        .addScanners(new MethodAnnotationsScanner())
                );
        return reflections.getMethodsAnnotatedWith(LambdaHandler.class);
    }
}
