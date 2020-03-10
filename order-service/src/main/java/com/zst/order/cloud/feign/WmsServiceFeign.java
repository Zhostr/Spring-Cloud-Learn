package com.zst.order.cloud.feign;

import com.wms.api.WmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/03/10 上午8:31
 * @version: V1.0
 */
@FeignClient(name = "wms-service")
public interface WmsServiceFeign extends WmsApi {


}