package com.zhaojun.sell.controller;

import com.zhaojun.sell.domain.ProductCategory;
import com.zhaojun.sell.domain.ProductInfo;
import com.zhaojun.sell.service.ProductCategoryService;
import com.zhaojun.sell.service.ProductInfoService;
import com.zhaojun.sell.utils.ResultVOUtil;
import com.zhaojun.sell.vo.ProductInfoVO;
import com.zhaojun.sell.vo.ProductVO;
import com.zhaojun.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @author zhaojun0193
 * @create 2017/10/20
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){

        //查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //查询所有商品类目 并将类目类型放入集合中
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        //根据类目编号集合查询类目
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();

        //组装productVoList
        for (ProductCategory category: categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo,productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
