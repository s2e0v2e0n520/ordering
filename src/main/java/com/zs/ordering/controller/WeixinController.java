package com.zs.ordering.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* 微信原生方法网页授权
* 微信授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
* redirect_uri
* */
//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb7ab501cd35f2e1c&redirect_uri=http%3a%2f%2fwww.zondarchina.com%2f&response_type=code&scope=snsapi_userinfo&state=1234#wecha
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("code:{}",code);

    }
}
