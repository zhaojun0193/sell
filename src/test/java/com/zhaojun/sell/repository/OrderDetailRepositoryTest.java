package com.zhaojun.sell.repository;

import com.zhaojun.sell.domain.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123457");
        orderDetail.setProductIcon("http://image.sell/2.png");
        orderDetail.setProductId("89454");
        orderDetail.setProductName("《重构改善既有代码的设计》");
        orderDetail.setProductPrice(new BigDecimal("66.8"));
        orderDetail.setProductQuantity(201);
        orderDetail.setOrderId("123457");
        OrderDetail detail = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(detail);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId("123457");
        Assert.assertNotNull(orderDetails);
    }
}