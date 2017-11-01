package com.zhaojun.sell.utils;

import com.zhaojun.sell.vo.ResultVO;

/**
 * @author zhaojun0193
 * @create 2017/11/1
 */
public class ResultVOUtil {

    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMessage("成功");
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVO error(Integer code, String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
