package com.zhaojun.sell.exception;

import com.zhaojun.sell.enums.ResultEnum;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
