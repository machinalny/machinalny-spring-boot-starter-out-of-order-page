package com.machinalny.autoconfigure;

import com.machinalny.filter.OutOfOrderPageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties(MachinalnyOutOfOrderPageProperties.class)
public class MachinalnyOutOfOrderPageAutoConfiguration {


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "spring.machinalny.enabled", matchIfMissing = true)
    static class DefaultMachinalnyOutOfOrderPageConfiguration {

        @Autowired
        private MachinalnyOutOfOrderPageProperties properties;

        @Bean
        @Order(Integer.MIN_VALUE)
        @ConditionalOnMissingFilterBean(OutOfOrderPageFilter.class)
        @ConditionalOnProperty(name = "spring.machinalny.message", matchIfMissing = true, havingValue = "Service is currently out of order")
        public OutOfOrderPageFilter outOfOrderPageFilter() {
            return new OutOfOrderPageFilter();
        }


        @Bean
        @Order(Integer.MIN_VALUE)
        @ConditionalOnMissingFilterBean(OutOfOrderPageFilter.class)
        @ConditionalOnProperty(name = "spring.machinalny.message")
        public OutOfOrderPageFilter outOfOrderPageFilterWithCustomMessage() {
            return new OutOfOrderPageFilter(properties.getMessage());
        }
    }
}
