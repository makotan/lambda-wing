package org.lambda_wing.tool.apigateway.converter.gwcore;

import org.lambda_wing.api_gw.core.*;
import org.lambda_wing.lambda.core.exception.LambdaWingException;
import org.lambda_wing.lambda.core.util.Either;
import org.lambda_wing.lambda.core.util.StringUtils;
import org.lambda_wing.tool.apigateway.converter.ConvertContext;
import org.lambda_wing.tool.apigateway.converter.ConverterUtils;
import org.lambda_wing.tool.apigateway.converter.MethodToSwaggerConverter;
import org.lambda_wing.tool.apigateway.model.AmazonApiGatewayAuth;
import org.lambda_wing.tool.apigateway.model.AmazonApiGatewayIntegrationResponse;
import org.lambda_wing.tool.apigateway.model.AmazonApiGatewayIntegration;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertErrors;
import org.lambda_wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefResponse;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/11/29.
 */
public class RestMethodConverter implements MethodToSwaggerConverter {
    @Override
    public Class<? extends Annotation> scanAnnotation() {
        return RestMethod.class;
    }

    @Override
    public Either<SwaggerConvertErrors,Swagger> convert(Class<?> klass, Method method, SwaggerConvertInfo info, Either<SwaggerConvertErrors, Swagger>  swaggerEither, ConvertContext context) {
        if (swaggerEither.isLeft()) {
            return swaggerEither;
        }
        Swagger swagger = swaggerEither.getRight();
        Path path = (Path) context.get(RestResource.class);
        setMethod(method , path , swagger,info);
        return Either.right(swagger);
    }
    ModelConverters modelConverters = new ModelConverters();

