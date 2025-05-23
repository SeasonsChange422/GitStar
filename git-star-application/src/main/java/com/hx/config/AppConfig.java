package com.hx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author dhx
 * @date 2025/4/2 18:00
 */
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 获取当前所有消息转换器
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        // 添加 ResourceHttpMessageConverter 到最前面（确保优先处理二进制流）
        converters.add(0, new ResourceHttpMessageConverter());
        // 重新设置转换器列表
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }
}
