package com.qwk;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiuwenke
 */
public class HttpInterfaceProxy implements InvocationHandler {
    private Target target;

    public HttpInterfaceProxy(Target target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
        if ("equals".equals(method.getName())) {
            try {
                Object otherHandler =
                        args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if ("hashCode".equals(method.getName())) {
            return hashCode();
        } else if ("toString".equals(method.getName())) {
            return toString();
        }

        MethodMetaData methodMetaData = getMethodMetaData(method);
        methodMetaData = parseParameter(method, args, methodMetaData);
        RestTemplate restTemplate = new RestTemplate();
        //todo 处理queryString
        String processedUrl = processUrl(target.url() + methodMetaData.getUri());

        final MethodMetaData finalMethodMetaData = methodMetaData;

        Object response = restTemplate.execute(processedUrl, methodMetaData.getMethod(), new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                HttpHeaders headers = request.getHeaders();
                headers.putAll(finalMethodMetaData.getHeaders());
                if (finalMethodMetaData.getBody() != null) {
                    OutputStream bodyOut = request.getBody();
                    IOUtils.copy(new ByteArrayInputStream(finalMethodMetaData.getBody().toString().getBytes(StandardCharsets.UTF_8)), bodyOut);
                }
            }
        }, new ResponseExtractor<Object>() {
            @Override
            public Object extractData(ClientHttpResponse response) throws IOException {
                InputStream body = response.getBody();
                String resp = IOUtils.toString(body);
                //迫于spring3.2前restTemplate泛型存在bug，故只能出此下策，使用fastjson解决泛型的问题，因此也导致了仅支持json形式的响应
                //https://github.com/spring-projects/spring-framework/issues/11685
                return JSON.parseObject(resp, method.getGenericReturnType());
            }
        }, methodMetaData.getUriVariable());

        return response;
    }

    public String processUrl(String url) {
//        if(!(url.startsWith("http") || url.startsWith("https"))) {
//            //如果不是以http或https开头，帮忙加上
//            url = "http" + url;
//        }
        //去除url中多余的/

        return url;
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
            if(parameterAnnotation.length == 0) {
                //尝试解析为
                continue;
            }
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

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HttpInterfaceProxy) {
            HttpInterfaceProxy that = (HttpInterfaceProxy) obj;
            return that.target.equals(this.target);
        }
        return false;
    }
}
