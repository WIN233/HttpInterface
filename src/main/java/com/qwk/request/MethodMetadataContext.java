package com.qwk.request;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author qiuwenke
 */
public class MethodMetadataContext {

    public <T extends Annotation> AnnotationMetadata<T> findAnnotation(Class<? extends Annotation> annotation) {
        T result = null;
        int count = 0;
        int index = 0;
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (int x = 0; x < parameterAnnotation.length; x++) {
                Annotation findResult = parameterAnnotation[x];
                if (findResult.annotationType() == annotation) {
                    count++;
                    result = (T) findResult;
                    index = i;
                }
            }
        }
        if(result == null) {
            return null;
        }
        AnnotationMetadata annotationMetadata = new AnnotationMetadata();
        annotationMetadata.setArgIndex(index);
        annotationMetadata.setAnnotation(result);
        return annotationMetadata;
    }

    public MethodMetadataContext(Object[] args, Method method) {
        this.args = args;
        this.method = method;

        init();
    }

    public void init() {
        this.parameterAnnotations = this.method.getParameterAnnotations();
        this.methodAnnotations = this.method.getAnnotations();
    }

    /**
     * 方法参数上的注解
     */
    private Annotation[][] parameterAnnotations;

    /**
     * 实际参数
     */
    private Object[] args;
    /**
     * 方法上的注解
     */
    private Annotation[] methodAnnotations;

    /**
     * 方法本身
     */
    private Method method;


    public Annotation[][] getParameterAnnotations() {
        return parameterAnnotations;
    }

//    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
//        this.parameterAnnotations = parameterAnnotations;
//    }

    public Object[] getArgs() {
        return args;
    }

//    public void setArgs(Object[] args) {
//        this.args = args;
//    }

    public Annotation[] getMethodAnnotations() {
        return methodAnnotations;
    }

//    public void setMethodAnnotations(Annotation[] methodAnnotations) {
//        this.methodAnnotations = methodAnnotations;
//    }

    public Method getMethod() {
        return method;
    }
//
//    public void setMethod(Method method) {
//        this.method = method;
//    }
}
