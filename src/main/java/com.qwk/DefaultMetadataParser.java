package com.qwk;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiuwenke
 */
public class DefaultMetadataParser implements MetadataParser, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public MethodMetaData parse(Method method, Object[] args) {
        HttpInterface annotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), HttpInterface.class);
        MethodMetaData methodMetaData = getMethodMetaData(method);
        methodMetaData = parseParameter(method, args, methodMetaData);
        RestTemplate restTemplate = new RestTemplate();
        //todo 处理queryString
        String actualUrl = annotation.url() + methodMetaData.getUri();

        MethodMetaData finalMethodMetaData = methodMetaData;

        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    private static MethodMetaData getMethodMetaData(Method method) {
        MethodMetaData methodMetaData = new MethodMetaData();
        RequestMapping annotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (annotation == null) {
            throw new IllegalArgumentException("RequestMapping can not be null pleas check your method [" + method.getName() + "]");
        }
        String[] value = annotation.value();
        if (value.length > 1) {
            throw new IllegalArgumentException("RequestMapping value only support one param");
        }

        String requestMappingValue = value[0];
        if (StringUtils.isNotBlank(requestMappingValue)) {
            methodMetaData.setUri(requestMappingValue);
        }
        RequestMethod[] requestMethods = annotation.method();
        //todo 只支持一个requestMethod
        if (requestMethods.length != 1) {
            throw new IllegalArgumentException("Request method nums only be one");
        }
        if (requestMethods.length > 0) {
            methodMetaData.setMethod(HttpMethod.valueOf(requestMethods[0].name()));
        }

        String[] headers = annotation.headers();
        for (String header : headers) {
            String[] split = header.split(":");
            methodMetaData.getHeaders().add(split[0], split[1]);
        }

        return methodMetaData;
    }

    private static MethodMetaData parseParameter(Method method, Object[] args, MethodMetaData methodMetaData) {
//        Parameter[] parameters = method.getParameters();
//        Object body = null;
//        if (parameters.length == 1) {
//            body = args[0];
//        }
        Map<String, Object> uriVariable = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType() == PathVariable.class) {
                    uriVariable.put(((PathVariable) annotation).value(), args[i]);
                }
            }
        }
        methodMetaData.setUriVariable(uriVariable);
        //TODO 解析body

        return methodMetaData;
    }

}
