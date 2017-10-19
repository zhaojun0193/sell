package com.zhaojun.sell.vo;

import lombok.Data;

/**
 * @author zhaojun0193
 * @create 2017/10/20
 */
@Data
public class ResultVO<T> {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;
}
