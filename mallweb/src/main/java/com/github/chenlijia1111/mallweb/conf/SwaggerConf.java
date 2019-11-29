package com.github.chenlijia1111.mallweb.conf;

import com.github.chenlijia1111.mallweb.common.pojo.Constants;
import com.github.chenlijia1111.utils.common.enums.ResponseStatusEnum;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/5 0005 上午 11:31
 **/
@Configuration
public class SwaggerConf {


    /**
     * 初始化创建 docket
     *
     * @param controllerPathPattern 控制器通配符拦截,表示显示哪些控制器
     * @param groupName             组名
     * @return springfox.documentation.spring.web.plugins.Docket
     * @author chenlijia
     * @since 下午 12:56 2019/8/5 0005
     **/
    private Docket initCreateDocket(String controllerPathPattern, String groupName) {
        ParameterBuilder builder = new ParameterBuilder();
        builder.name(Constants.TOKEN).description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        ArrayList<Parameter> parameters = Lists.newArrayList();
        parameters.add(builder.build());

        ApiInfo apiInfo = new ApiInfoBuilder().title("商城系统API文档").
                contact(new Contact("陈礼佳", "http://192.168.1.134:8087/jiuyou", "571740367@qq.com")).
                version("1.0").description("恩泰生态商城系统接口").build();

        ApiSelectorBuilder apis = new Docket(DocumentationType.SWAGGER_2).
                apiInfo(apiInfo).select().apis(RequestHandlerSelectors.any());
        if (StringUtils.hasText(controllerPathPattern)) {
            apis.paths(PathSelectors.ant(controllerPathPattern));
        } else {
            apis.paths(PathSelectors.any());
        }

        Docket docket = apis.build();

        if (StringUtils.hasText(groupName)) {
            docket.groupName(groupName);
        }

        //header
        docket.globalOperationParameters(parameters);
        return docket;
    }


    /**
     * 注入swagger配置
     *
     * @return
     */
    @Bean
    public Docket createApiAdmin() {

        Docket docket = initCreateDocket("/admin/**", "后台");
        docket.useDefaultResponseMessages(false);
        createResponseMessage(docket);
        return docket;
    }


    /**
     * 注入swagger配置
     *
     * @return
     */
    @Bean
    public Docket createClientShop() {

        Docket docket = initCreateDocket("/app/**", "客户端");
        docket.useDefaultResponseMessages(false);
        createResponseMessage(docket);
        return docket;
    }

    /**
     * 注入swagger配置
     *
     * @return
     */
    @Bean
    public Docket createApiSystem() {

        Docket docket = initCreateDocket("/system/**", "系统接口");
        docket.useDefaultResponseMessages(false);
        createResponseMessage(docket);
        return docket;
    }


    /**
     * 设置返回值状态
     *
     * @param docket 1
     * @return void
     * @author chenlijia
     * @since 下午 12:42 2019/8/5 0005
     **/
    private void createResponseMessage(Docket docket) {
        List<ResponseMessage> list = Lists.newArrayList();
        ResponseStatusEnum[] values = ResponseStatusEnum.values();
        for (ResponseStatusEnum value : values) {
            int code = value.getCode();
            String name = value.getName();

            Map<String, Header> headers = new HashMap<>();
            List<VendorExtension> vendorExtensions = new ArrayList<>();

            ResponseMessage responseMessage = new ResponseMessage(code, name, new ModelRef(""), headers, vendorExtensions);
            list.add(responseMessage);
        }

        docket.globalResponseMessage(RequestMethod.GET, list);
        docket.globalResponseMessage(RequestMethod.POST, list);
        docket.globalResponseMessage(RequestMethod.PUT, list);
        docket.globalResponseMessage(RequestMethod.DELETE, list);
        docket.globalResponseMessage(RequestMethod.OPTIONS, list);


    }


}
