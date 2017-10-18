package com.zhaojun.sell.service;

import com.zhaojun.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceTest {

    @Autowired
    private ProductInfoService service;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = service.findOne("123456");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfos = service.findAll(pageRequest);
        Assert.assertNotEquals(0,productInfos.getSize());
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void findByProductStatus() throws Exception {
    }

}