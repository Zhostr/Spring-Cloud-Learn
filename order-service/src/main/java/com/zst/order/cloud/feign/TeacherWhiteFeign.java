package com.zst.order.cloud.feign;

import com.zst.commons.util.GenericResponse;
import com.zst.order.cloud.feign.imlp.TeacherWhiteFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: fallback 通过 Hystrix 来实现的，想要回退降级必须 feign.hystrix.enabled = true，默认情况是不支持降级的
 * path 前缀，应用于所有接口
 * @author: Zhoust
 * @date: 2020/02/25 下午5:33
 * @version: V1.0
 */
@FeignClient(url = "http://localhost:8080", path = "api" ,name = "TeacherFeign",
        fallbackFactory = TeacherWhiteFallbackFactory.class)
public interface TeacherWhiteFeign {

    @GetMapping("classroom/get")
    GenericResponse<String> get();

}