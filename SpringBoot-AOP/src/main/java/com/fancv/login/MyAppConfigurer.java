package com.fancv.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan
public class MyAppConfigurer implements WebMvcConfigurer {

    @Bean
    public MappedInterceptor getMappedInterceptor() {
        //注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        //拦截路径 ("/**")对所有请求都拦截
        String[] includePatterns = new String[]{"/**"};
        //排除拦截路径
        String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html/**",
                "/api", "/api-docs", "/api-docs/**"};

        //将数组转化为集合
        List<String> listOldExclude = Arrays.asList(excludePatterns);

        //将自定义的排除拦截路径添加到集合中
        List<String> listNewExclude = new ArrayList<>();
        listNewExclude.add("/demoJson/getCityList.json");
        listNewExclude.add("/demoJson/getStudentList.json");

        //定义新集合
        List<String> listExclude = new ArrayList<>();
        listExclude.addAll(listOldExclude);
        listExclude.addAll(listNewExclude);

        //将新集合转化回新数组
        String[] newExcludePatterns = listExclude.toArray(new String[listExclude.size()]);

        return new MappedInterceptor(includePatterns, newExcludePatterns, loginInterceptor);
    }

}
