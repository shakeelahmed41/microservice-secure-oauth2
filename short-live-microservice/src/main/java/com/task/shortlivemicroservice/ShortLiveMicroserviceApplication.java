package com.task.shortlivemicroservice;

import com.task.controller.RunTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTask
public class ShortLiveMicroserviceApplication {

    @Bean
    public RunTask runTask()
    {
        return new RunTask();
    }

    public static void main(String[] args) {
        SpringApplication.run(ShortLiveMicroserviceApplication.class, args);
    }

}
