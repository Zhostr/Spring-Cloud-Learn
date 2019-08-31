package com.zst.client.config.native_feign;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/03/05 20:59
 * @version: V1.0
 */
public interface Github {

    @RequestLine("get /repos/{owner}/{repo}/contributors")
    List<Contributor> getContributors(@Param("owner") String owner, @Param("repo") String repository);

    class Contributor {
        String login;
        int contributions;
    }

}