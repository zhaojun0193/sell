package com.zhaojun.sell.controller;

import com.zhaojun.sell.converter.OrderForm2OrderDTOConverter;
import com.zhaojun.sell.domain.OrderDetail;
import com.zhaojun.sell.dto.OrderDTO;
import com.zhaojun.sell.enums.ResultEnum;
import com.zhaojun.sell.exception.SellException;
import com.zhaojun.sell.form.OrderForm;
import com.zhaojun.sell.service.OrderService;
import com.zhaojun.sell.utils.ResultVOUtil;
import com.zhaojun.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【订单创建】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【订单创建】购物车为空，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.CAR_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     * @param openid 用户微信openid
     * @param page 页数
     * @param size 每页条数
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 订单详情
     * @param openid 用户微信openid
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/detail")
    public ResultVO<OrderDetail> detail(@RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        //TODO 不安全的做法，改进
        OrderDTO orderDTO = orderService.findOne(orderId,openid);

        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid)){
            log.error("【取消订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //TODO 不安全的做法，改进

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(openid);
        orderDTO.setOrderId(orderId);
        orderService.cancel(orderDTO);

        return ResultVOUtil.success();
    }
}
