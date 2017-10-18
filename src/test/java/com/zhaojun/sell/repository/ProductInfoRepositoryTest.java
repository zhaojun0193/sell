package com.zhaojun.sell.repository;

import com.zhaojun.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
//    @Transactional
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("茶杯");
        productInfo.setProductPrice(new BigDecimal(19.8));
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://cup.jpg");
        productInfo.setProductDescription("杯子");

        ProductInfo result = repository.save(productInfo);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOne(){
        ProductInfo productInfo = repository.findOne("123456");
        Assert.assertNotNull(productInfo);
    }

    @Test
    @Transactional
    public void update(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("水杯");
        productInfo.setProductPrice(new BigDecimal(19.8));
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://cup.jpg");
        productInfo.setProductDescription("杯子");

        ProductInfo result = repository.save(productInfo);
        Assert.assertEquals("水杯",result.getProductName());
    }


    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,byProductStatus.size());
    }

}