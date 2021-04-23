package com.fancv.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2 {
    @Bean
    public Docket appApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("appId").description("应用ID")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2).groupName("app（codeBase）").genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(appApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fancv.report.controller"))//扫描的包
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
//                .paths(or(regex("/user/.*")))//过滤
                .build()
                .globalOperationParameters(pars);
    }

    @Bean
    public Docket prjAppApi() {
        // 每个接口都共有的参数
        ParameterBuilder param1 = new ParameterBuilder();
        param1.name("sign").description("签名").modelRef(new ModelRef("string")).parameterType("query").required(false).build();
        ParameterBuilder param2 = new ParameterBuilder();
        param2.name("sessionId").description("sessionId").modelRef(new ModelRef("string")).parameterType("query").required(false).build();
        ParameterBuilder param3 = new ParameterBuilder();
        param3.name("userId").description("userId").modelRef(new ModelRef("integer")).parameterType("query").required(false).build();
        ParameterBuilder param4 = new ParameterBuilder();//参数非必填，传空也可以
        param4.name("appVersion").description("app版本").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        List<Parameter> aParameters = Lists.newArrayList();
        aParameters.add(param1.build());
        aParameters.add(param2.build());
        aParameters.add(param3.build());
        aParameters.add(param4.build());

        return new Docket(DocumentationType.SWAGGER_2).groupName("prjApp（工程版app）").genericModelSubstitutes(ResponseEntity.class)
                .globalOperationParameters(aParameters)
                .apiInfo(prjAppApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gemdale.iot.prj.controller"))//扫描的包
//                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
//                .paths(or(regex("/user/.*")))//过滤
                .build();
    }

    private ApiInfo appApiInfo() {
        return new ApiInfoBuilder()
                .title("xxx接口文档-APP")
                .description("该文档主要提供APP 端的接口 \r\n\n"
                        + "请求服务:http//10.34.4.47:8777/cloud/  （测试服务器不支持https）\r\n\n"
                        + "返回：  \r\n\n"
                        + "ReturnBody<T> {\"code\":\"标识码\",\"msg\":\"描述\",data{ json字符串（对象）  } \r\n\n"
                        + "")
                .contact(new springfox.documentation.service.Contact("xxx业务后台开发组", null, null))
                .version("1.0.0")
                .build();
    }


    private ApiInfo prjAppApiInfo() {
        return new ApiInfoBuilder()
                .title("xxx接口文档-工程版APP")
                .description("该文档主要提供工程版APP端的接口 \r\n\n"
                        + "请求服务:http//10.34.4.47:18777/  \r\n\n"
                        + "返回：  \r\n\n"
                        + "ReturnBody<T> {\"code\":\"标识码\",\"msg\":\"描述\",data{ json字符串（对象）  } \r\n\n"
                        + "")
                .contact(new springfox.documentation.service.Contact("xxxx业务后台开发组", null, null))
                .version("1.0.0")
                .build();
    }
}