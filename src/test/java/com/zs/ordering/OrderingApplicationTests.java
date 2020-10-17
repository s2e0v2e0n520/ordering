package com.zs.ordering;

import com.zs.ordering.dao.ProductCategoryMapper;
import com.zs.ordering.dto.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderingApplicationTests {

    //    注入
    @Resource
    private ProductCategoryMapper productCategoryMapper;

    /*1.查询所有*/
    @Test
    public void select() {
        log.debug("debug");
        log.error("err");
        log.info("info");
        //传入null代表查询所有
        List<ProductCategory> list = productCategoryMapper.selectList(null);
        //判断是五条
        Assert.assertEquals(1, list.size());
        // 遍历list
        list.forEach(System.out::println);
        log.info("查询到的user==》{}", list.toString());
    }

    /*2.根据id查询一条*/
    @Test
    public void selectById() {
        ProductCategory productCategory = new ProductCategory();

        productCategory = productCategoryMapper.selectById("1");
//        productCategory =productCategoryMapper.selectOne();
        System.out.println("一条数据==>" + productCategory);

    }

    /*2.1根据ids查询多条*/
    @Test
    public void selectByIds() {
//        ProductCategory productCategory = new ProductCategory();
        List<Integer> idList = Arrays.asList(1, 2);
        List<ProductCategory> productCategoryList = productCategoryMapper.selectBatchIds(idList);
//        productCategory =productCategoryMapper.selectOne();
        productCategoryList.forEach(System.out::println);
    }

    /*2.3根据Map查询*/
    @Test
    public void selectByMap() {
        Map<String,Object> productCategoryMap=new HashMap<>();
        productCategoryMap.put("category_name","男装");
        productCategoryMap.put("category_type",3);
        //ToDo,这里Map的K要和数据库中的字段一致，而不是实体类中的属性
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByMap(productCategoryMap);
        productCategoryList.forEach(System.out::println);
    }
    /*3.新增一条*/
    @Test
    public void addById() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(4);
        productCategory.setCategoryName("男装4");
        productCategory.setCategoryType(43);
        productCategory.setRemark("我是一个备注，在数据库中没有对应字段哦");
        int rows = productCategoryMapper.insert(productCategory);
        log.info("影响行数{}", rows);
    }
}
