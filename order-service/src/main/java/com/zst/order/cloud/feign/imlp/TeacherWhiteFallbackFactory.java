package com.zst.order.cloud.feign.imlp;

import com.zst.commons.util.GenericResponse;
import com.zst.order.cloud.feign.TeacherWhiteFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/25 下午6:28
 * @version: V1.0
 */
@Slf4j
@Component
public class TeacherWhiteFallbackFactory implements FallbackFactory<TeacherWhiteFeign> {

    @Override
    public TeacherWhiteFeign create(Throwable cause) {
        return new TeacherWhiteFeign() {
            @Override
            public GenericResponse<String> get() {
                log.error("api/classroom/get 接口降级！", cause);
                return null;
            }
        };
    }
}