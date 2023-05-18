package com.qwk.test;

import com.qwk.HttpInterface;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author qiuwenke
 */
@HttpInterface(value = "githubApi", url = "${test}")
public interface GithubApi {

    @RequestMapping(value = "/repos/{owner}/{repo}/contributors",method = RequestMethod.GET)
    List<Contributor> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo, String test, @PathVariable("fdsf") String hh,
                                   @RequestBody String body);


}
