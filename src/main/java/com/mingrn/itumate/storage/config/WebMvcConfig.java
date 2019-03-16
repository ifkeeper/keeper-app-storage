package com.mingrn.itumate.storage.config;

import com.mingrn.itumate.global.config.GlobalWebMvcConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * Mvc 配置
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-01-29 11:08
 */
//@Configuration
public class WebMvcConfig extends GlobalWebMvcConfig {

    @Value("${storage.real.path}")
    private String storageRealPath;

    /**
     * addResourceLocations: 映射静态路径地址,一定要使用 / 结尾
     * <br>
     * classpath: 项目路径
     * file:本地路径,如:file:E:/tmp/;file:/tmp/
     * jar:jar资源文件,如:jar://tmp.jar
     * ftp:FTP协议资源文件,如:ftp://
     * you can find more information from <a href="https://blog.csdn.net/ljyhust/article/details/83421749"></a>
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger-ui 资源映射
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/store/**").addResourceLocations("file:" + storageRealPath);
    }*/
}
