package com.zs.ordering.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Configuration
@Component
public class WechatMpConfig {
    @Resource
    private WechatAccountConfig accountConfig;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService= new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return  wxMpService;
    }
    /*微信客户端配置存储*/
    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
//       WxMpConfigStorage wxMpConfigStorage =new WxMpInMemoryConfigStorage();
        //set方法在实现类WxMpInMemoryConfigStorage（基于内存的配置provider,在实际生产中，这些配置应该持久化）中，so↓
        WxMpInMemoryConfigStorage wxMpConfigStorage =new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
