package com.zs.ordering.vo;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/*
 * 用来封装请求官方接口的参数
 * @author Steven
 * @since 2020-10-30
 * */
@Data
@Configuration
public class WxMssVo {
    private String touser;//用户openid
    private String template_id;//订阅消息模版id
    private String page = "pages/index/index";//默认跳到小程序首页
    private Map<String, TemplateData> data;//推送文字

}