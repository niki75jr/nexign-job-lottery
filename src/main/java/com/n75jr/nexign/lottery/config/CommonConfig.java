package com.n75jr.nexign.lottery.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Configuration
public class CommonConfig {

    @Bean
    public RestTemplate restTemplate() {
        if (log.isTraceEnabled()) {
            log.trace("@Bean restTemplate created");
        }
        return new RestTemplate();
    }
}
