package com.zst.wms.controller;

import com.wms.api.WmsApi;
import com.zst.commons.util.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/03/09 上午9:49
 * @version: V1.0
 */
@Slf4j
@RestController
public class WmsController implements WmsApi {

    @Override
    public GenericResponse<String> noticeDelivery(Long productId, Long userId) {
        log.info("给 userId = {}，发 productId = {} 货物成功！", userId, productId);
        return GenericResponse.success("success");
    }

}