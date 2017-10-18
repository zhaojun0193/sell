package com.zhaojun.sell.repository;

import com.zhaojun.sell.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zhaojun0193
 * @create 2017/10/18
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>{

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
