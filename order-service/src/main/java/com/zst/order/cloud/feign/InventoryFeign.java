package com.zst.order.cloud.feign;

import com.inventory.api.InventoryApi;
import com.wms.api.WmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/03/09 上午10:59
 * @version: V1.0
 */
@FeignClient(name = "inventory-service")
public interface InventoryFeign extends InventoryApi {


}