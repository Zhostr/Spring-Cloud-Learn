package com.zst.feign.fallback;

import com.zst.entity.Person;
import com.zst.feign.FirstEurekaProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 回退降级
 * @author: Zhoust
 * @date: 2018/12/18 下午5:12
 * @version: V1.0
 */
@Slf4j
@Component
public class FirstEurekaProviderFallBack implements FirstEurekaProvider {

    @Override
    public Person getPersonById(Integer id) {
        log.error("调用 Person 服务异常！");
        return null;
    }

}