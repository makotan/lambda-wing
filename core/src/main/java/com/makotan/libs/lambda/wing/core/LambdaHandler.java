package com.makotan.libs.lambda.wing.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/10/23.
 */
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LambdaHandler {
    String value() default "";
    int mem() default 128;
    int time() default 15;
    String discription() default "";
}
