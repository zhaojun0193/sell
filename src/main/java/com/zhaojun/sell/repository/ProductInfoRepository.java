package com.zhaojun.sell.repository;

import com.zhaojun.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zhaojun0193
 * @create 2017/10/18
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer status);

}
