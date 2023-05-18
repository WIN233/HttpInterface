package com.qwk.request;

import java.lang.annotation.Annotation;

/**
 * @author qiuwenke
 */
public class AnnotationMetadata<T extends Annotation> {
    private int argIndex;
    private T annotation;

    public int getArgIndex() {
        return argIndex;
    }

    public void setArgIndex(int argIndex) {
        this.argIndex = argIndex;
    }

    public T getAnnotation() {
        return annotation;
    }

    public void setAnnotation(T annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "AnnotationMetadata{" +
                "argIndex=" + argIndex +
                ", annotation=" + annotation +
                '}';
    }
}
