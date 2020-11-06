package com.zs.ordering.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
* TemplateData 用来定义消息的内容
*Steven
* */
@Data
@AllArgsConstructor
public class TemplateData {

    private String value;
    private  String  color;
//只含有value的构造方法（小程序订阅消息中data只有一个值value）
    public TemplateData(String value) {
        this.value = value;
    }

//    public TemplateData(String value,String color) {
//        this.value = value;
//        this.color=color;
//    }

//@DATA的构造方法在统一客服消息中的模板消息中的data
}