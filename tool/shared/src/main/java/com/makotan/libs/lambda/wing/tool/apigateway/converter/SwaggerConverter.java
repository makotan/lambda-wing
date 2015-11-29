package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.google.common.collect.Collections2;
import com.google.common.io.CharStreams;
import com.makotan.libs.lambda.wing.api_gw.core.*;
import com.makotan.libs.lambda.wing.core.LambdaHandler;
import com.makotan.libs.lambda.wing.core.exception.LambdaWingException;
import com.makotan.libs.lambda.wing.core.util.Either;
import com.makotan.libs.lambda.wing.core.util.StringUtils;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.ApiInfoConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.RestMethodConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.converter.gwcore.RestResourceConverter;
import com.makotan.libs.lambda.wing.tool.apigateway.model.AmazonApiGatewayAuth;
import com.makotan.libs.lambda.wing.tool.apigateway.model.AmazonApiGatewayIntegrationResponse;
import com.makotan.libs.lambda.wing.tool.apigateway.model.AmazonApigatewayIntegration;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertErrors;
import com.makotan.libs.lambda.wing.tool.apigateway.model.SwaggerConvertInfo;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Info;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefResponse;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by makotan on 2015/11/21.
 */
public class SwaggerConverter {
    Logger log = LoggerFactory.getLogger(getClass());

    List<ClassToSwaggerConverter> classToSwaggerConverters = new ArrayList<>();
    List<MethodToSwaggerConverter> methodToSwaggerConverters = new ArrayList<>();

    public SwaggerConverter() {
        classToSwaggerConverters.add(new ApiInfoConverter());
        classToSwaggerConverters.add(new RestResourceConverter());
        methodToSwaggerConverters.add(new RestMethodConverter());
    }

    public Either<SwaggerConvertErrors,Swagger> convert(SwaggerConvertInfo info) {
        Swagger swagger = new Swagger();
        ConvertContext context = new ConvertContext();

        Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .addClassLoader(getClass().getClassLoader())
                        .filterInputsBy(name -> name.startsWith(info.basePackage))
                        .forPackages(info.basePackage)
                        .addScanners(new TypeAnnotationsScanner())
                );


        classToSwaggerConverters.forEach(conv -> {
            Class<? extends Annotation> anno = conv.scanAnnotation();
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(anno);
            if (typesAnnotatedWith.isEmpty()) {
                return;
            }
            typesAnnotatedWith.forEach(type -> {
                conv.convert(type, info , swagger,context);
                if (conv.useMethodScan()) {
                    methodToSwaggerConverters.forEach(mconv -> {
                        Set<Method> methods = Arrays.stream(type.getMethods()).filter(m -> m.getAnnotation(mconv.scanAnnotation()) != null).collect(Collectors.toSet());
                        methods.forEach(method -> {
                            mconv.convert(type,method,info,swagger,context);
                        });
                    });
                }
            });
        });

        Class<?> baseClass = null;
        {
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(ApiInfo.class);
            if (typesAnnotatedWith.isEmpty()) {
                return Either.left(new SwaggerConvertErrors());
            }
            baseClass = typesAnnotatedWith.stream().findFirst().get();
        }
        //Package basePackage = Package.getPackage(info.basePackage);
        ApiInfo apiInfo = baseClass.getAnnotation(ApiInfo.class);
        if (apiInfo != null) {
            ConverterUtils.setCheckVal(swagger::basePath , apiInfo.basePath() , "" , info::getBasePath , StringUtils::isEmpty);

            Info swaggerInfo = new Info();
            ConverterUtils.setCheckVal(swaggerInfo::description , apiInfo.description() , "" , info::getDescription , StringUtils::isEmpty);
            ConverterUtils.setCheckVal(swaggerInfo::title , apiInfo.title() , StringUtils::isEmpty);
            ConverterUtils.setCheckVal(swaggerInfo::version , apiInfo.version() , "" , info::getVersion , StringUtils::isEmpty);
            swagger.info(swaggerInfo);

            ConverterUtils.setConvertVal(swagger::schemes , apiInfo.scheme() , ConverterUtils::convertScheme , ConverterUtils::isEmpty);
            ConverterUtils.setConvertVal(swagger::produces , apiInfo.produces() , Arrays::asList , ConverterUtils::isEmpty);
            ConverterUtils.setConvertVal(swagger::consumes , apiInfo.consumes() , Arrays::asList , ConverterUtils::isEmpty);
        }
        {
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RestResource.class);
            typesAnnotatedWith.stream().forEach(klass -> {
                RestResource restResource = klass.getAnnotation(RestResource.class);
                String path = restResource.value();
                Path swgPath = new Path();
                swagger.path(path , swgPath);
                Set<Method> restMethods = Arrays.stream(klass.getMethods()).filter(m -> m.getAnnotation(RestMethod.class) != null).collect(Collectors.toSet());
                restMethods.forEach(method -> {
                    setMethod(method , swgPath , swagger);
                });
            });

        }

        return Either.right(swagger);
    }

    ModelConverters modelConverters = new ModelConverters();

    void setMethod(Method method , Path path , Swagger swagger) {
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

        AmazonApigatewayIntegration integration = new AmazonApigatewayIntegration();

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
                    .append("9876543210")
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
