package org.lambda_wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/20.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseParameterMapping {
    String methodParam();
    String integrationParam();
}
