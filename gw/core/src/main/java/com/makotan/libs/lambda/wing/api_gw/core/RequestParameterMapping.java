package com.makotan.libs.lambda.wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/20.
 */
@Repeatable(RequestParameterMappingAnnotation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestParameterMapping {
    enum InType {
        query,header,path
    }
    String name();
    InType in();
    String description() default "";
    boolean required() default false;
    String integrationParam();
    String methodParam();
}
