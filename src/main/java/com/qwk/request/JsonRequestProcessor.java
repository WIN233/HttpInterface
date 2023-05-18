package com.qwk.request;


import com.qwk.MethodMetaData;

/**
 * @author qiuwenke
 */
public class JsonRequestProcessor implements RequestProcessor {


    /**
     * 仅支持一个对象转为json，其他对象只能通过queryString传递
     *
     */
    @Override
    public void process(MethodMetadataContext context, MethodMetaData methodMetaData) {
//        AnnotationMetadata<RequestBody> requestBody = findRequestBody(context.getParameterAnnotations(), context.getMethod());
//        Object arg = context.getArgs()[requestBody.getArgIndex()];
//        String s = JSON.toJSONString(arg);
//        methodMetaData.setBody(s);
    }


//    public static AnnotationMetadata findRequestBody(Annotation[][] parameterAnnotations, Method method) {
//
//        RequestBody result = null;
//        int count = 0;
//        int index = 0;
//        for (int i = 0; i < parameterAnnotations.length; i++) {
//            Annotation[] parameterAnnotation = parameterAnnotations[i];
//            for (int x = 0; x < parameterAnnotation.length; x++) {
//                Annotation annotation = parameterAnnotation[x];
//                if (annotation.annotationType() == RequestBody.class) {
//                    count++;
//                    result = (RequestBody) annotation;
//                    index = i;
//                }
//            }
//        }
//        if (count > 1) {
//            throw new IllegalArgumentException("the @RequestBody more than one,please check your method:" + method.toString());
//        }
//        if(result == null) {
//            return null;
//        }
//        AnnotationMetadata annotationMetadata = new AnnotationMetadata();
//        annotationMetadata.setArgIndex(index);
//        annotationMetadata.setAnnotation(result);
//        return annotationMetadata;
//    }
}
