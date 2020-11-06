package com.zs.ordering.enums;

import lombok.Getter;

@Getter
public enum UniformMesgEnum {


    ERR_Temp(40037,"模板id不正确，weapp_template_msg.template_id或者mp_template_msg.template_id哦~"),
    ERR_28(41028,"weapp_template_msg.form_id过期或者不正确哦~"),
    ERR_29(41029,"weapp_template_msg.form_id已被使用哦`"),
    ERR_30(41030,"weapp_template_msg.page不正确哦~"),
    ERR_OutNmber(45009,"接口调用超过限额哦~"),
    ERR_OPENID(40003,"touser不是正确的openid哦~"),
    ERR_APPID(40013,"appid不正确，或者不符合绑定关系要哦~"),
    OK(0,"发送成功哦~");
    private Integer code;
    private String name;

    UniformMesgEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
