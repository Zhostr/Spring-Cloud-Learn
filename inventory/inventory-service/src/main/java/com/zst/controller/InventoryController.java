package com.zst.controller;

import com.inventory.api.InventoryApi;
import com.zst.commons.util.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/03/09 上午10:30
 * @version: V1.0
 */
@Slf4j
@RestController
public class InventoryController implements InventoryApi {

    @Override
    public GenericResponse<Boolean> noticeDelivery(Long productId, Long stock) {
        log.info("对商品 {} 扣减库存 {}", productId, stock);
        return GenericResponse.success(true);
    }

}