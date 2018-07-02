package com.zhaojun.sell.enums;

import lombok.Getter;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
@Getter
public enum  ResultEnum {
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不正确"),
    ORDER_NOT_EXITS(12,"订单不存在"),
    ORDERDETAIL_NOT_EXITS(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDERDETAIL_EMPTY(15,"订单详情为空"),
    ORDER_UPDATE_FAIL(16,"订单更新失败"),
    PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    CAR_EMPTY(18,"购物车为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户")
    ;

    ResultEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;
}
