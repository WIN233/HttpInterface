package com.qwk.test;

import com.qwk.HttpInterfaceProxy;
import com.qwk.Target;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class TestHttpInterfaceProxy {

    @Test
    public void test() {
        Target<GithubApi> objectTarget = new Target.DefaultTarget<>(GithubApi.class);


        GithubApi githubApi = (GithubApi) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{GithubApi.class}, new HttpInterfaceProxy(objectTarget));
//        List<Contributor> contributors = githubApi.contributors("spring", "spring");
//        Assert.notEmpty(contributors);
    }

}
