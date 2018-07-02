package com.zhaojun.sell.service.impl;

import com.zhaojun.sell.domain.OrderDetail;
import com.zhaojun.sell.dto.OrderDTO;
import com.zhaojun.sell.enums.OrderStatusEnum;
import com.zhaojun.sell.enums.PayStatusEnum;
import com.zhaojun.sell.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String OPENID = "open3245445657";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("四川省成都市高新区投资大厦南楼16楼");
        orderDTO.setBuyerName("郭霖");
        orderDTO.setBuyerPhone("13990797728");
        orderDTO.setBuyerOpenid(OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId("123456");
        detail1.setProductQuantity(2);
        OrderDetail detail2 = new OrderDetail();
        detail2.setProductId("1234567");
        detail2.setProductQuantity(5);

        orderDetailList.add(detail1);
        orderDetailList.add(detail2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne("1530424850648679106",OPENID);
        Assert.assertNotNull(result);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(1,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(OPENID, pageRequest);
        Assert.assertNotNull(orderDTOPage.getContent());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1530424850648679106",OPENID);
        OrderDTO cancelDTO = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancelDTO.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1530424850648679106",OPENID);
        OrderDTO cancelDTO = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),cancelDTO.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1530424850648679106",OPENID);
        OrderDTO cancelDTO = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),cancelDTO.getPayStatus());
    }
}