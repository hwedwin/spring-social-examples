package com.tanghuan.dev.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.List;

/**
 * Created by Arthur on 2017/4/13.
 */

@Configuration
@ComponentScan(basePackages = {"com.tanghuan.dev.oauth.controller"})
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(deviceHandlerMethodArgumentResolver());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        vr.setCharacterEncoding("UTF-8");
        vr.setContentType("text/html");
        registry.viewResolver(vr);
    }

    @Bean
    public ITemplateEngine templateEngine() {
        SpringTemplateEngine te = new SpringTemplateEngine();
        te.setTemplateResolver(templateResolver());
        return te;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver tr = new SpringResourceTemplateResolver();
        tr.setTemplateMode("HTML");
        tr.setPrefix("classpath:/templates/");
        tr.setSuffix(".html");
        tr.setCharacterEncoding("UTF-8");
        tr.setCacheable(false);
        tr.setOrder(2);
        return tr;
    }

    @Bean
    public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }

}
