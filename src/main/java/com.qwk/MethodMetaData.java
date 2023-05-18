package com.qwk;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @author qiuwenke
 */
public class MethodMetaData {

    private String uri;
    private HttpMethod method;
    private String queryString;
    private Object body;
    private HttpHeaders headers = new HttpHeaders();
    private Map<String, Object> uriVariable;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Map<String, Object> getUriVariable() {
        return uriVariable;
    }

    public void setUriVariable(Map<String, Object> uriVariable) {
        this.uriVariable = uriVariable;
    }

    @Override
    public String toString() {
        return "MethodMetaData{" +
                "uri='" + uri + '\'' +
                ", method=" + method +
                ", queryString='" + queryString + '\'' +
                ", body=" + body +
                ", headers=" + headers +
                ", uriVariable=" + uriVariable +
                '}';
    }
}
