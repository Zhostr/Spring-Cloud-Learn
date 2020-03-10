package com.inventory.api;

import com.zst.commons.util.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 库存系统 API
 * @author: Zhoust
 * @date: 2020/03/09 上午9:32
 * @version: V1.0
 */
@RequestMapping("/inventory")
public interface InventoryApi {

    /**
     * 商品 productId 扣减库存 stock
     * @param productId
     * @param stock
     * @return
     */
    @GetMapping(value = "/deduct/{productId}/{stock}")
    GenericResponse<Boolean> noticeDelivery(@PathVariable("productId") Long productId, @PathVariable("stock") Long stock);


}