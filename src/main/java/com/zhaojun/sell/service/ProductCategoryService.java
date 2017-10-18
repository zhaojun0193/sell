package com.zhaojun.sell.service;

import com.zhaojun.sell.domain.ProductCategory;

import java.util.List;

/**
 * @author zhaojun0193
 * @create 2017/10/18
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
