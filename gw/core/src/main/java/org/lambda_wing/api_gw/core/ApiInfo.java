package org.lambda_wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/19.
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInfo {
    String title();
    String description() default "";
    String version();
    String host();
    String[] scheme() default {"http","https"};
    String basePath() default "";
    String[] produces() default {"application/json; charset=UTF-8"};
    String[] consumes() default {"application/json; charset=UTF-8"};
}
