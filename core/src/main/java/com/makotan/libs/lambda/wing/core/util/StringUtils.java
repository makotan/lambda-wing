package com.makotan.libs.lambda.wing.core.util;

/**
 * User: makotan
 * Date: 2015/11/08
 */
public class StringUtils {
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return ! isEmpty(value);
    }
    
    public static boolean equals(String val1 , String val2) {
        if (val1 == null) {
            return val2 == null;
        } else {
            return val1.equals(val2);
        }
    }
    
    public static boolean notEquals(String val1 , String val2) {
        return ! equals(val1,val2);
    }
}
