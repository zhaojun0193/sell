package com.zhaojun.sell.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author zhaojun0193
 * @create 2017/10/18
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {

    @Id
    private String productId;

    /**
     * 名字
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private Integer productStock;

    /**
     * 描述
     */
    private String productDescription;

    /**
     * 图片
     */
    private String productIcon;

    /**
     * 状态
     */
    private Integer productStatus;

    /**
     * 类目编号
     */
    private Integer categoryType;
}