    void setMethod(Method method , Path path , Swagger swagger,SwaggerConvertInfo info) {
        Operation operation = new Operation();
        RestMethod restMethod = method.getAnnotation(RestMethod.class);
        if (restMethod.httpMethod().equals(RestMethod.HttpMethod.GET)) {
            path.get(operation);
        } else if (restMethod.httpMethod().equals(RestMethod.HttpMethod.HEAD)) {
            path.head(operation);
        } else if (restMethod.httpMethod().equals(RestMethod.HttpMethod.POST)) {
            path.post(operation);
        } else if (restMethod.httpMethod().equals(RestMethod.HttpMethod.OPTIONS)) {
            path.options(operation);
        } else if (restMethod.httpMethod().equals(RestMethod.HttpMethod.PUT)) {
            path.put(operation);
        } else if (restMethod.httpMethod().equals(RestMethod.HttpMethod.DEELTE)) {
            path.delete(operation);
        } else if (restMethod.httpMethod().equals(RestMethod.HttpMethod.PATCH)) {
            path.patch(operation);
        }

        ConverterUtils.setCheckVal(operation::summary , restMethod.summary() , "", StringUtils::isEmpty);
        ConverterUtils.setCheckVal(operation::description , restMethod.description() , "", StringUtils::isEmpty);
        ConverterUtils.setConvertVal(operation::tags , restMethod.tag() , Arrays::asList , ConverterUtils::isEmpty);

        AmazonApiGatewayAuth auth = new AmazonApiGatewayAuth(restMethod.auth());
        operation.setVendorExtension("x-amazon-apigateway-auth" , auth);

        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        Class<?> bodyClass = null;
        if (requestMapping != null) {
            bodyClass = requestMapping.requestClass();
        } else {
            if (method.getParameterTypes().length == 1) {
                bodyClass = method.getParameterTypes()[0];
            }
        }

        if (bodyClass != null) {
            String requestClassName = addClassModel(swagger, bodyClass);
            BodyParameter param = new BodyParameter()
                    .schema(swagger.getDefinitions().get(requestClassName));
            operation.parameter(param);
        }

        AmazonApiGatewayIntegration integration = new AmazonApiGatewayIntegration();

        RequestBodyMappingAnnotation requestBodyMappingAnnotation = method.getAnnotation(RequestBodyMappingAnnotation.class);
        if (requestBodyMappingAnnotation != null && requestBodyMappingAnnotation.value() != null && requestBodyMappingAnnotation.value().length != 0) {
            List<String> contentTypes = Arrays.stream(requestBodyMappingAnnotation.value()).map(RequestBodyMapping::contentType).collect(Collectors.toList());
            ConverterUtils.setCheckVal(swagger::consumes , contentTypes , ConverterUtils::isEmpty);
            Arrays.stream(requestBodyMappingAnnotation.value()).forEach( body -> {
                String template = body.requestMappingTemplate();
                if (StringUtils.isEmpty(template)) {
                    template = ConverterUtils.readResource(body.requestMappingTemplateFile());
                }
                if (StringUtils.isNotEmpty(template)) {
                    integration.addRequestTemplates(body.contentType() , template);
                }
            });
        }


        LambdaBackend lambdaBackend = method.getAnnotation(LambdaBackend.class);
        if (lambdaBackend != null) {
            integration.type("aws").httpMethod("POST");
            ConverterUtils.setCheckVal(integration::credentials , lambdaBackend.credentials() , "" , StringUtils::isEmpty);
            StringBuilder sb = new StringBuilder("arn:aws:apigateway:");
            sb.append(lambdaBackend.region())
                    .append(":lambda:path/2015-03-31/functions/arn:aws:lambda:")
                    .append(lambdaBackend.region())
                    .append(":")
                    .append(info.getAwsAccountId())
                    .append(":")
                    .append(lambdaBackend.functionName());
            if (StringUtils.isNotEmpty(lambdaBackend.aliasName())) {
                sb.append(":").append(lambdaBackend.aliasName());
            }
            sb.append("/invocations");
            integration.uri(sb.toString());
            ConverterUtils.setCheckVal(integration::cacheNamespace , lambdaBackend.cacheNamespace() , "" , StringUtils::isEmpty);
            ConverterUtils.setConvertVal(integration::cacheKeyParameters , lambdaBackend.cacheKeyParameters() , Arrays::asList , ConverterUtils::isEmpty);
        }
        AwsBackend awsBackend = method.getAnnotation(AwsBackend.class);
        if (awsBackend != null) {
            integration.type("aws");
            ConverterUtils.setVal(integration::httpMethod , awsBackend.httpMethod());
            ConverterUtils.setVal(integration::credentials , awsBackend.credentials());
            ConverterUtils.setVal(integration::uri , awsBackend.uri());
            ConverterUtils.setCheckVal(integration::cacheNamespace , awsBackend.cacheNamespace() , "" , StringUtils::isEmpty);
            ConverterUtils.setConvertVal(integration::cacheKeyParameters , awsBackend.cacheKeyParameters() , Arrays::asList , ConverterUtils::isEmpty);
        }


        RequestParameterMappingAnnotation requestParameterMappingAnnotation = method.getAnnotation(RequestParameterMappingAnnotation.class);
        if (requestParameterMappingAnnotation != null) {
            RequestParameterMapping[] requestParameterMappings = requestParameterMappingAnnotation.value();
            Arrays.stream(requestParameterMappings).forEach(rpm -> {
                Parameter parameter;
                if (rpm.in() == RequestParameterMapping.InType.header) {
                    parameter = new HeaderParameter();
                } else if (rpm.in() == RequestParameterMapping.InType.query) {
                    parameter = new QueryParameter();
                } else if (rpm.in() == RequestParameterMapping.InType.path) {
                    parameter = new PathParameter();
                } else {
                    throw new LambdaWingException("TODO エラーメッセージ書く");
                }
                parameter.setName(rpm.name());
                parameter.setDescription(rpm.description());
                parameter.setRequired(rpm.required());
                integration.addRequestParameters(rpm.integrationParam() , rpm.methodParam());
            });
        }

        ResponseMappingAnnotation responseMappingAnnotation = method.getAnnotation(ResponseMappingAnnotation.class);
        if (responseMappingAnnotation != null) {
            Arrays.stream(responseMappingAnnotation.value()).forEach(rma -> {
                String searchKey = "default";
                AmazonApiGatewayIntegrationResponse response = new AmazonApiGatewayIntegrationResponse();
                if (StringUtils.isNotEmpty(rma.searchKey())) {
                    searchKey = rma.searchKey();
                }
                response.statusCode("" + rma.statusCode());
                if (rma.responseClass() != Object.class) {
                    String className = addClassModel(swagger, rma.responseClass());
                    operation.response(rma.statusCode() , new RefResponse(className));
                }
                Arrays.stream(rma.parameter()).forEach(rpm -> {
                    response.addResponseParameters(rpm.methodParam() , rpm.integrationParam());
                });

                Arrays.stream(rma.template()).forEach(rtm -> {
                    String template = rtm.template();
                    if (StringUtils.isEmpty(template)) {
                        template = ConverterUtils.readResource(rtm.fileName());
                    }
                    response.addResponseTemplates(rtm.mime() , template);
                });

                integration.addResponses(searchKey , response);

            });
        }
        operation.setVendorExtension("x-amazon-apigateway-integration" , integration);


    }

    String addClassModel(Swagger swagger, Class<?> klass) {
        String requestClassName = klass.getSimpleName(); // TODO 可変にする
        Map<String, Model> modelMap = modelConverters.readAll(klass);
        modelMap.forEach(swagger::addDefinition);
        return requestClassName;
    }
}
