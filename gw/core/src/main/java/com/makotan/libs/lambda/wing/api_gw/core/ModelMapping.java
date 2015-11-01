package com.makotan.libs.lambda.wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/01.
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelMapping {
    /**
     * 変換元からデータを取得するときに使うJsonPath表現
     * 変換元と変換先で複雑な変換を必要としない($inputRoot.フィールド名で取得出来、型も同じ)場合は自動で作れるようにしたい
     * @return
     */
    String value();
}
