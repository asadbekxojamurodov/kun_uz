package com.example.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//public class SecuredFilterConfig {
//
//    @Autowired
//    private TokenFilter tokenFilter;
//
//    @Bean
//    public FilterRegistrationBean<Filter> filterRegistrationBean() {
//        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(tokenFilter);
//
////   Region
//
//        bean.addUrlPatterns("/region/adm");
//        bean.addUrlPatterns("/region/adm/*");
//        bean.addUrlPatterns("/region/adm/**");
//
////   ArticleType
//
//        bean.addUrlPatterns("/articleType/adm");
//        bean.addUrlPatterns("/articleType/adm/*");
//        bean.addUrlPatterns("/articleType/adm/**");
//
////   Article
//
//        bean.addUrlPatterns("/article/adm");
//        bean.addUrlPatterns("/article/adm/*");
//        bean.addUrlPatterns("/article/adm/**");
//
//        bean.addUrlPatterns("/article/mod");
//        bean.addUrlPatterns("/article/mod/*");
//        bean.addUrlPatterns("/article/mod/**");
//
//        bean.addUrlPatterns("/article/pub");
//        bean.addUrlPatterns("/article/pub/*");
//        bean.addUrlPatterns("/article/pub/**");
//
////   Category
//
//        bean.addUrlPatterns("/category/adm");
//        bean.addUrlPatterns("/category/adm/*");
//        bean.addUrlPatterns("/category/adm/**");
//
////   Profile
//
//        bean.addUrlPatterns("/profile/adm");
//        bean.addUrlPatterns("/profile/adm/*");
//        bean.addUrlPatterns("/profile/adm/**");
//
////   Type
//
//        bean.addUrlPatterns("/types/adm");
//        bean.addUrlPatterns("/types/adm/*");
//        bean.addUrlPatterns("/types/adm/**");
//
//
//        return bean;
//    }
//
//}
