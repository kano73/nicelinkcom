package com.nicelink.nicer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register").setViewName("registerpage");
        registry.addViewController("/login").setViewName("loginpage");
        registry.addViewController("/profile").setViewName("profilepage");
        registry.addViewController("/actions").setViewName("actionspage");
        registry.addViewController("/createlink").setViewName("createlinkpage");
        registry.addViewController("/nl/**").setViewName("redirectingpage");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/notfound").setViewName("notfound");
        registry.addViewController("/mylinks").setViewName("mylinkspage");
    }
}
