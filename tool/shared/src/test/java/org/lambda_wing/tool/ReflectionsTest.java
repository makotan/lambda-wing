package org.lambda_wing.tool;


import com.amazonaws.services.lambda.runtime.Context;
import org.lambda_wing.lambda.core.LambdaHandler;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;


/**
 * Created by makotan on 2015/10/23.
 */
public class ReflectionsTest {
    @Test
    public void find() {
        Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .addClassLoader(getClass().getClassLoader())
                        .filterInputsBy(name -> name.startsWith("org.lambda_wing.tool"))
                        .forPackages("org.lambda_wing.tool")
                        .addScanners(new MethodAnnotationsScanner())
                );
        Set<Method> methodSet = reflections.getMethodsAnnotatedWith(LambdaHandler.class);
        Method method = methodSet.stream().findFirst().get();
        System.out.println(method.getDeclaringClass());
        System.out.println(method.getParameterTypes()[0]);
        System.out.println(method.getParameterTypes()[1]);
        System.out.println(method.getReturnType());
    }

    @LambdaHandler(value = "findtest")
    public String ha(Integer v , Context c) {
        return ""  + v;
    }

    //@Test
    public void findJar() throws MalformedURLException {
        URL url = new File("../../sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar").toURI().toURL();
        Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .addUrls(url)
                        //.addClassLoader(URLClassLoader.newInstance(new URL[]{url}))
                        //.addClassLoader(getClass().getClassLoader())
                        .filterInputsBy(name -> name.startsWith("org.lambda_wing.sample1"))
                        .forPackages("org.lambda_wing.sample1")
                        .addScanners(new MethodAnnotationsScanner())
                );
        Set<Method> methodSet = reflections.getMethodsAnnotatedWith(LambdaHandler.class);
        System.out.println(methodSet);
        Method method = methodSet.stream().findFirst().get();
        System.out.println(method.getDeclaringClass());
        System.out.println(method.getParameterTypes()[0]);
        System.out.println(method.getParameterTypes()[1]);
        System.out.println(method.getReturnType());

        HandlerFinder handlerFinder = new HandlerFinder();
        Set<Method> methods = handlerFinder.find(url, "org.lambda_wing.sample1");
        System.out.println(methods);
    }

}
