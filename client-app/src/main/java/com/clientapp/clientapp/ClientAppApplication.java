package com.clientapp.clientapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan(basePackages = {"com.clientapp"})
public class ClientAppApplication{

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientAppApplication.class, args);
    }

}
