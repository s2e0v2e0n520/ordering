package com.zs.ordering.controller;

import com.zs.ordering.config.ProjectUrlConfig;
import lombok.extern.slf4j.Slf4j;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.annotation.Resource;

/*
 * 使用第三方SDK网页授权
 * 微信授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
 * redirect_uri
 * */
//@RestController
//重定向！！！！！类上用Restcontroller时返回jason不会重定向跳转要用@Controller

//@RestController
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Resource
    private WxMpService wxMpService;

    @Resource
    private ProjectUrlConfig projectUrlConfig;

    /*第一步：用SDK拼接成带appid，跳转链接、scope等的授权链接*/
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnurl")String returnUrl){
        //1.配置(去congig中配置了)
        //2.调用方法

        /*
        * redirectUrl是带参数（重定向url，scope，appid等的）重定向的url
        * returnurl为重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
        */
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(projectUrlConfig.getWechatMpAuthorize(), WxConsts.OAUTH2_SCOPE_USER_INFO, UriEncoder.encode(returnUrl));
        log.info("微信网页授权获取code的带参数地址,result={}",redirectUrl);
        //重定向！！！！！类上用Restcontroller时返回jason不会重定向跳转，所以用了Controller
        return  "redirect:" +redirectUrl;
    }
    //获取用户信息方法,（网页授权转发地址）
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl ){
        log.info("获取到的code{}",code);
        /*第三步：通过code换取网页授权access_token*/
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken=  wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
//            e.printStackTrace();
            log.error("[微信网页授权]{}",e);
//            throw new
        }
        //获取openid
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("获取到的openid{}",openId);

        //重定向
        String A=returnUrl+"?openid="+openId;
        log.info("返回openid的url{}",A);
                return "redicert:"+ returnUrl+"?openid="+openId;

    }
}
