package com.zhaojun.sell.dto;

import lombok.Data;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
@Data
public class CartDTO {
    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
