package com.zhaojun.sell.service.impl;

import com.zhaojun.sell.converter.OrderMaster2OderDTOConverter;
import com.zhaojun.sell.domain.OrderDetail;
import com.zhaojun.sell.domain.OrderMaster;
import com.zhaojun.sell.domain.ProductInfo;
import com.zhaojun.sell.dto.CartDTO;
import com.zhaojun.sell.dto.OrderDTO;
import com.zhaojun.sell.enums.OrderStatusEnum;
import com.zhaojun.sell.enums.PayStatusEnum;
import com.zhaojun.sell.enums.ResultEnum;
import com.zhaojun.sell.exception.SellException;
import com.zhaojun.sell.repository.OrderDetailRepository;
import com.zhaojun.sell.repository.OrderMasterRepository;
import com.zhaojun.sell.service.OrderService;
import com.zhaojun.sell.service.ProductInfoService;
import com.zhaojun.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.text.CollatorUtilities;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniquKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3. 写入订单数据库（orderDetail）
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniquKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }

        //4. 写入订单数据库（orderMaster）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStocks(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId,String openid) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);

        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXITS);
        }

        if(!orderMaster.getBuyerOpenid().equals(openid)){
            log.error("【订单查询】订单的openid不一致，openid={},orderMaster={}",openid,orderMaster);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXITS);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());

        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXITS);
        }

        if(!orderMaster.getBuyerOpenid().equals(orderDTO.getBuyerOpenid())){
            log.error("【订单查询】订单的openid不一致，openid={},orderMaster={}",orderDTO.getBuyerOpenid(),orderMaster);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，oderId={}, orderStatus={}, ", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //写回库存
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderDTO.getOrderId());
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【取消订单】订单中无商品详情，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDERDETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productInfoService.increaseStock(cartDTOList);
        //如果已支付，需要退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO 退款流程
        }

        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单的状态不正确，orderId={},orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改状态
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单的状态不正确，orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderMaster.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确，orderId={}, payStatus={}", orderMaster.getOrderId(), orderMaster.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
}
