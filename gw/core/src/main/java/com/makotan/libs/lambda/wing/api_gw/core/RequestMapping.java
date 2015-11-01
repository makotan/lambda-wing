package com.makotan.libs.lambda.wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/01.
 */
@Repeatable(RequestMappingAnnotation.class)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface RequestMapping {
    /**
     * マッピングで使うステータスコード
     * @return
     */
    int statusCode() default 200;

    /**
     * コンテンツタイプを文字列で
     * @return
     */
    String contentType() default "application/json";

    /**
     * レスポンスをマッピングするための個別テンプレート
     * 無いときはLambdaの戻りの型情報から自動で作る。作れないときはエラー
     * @return
     */
    String responseMappingTemplate();
}
