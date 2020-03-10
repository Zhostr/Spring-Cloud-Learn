package com.zst.order.controller;

import com.zst.commons.util.GenericResponse;
import com.zst.order.cloud.feign.InventoryFeign;
//import com.zst.order.cloud.feign.WmsServiceFeign;
import com.zst.order.cloud.feign.WmsServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/03/10 上午8:19
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private WmsServiceFeign wmsServiceFeign;

    @Autowired
    private InventoryFeign inventoryFeign;

    @GetMapping("/buy")
    public GenericResponse<Boolean> buy(Long productId, Long userId) {
        //通知库存服务扣减库存，通知仓储服务发货
        GenericResponse<Boolean> inventoryResponse = inventoryFeign.noticeDelivery(productId, userId);
        log.info("库存服务扣减成功？{}", inventoryResponse.getData());
        GenericResponse<String> wmsResponse = wmsServiceFeign.noticeDelivery(productId, userId);
        log.info("wms 发货成功？{}", wmsResponse.getData());
        log.info("用户下单成功！");
        return GenericResponse.success(true);
    }


}