package com.makotan.libs.lambda.wing.api_gw.core;

import io.swagger.annotations.SwaggerDefinition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/19.
 */
@Target( { ElementType.PACKAGE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInfo {
    String title();
    String description() default "";
    String version();
    String host();
    SwaggerDefinition.Scheme[] scheme() default {SwaggerDefinition.Scheme.HTTP,SwaggerDefinition.Scheme.HTTPS};
    String basePath() default "";
}
