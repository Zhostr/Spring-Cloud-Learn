package com.zst.provider.controller;

import com.netflix.appinfo.InstanceInfo;
import com.zst.provider.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/11/28 下午9:34
 * @version: V1.0
 */
@Slf4j
@RestController
public class InvokerController {


    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/router")
    public String router() {
        //查找服务列表
        List<ServiceInstance> serviceInstances = getServiceInstances();
        for(ServiceInstance service : serviceInstances) {
            EurekaDiscoveryClient.EurekaServiceInstance eurekaServiceInstance = (EurekaDiscoveryClient.EurekaServiceInstance) service;
            InstanceInfo instanceInfo = eurekaServiceInstance.getInstanceInfo();
            log.info("应用 {}，实例 id = {}, 实例状态为 {}", instanceInfo.getAppName(), instanceInfo.getId(), instanceInfo.getStatus());
        }
        return "";
    }


    public List<ServiceInstance> getServiceInstances() {
        List<String> ids = discoveryClient.getServices();
        List<ServiceInstance> result = new ArrayList<>();
        for(String id : ids) {
            List<ServiceInstance> instances = discoveryClient.getInstances(id);
            result.addAll(instances);
        }
        return result;
    }

    @PostMapping("forPostBody")
    public Person getPerson(@RequestBody Person person) {
        log.info("person is {}", person);
        person.setAge(10);
        person.setMsg("Msg Msajskldljdlas安徽分撒旦");
        person.setName("Name");
        return person;
    }

}