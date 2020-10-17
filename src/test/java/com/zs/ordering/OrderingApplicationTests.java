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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderingApplicationTests {

    //    注入
    @Resource
    private ProductCategoryMapper productCategoryMapper;

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
    }

}
