package com.qwk.test;
// 导入所需的包

import com.qwk.request.AnnotationMetadata;
import com.qwk.request.MethodMetadataContext;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class MethodMetadataContextTest {

    // 测试用例1：验证 findAnnotation 方法返回为 null 的情况
    @Test
    public void testFindAnnotationIsNull() throws Exception {
        Method method = TestClass.class.getMethod("testMethod", String.class, int.class);
        Object[] args = new Object[]{"test", 123};
        MethodMetadataContext context = new MethodMetadataContext(args, method);
        AnnotationMetadata annotationMetadata = context.findAnnotation(TestAnnotation.class);
        Assert.assertNull(annotationMetadata);
    }

    // 测试用例2：验证 findAnnotation 方法返回非 null 的情况
    @Test
    public void testFindAnnotationNotNull() throws Exception {
        Method method = TestClass.class.getMethod("testMethod", String.class, int.class);
        Object[] args = new Object[]{"test", 123};
        MethodMetadataContext context = new MethodMetadataContext(args, method);
        AnnotationMetadata<TestMethodAnnotation> annotationMetadata = context.findAnnotation(TestMethodAnnotation.class);
        Assert.assertNotNull(annotationMetadata);
        Assert.assertEquals(annotationMetadata.getArgIndex(), 1);
        Assert.assertEquals(annotationMetadata.getAnnotation().value(), "test");
    }

    // 定义测试注解 TestAnnotation
    @TestAnnotation
    public interface TestClass {
        void testMethod(String arg1, @TestMethodAnnotation("test") int arg2);
    }

    // 定义测试注解 TestAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {
    }

    @Retention(RetentionPolicy.RUNTIME)
    // 定义测试注解 TestMethodAnnotation
    @interface TestMethodAnnotation {
        String value();
    }
}
