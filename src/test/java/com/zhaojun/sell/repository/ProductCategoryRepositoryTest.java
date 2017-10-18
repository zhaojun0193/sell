package com.zhaojun.sell.repository;

import com.zhaojun.sell.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void finOneTest(){
        ProductCategory category = repository.findOne(1);
        System.out.println(category);
    }

    @Test
    @Transactional
    public void saveCategory(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("女生最爱");
        category.setCategoryType(2);
        ProductCategory result = repository.save(category);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateCategory(){
        ProductCategory category = new ProductCategory();
        category.setCategoryId(3);
        category.setCategoryType(2);
        category.setCategoryName("男生最爱");
        repository.save(category);
    }

    @Test
    public void deleteCteGory(){
        repository.delete(6);
    }

    @Test
    public void findByCateGoryTypeTst(){
        List<Integer> categoryTypeList = Arrays.asList(1,2,3);

        List<ProductCategory> categories = repository.findByCategoryTypeIn(categoryTypeList);

        for (ProductCategory category: categories){
            System.out.println(category);
        }
    }
}