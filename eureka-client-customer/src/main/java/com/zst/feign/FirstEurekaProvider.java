package com.zst.feign;

import com.zst.entity.Person;
import com.zst.feign.fallback.FirstEurekaProviderFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 使用 @FeignClient 指定调用的服务名和降级
 *
 * @description: first-eureka-provider 服务客户端
 * @author: Zhoust
 * @date: 2018/12/18 上午10:58
 * @version: V1.0
 */
@Service
@FeignClient(name="first-eureka-provider", fallback = FirstEurekaProviderFallBack.class)
public interface FirstEurekaProvider {

    /**
     * 调用 first-eureka-provider 服务提供的 get/{id} 接口
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    Person getPersonById(@PathVariable("id") Integer id);

}