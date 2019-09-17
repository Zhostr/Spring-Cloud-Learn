package com.zst.provider.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 17:13
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    private Long id;

    private String name;

    private String description;

    private Long price;

    private Long cityId;

    /** 接口返回值是 cityId，查自己的内存，获取 cityName
     *  @see com.zst.client.controller.ProductController#getProductInfo(java.lang.Long) **/
    private String cityName;

}