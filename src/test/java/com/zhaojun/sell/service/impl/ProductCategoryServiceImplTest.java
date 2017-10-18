package com.zhaojun.sell.service.impl;

import com.zhaojun.sell.domain.ProductCategory;
import com.zhaojun.sell.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService service;

    @Test
    public void findOne() throws Exception {
        Assert.assertNotNull(service.findOne(1));
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> categoryList = service.findAll();
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> byCategoryTypeIn = service.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("测试数据");
        category.setCategoryType(999);
        ProductCategory productCategory = service.save(category);
        Assert.assertEquals(category,productCategory);
    }

}