package com.tanghuan.dev.oauth.config;

import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by Arthur on 2017/4/13.
 */

@Configuration
@Import(value = {JpaConfig.class, SocialConfig.class})
@ComponentScan(
        basePackages = {
                "com.tanghuan.dev.oauth.service.impl",
                "com.tanghuan.dev.oauth.exception"
        }
)
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 配置文件上传的解析器
     * @return
     */
    @Bean(name = "filterMultipartResolver")
    public CommonsMultipartResolver filterMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();

        // 这里设置的允许最大上传50M的文件
        resolver.setMaxUploadSize(50000000);
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

}
