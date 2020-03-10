package com.zst.order.health;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 *
 * @description: 健康检查处理器（将服务的健康状态保存到内存，一旦状态发生改变，就会向服务器重新注册，其他客户端拿不到不可用的实例）
 * @author: Zhoust
 * @date: 2018/11/28 上午10:00
 * @version: V1.0
 */
@Slf4j
@Component
public class CustomHealthCheckHandler implements HealthCheckHandler {

    @Autowired
    private CustomHealthIndicator healthIndicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
//        log.info("Invoke getStatus method.");
        Status status = healthIndicator.health().getStatus();
        if(status.equals(Status.UP)) {
            return InstanceInfo.InstanceStatus.UP;
        }
        return InstanceInfo.InstanceStatus.DOWN;
    }

}