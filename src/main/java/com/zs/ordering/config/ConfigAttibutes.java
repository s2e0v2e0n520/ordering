package com.zs.ordering.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "api.apiurl")
@Component
public class ConfigAttibutes {

    private  String  subscribeMessage;

    private  Integer doctorid;

    private  String uniformMessage;

}
