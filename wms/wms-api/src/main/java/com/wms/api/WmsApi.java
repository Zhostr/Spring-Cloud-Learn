package com.wms.api;

import com.zst.commons.util.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 仓储系统 API
 * @author: Zhoust
 * @date: 2020/03/09 上午9:32
 * @version: V1.0
 */
@RequestMapping("/wms")
public interface WmsApi {

    /**
     * 通知给用户发货
     * @param productId
     * @param userId
     * @return
     */
    @GetMapping(value = "/noticeDelivery")
    GenericResponse<String> noticeDelivery(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId);


}