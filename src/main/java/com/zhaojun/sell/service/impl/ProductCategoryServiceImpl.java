package com.zhaojun.sell.service.impl;

import com.zhaojun.sell.domain.ProductCategory;
import com.zhaojun.sell.repository.ProductCategoryRepository;
import com.zhaojun.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaojun0193
 * @create 2017/10/18
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryRepository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
