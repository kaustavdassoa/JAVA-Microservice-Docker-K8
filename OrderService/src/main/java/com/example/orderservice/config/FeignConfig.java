package com.example.orderservice.config;

import com.example.orderservice.external.decoder.CustomErrorDecoder;
import feign.Capability;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig{

    @Bean
    ErrorDecoder getCustomFeignErrorDecode()
    {
       return new CustomErrorDecoder();

    }



}
