package com.zst.order.health;

import com.zst.order.controller.MockHealth;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义健康检查指示器
 * @see ConfigServerHealthIndicator
 * @author: Zhoust
 * @date: 2018/11/28 上午9:36
 * @version: V1.0
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if(MockHealth.healthStatus.equals(Boolean.TRUE)) {
            Health.Builder builder = Health.up();
            builder.withDetail("name", "Zhoust");
            builder.withDetail("gender", "Boy");
            return builder.build();
        }
        else {
            return new Health.Builder(Status.DOWN).build();
        }
    }

}