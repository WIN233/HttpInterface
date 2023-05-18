package com.qwk.test;//package com.shengpay.pos.operating.http;
//
//import com.shengpay.pos.operating.configuration.http.request.AnnotationMetadata;
//import com.shengpay.pos.operating.configuration.http.request.JsonRequestProcessor;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.lang.reflect.Method;
//
//public class TestAnnotationMetadata {
//
//    private interface TestInterface {
//        void simple(@RequestBody String body);
//
//        void complex1(@RequestBody String body, TestInterface t);
//
//        void complex2(TestInterface t,@RequestBody String body);
//
//        void complex3(TestInterface t,@RequestBody String body,Object o);
//
//        void complex4(TestInterface t, @RequestBody @PathVariable String body, Object o);
//    }
//
//
//    @Test
//    public void testSimple() throws NoSuchMethodException {
//        Method simple = TestInterface.class.getMethod("simple", String.class);
//        AnnotationMetadata<RequestBody> simpleResult = JsonRequestProcessor.findRequestBody(simple.getParameterAnnotations(), simple);
//        Assert.assertEquals(RequestBody.class, simpleResult.getAnnotation().annotationType());
//        Assert.assertEquals(0, simpleResult.getArgIndex());
//    }
//
//
//    @Test
//    public void testComplex1() throws NoSuchMethodException {
//        Method complex = TestInterface.class.getMethod("complex1", String.class, TestInterface.class);
//        AnnotationMetadata<RequestBody> complexResult = JsonRequestProcessor.findRequestBody(complex.getParameterAnnotations(), complex);
//        Assert.assertEquals(RequestBody.class, complexResult.getAnnotation().annotationType());
//        Assert.assertEquals(0, complexResult.getArgIndex());
//    }
//
//    @Test
//    public void testComplex2() throws NoSuchMethodException {
//        Method complex = TestInterface.class.getMethod("complex2", TestInterface.class, String.class);
//        AnnotationMetadata<RequestBody> complexResult = JsonRequestProcessor.findRequestBody(complex.getParameterAnnotations(), complex);
//        Assert.assertEquals(RequestBody.class, complexResult.getAnnotation().annotationType());
//        Assert.assertEquals(1, complexResult.getArgIndex());
//    }
//
//    @Test
//    public void testComplex3() throws NoSuchMethodException {
//        Method complex = TestInterface.class.getMethod("complex3", TestInterface.class, String.class, Object.class);
//        AnnotationMetadata<RequestBody> complexResult = JsonRequestProcessor.findRequestBody(complex.getParameterAnnotations(), complex);
//        Assert.assertEquals(RequestBody.class, complexResult.getAnnotation().annotationType());
//        Assert.assertEquals(1, complexResult.getArgIndex());
//    }
//
//    @Test
//    public void testComplex4() throws NoSuchMethodException {
//        Method complex = TestInterface.class.getMethod("complex4", TestInterface.class, String.class, Object.class);
//        AnnotationMetadata<RequestBody> complexResult = JsonRequestProcessor.findRequestBody(complex.getParameterAnnotations(), complex);
//        Assert.assertEquals(RequestBody.class, complexResult.getAnnotation().annotationType());
//        Assert.assertEquals(1, complexResult.getArgIndex());
//    }
//
//}
