package org.lambda_wing.tool;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;


/**
 * Created by makotan on 2015/10/29.
 */
public class ApiGatewayModelUtil {
    public Map<String,String> createFunctionModels(Set<Method> methodSet) {
        HashMap<String,String> ret = new HashMap<>();
        methodSet.stream().forEach(method -> {
            putJsonSchema(ret , method.getReturnType());
            putJsonSchema(ret , method.getParameterTypes()[0]);
        });
        return ret;
    }

    void putJsonSchema(HashMap<String,String> map , Class<?> baseClass) {
        ObjectMapper mapper = new ObjectMapper();
        // TODO:awsのパッケージとPrimitive(void含む)とStreamを除外する
        try {
            SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
            mapper.acceptJsonFormatVisitor(baseClass, visitor);
            JsonSchema schema = visitor.finalSchema();
            String jsonS = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
            map.put(baseClass.getCanonicalName() , jsonS);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
