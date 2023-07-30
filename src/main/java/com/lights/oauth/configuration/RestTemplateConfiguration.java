package com.lights.oauth.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestTemplateConfiguration {

    @Bean(name = "restTemplate")
    public RestTemplate oscarRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

}
