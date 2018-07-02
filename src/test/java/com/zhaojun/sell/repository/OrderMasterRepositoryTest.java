package com.zhaojun.sell.repository;

import com.zhaojun.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private final String OPENID = "open3245445657";

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerAddress("四川成都高新区天府新谷");
        orderMaster.setBuyerName("赵军");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerPhone("18328623413");
        orderMaster.setOrderAmount(new BigDecimal("8888.66"));
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0,page.getTotalElements());
    }
}