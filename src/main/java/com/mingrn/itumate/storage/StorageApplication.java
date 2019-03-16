package com.mingrn.itumate.storage;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.mingrn.itumate")
public class StorageApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(StorageApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}