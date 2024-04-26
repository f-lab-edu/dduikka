package com.flab.dduikka.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.flab.dduikka")
public class FeignClientConfig {
}
