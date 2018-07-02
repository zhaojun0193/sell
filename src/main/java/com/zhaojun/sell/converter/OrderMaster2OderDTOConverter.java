package com.zhaojun.sell.converter;

import com.zhaojun.sell.domain.OrderMaster;
import com.zhaojun.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
public class OrderMaster2OderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e ->
                converter(e)
        ).collect(Collectors.toList());
    }
}
