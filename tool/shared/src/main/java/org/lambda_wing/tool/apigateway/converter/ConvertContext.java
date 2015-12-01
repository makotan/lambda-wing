package org.lambda_wing.tool.apigateway.converter;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makotan on 2015/11/29.
 */
public class ConvertContext {
    final Map<Class<? extends Annotation>, Object> context = new HashMap<>();
    final Map<String, Object> namedContext = new HashMap<>();

    public void putContext(Class<? extends Annotation> klass, Object val) {
        context.put(klass , val);
    }

    public Object get(Class<? extends Annotation> klass) {
        return context.get(klass);
    }


    public void putContext(String key, Object val) {
        namedContext.put(key , val);
    }

    public Object get(String key) {
        return namedContext.get(key);
    }

}
