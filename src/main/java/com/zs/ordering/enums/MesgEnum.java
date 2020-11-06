package com.zs.ordering.enums;

import lombok.Getter;

@Getter
public enum MesgEnum {
    /**
     * 40003	touser字段openid为空或者不正确
     * 40037	订阅模板id为空不正确
     * 43101	用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系
     * 47003	模板参数不准确，可能为空或者不满足规则，errmsg会提示具体是哪个字段出错
     * 41030	page路径不正确，需要保证在现网版本小程序中存在，与app.json保持一致
     */
    ERR_OPENID(40003,"touser字段openid为空或者不正确哦~"),
    ERR_TPLT(40037,"订阅模板id为空不正确哦~"),
    ERR_REFUSE(43101,"用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系哦~"),
    ERR_TPLT_PARA(47003,"模板参数不准确，可能为空或者不满足规则，errmsg会提示具体是哪个字段出错哦`"),
    ERR_PAGE(41030,"page路径不正确，需要保证在现网版本小程序中存在，与app.json保持一致哦~");

    private Integer code;
    private String name;

    MesgEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
