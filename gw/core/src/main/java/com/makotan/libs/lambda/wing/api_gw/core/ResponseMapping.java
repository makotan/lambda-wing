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
     * コンテンツタイプを文字列で
     * @return
     */
    String contentType() default "application/json";

    /**
     * リクエストをマッピングするための個別テンプレート
     * 無いときはLambdaの引数情報から自動で作る。作れないときはエラー
     * @return
     */
    String requestMappingTemplate();
}
