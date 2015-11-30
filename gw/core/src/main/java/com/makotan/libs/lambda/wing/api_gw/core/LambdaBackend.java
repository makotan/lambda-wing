package com.makotan.libs.lambda.wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/20.
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LambdaBackend {

    /**
     * Lambdaとして呼び出すFunction名
     * @return
     */
    String functionName();

    /**
     * Lambdaとして呼び出すFunctionのAlias名
     * 無ければグローバル設定を利用する
     * @return
     */
    String aliasName() default "";

    String region() default "";

    String credentials() default "";
    String cacheNamespace() default "";
    String[] cacheKeyParameters() default {};

}
