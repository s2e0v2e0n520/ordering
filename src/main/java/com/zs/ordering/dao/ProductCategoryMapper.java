package com.zs.ordering.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.ordering.dto.ProductCategory;
/*
* ProductCategoryMapper接口
* */
/*1.使用MyBatis-Plus的方法需要继承MP的通用Mapper
* 2.BaseMapper<>尖括号内是操作实体类
* */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

}
