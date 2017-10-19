package com.zhaojun.sell.controller;

import com.zhaojun.sell.vo.ProductInfoVO;
import com.zhaojun.sell.vo.ProductVO;
import com.zhaojun.sell.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 买家商品
 * @author zhaojun0193
 * @create 2017/10/20
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @GetMapping("/list")
    public ResultVO list(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMessage("成功");

        ProductInfoVO productInfoVO = new ProductInfoVO();

        List<ProductInfoVO> productInfoVOList = Arrays.asList(productInfoVO);

        ProductVO productVO = new ProductVO();

        productVO.setProductInfoVOList(productInfoVOList);

        resultVO.setData(productVO);

        return resultVO;
    }
}
