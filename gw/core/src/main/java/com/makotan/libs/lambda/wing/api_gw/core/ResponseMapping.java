package com.makotan.libs.lambda.wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/01.
 */
@Repeatable(ResponseMappingAnnotation.class)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseMapping {
    /**
     * マッピングで使うステータスコード
     * @return
     */
    int statusCode() default 200;

    ResponseParameterMapping[] parameter() default {};

    ResponseTemplateMapping[] template() default {};


}
