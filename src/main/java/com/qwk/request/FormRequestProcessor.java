package com.qwk.request;

import com.qwk.MethodMetaData;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiuwenke
 */
public class FormRequestProcessor implements RequestProcessor {

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public void process(MethodMetadataContext context, MethodMetaData methodMetaData) {
        Object body = new Object();
        Object[] args = context.getArgs();

        Map<String,String> map = new HashMap<>(args.length);

        AnnotationMetadata annotationMetadata = context.findAnnotation(RequestParam.class);
        if (annotationMetadata != null) {
            RequestParam requestParam = (RequestParam)annotationMetadata.getAnnotation();
            int argIndex = annotationMetadata.getArgIndex();
            Object arg = args[argIndex];
            String argString = "";
            if(arg != null) {
                argString = arg.toString();
            }
            String name = getParameterName(requestParam, argIndex, context.getMethod());
            map.put(name,argString);
        }

//        MultiValueMap

        //处理requestParam


//        for (Object arg : args) {
//
//        }
    }

    private String getParameterName(RequestParam requestParam, int argIndex, Method method) {
        if (StringUtils.hasText(requestParam.value())) {
            return requestParam.value();
        }
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames == null) {
            //TODO 处理获取名称失败的情况
        }
        return parameterNames[argIndex];
    }

    public static void main(String[] args) {
        boolean primitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(String.class);
        System.out.println(primitiveOrWrapper);
    }
}
