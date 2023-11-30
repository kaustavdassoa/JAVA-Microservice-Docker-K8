package com.example.orderservice.config;

import com.example.orderservice.external.intercept.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestTemplateConfig {



    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Autowired
    OAuth2ClientConfig oAuth2ClientConfig;

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate()
    {
        RestTemplate restTemplate
                = new RestTemplate();
        restTemplate.setInterceptors(
                Arrays.asList(
                        new RestTemplateInterceptor(
                                oAuth2ClientConfig.clientManager(clientRegistrationRepository
                                        ,oAuth2AuthorizedClientRepository))));
        return restTemplate;

    }



}
