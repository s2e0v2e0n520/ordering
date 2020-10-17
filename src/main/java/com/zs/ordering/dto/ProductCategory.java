package com.zs.ordering.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 类目实体类
 * Created by zs
 * 2020-09-30
 * */
@Data
//当数据库名和实体类名不一致时，用@TableName指定包名，如：@TableName("zs_ProductCategory")
public class ProductCategory {
//    默认将“id”作为主键，当没有“id”属性时，需要指定主键，如@TableId
    /*类目id*/
    @TableId
    private Integer categoryId;
    /*类目名*/
//    默认规则，实体类中驼峰书写在数据库对应变为_,若数据库字段名和实体类对不上，用@TableField("user_name")指定在数据库中的字段名
    private String categoryName;
    /*类目编号*/
    private int categoryType;




}
