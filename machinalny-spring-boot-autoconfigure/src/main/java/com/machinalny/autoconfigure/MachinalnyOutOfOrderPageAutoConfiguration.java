package com.machinalny.autoconfigure;

import com.machinalny.filter.OutOfOrderPageFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@AutoConfigureAfter(WebMvcAutoConfiguration.class)
//@ConditionalOnClass()
//@ConditionalOnMissingBean()
@EnableConfigurationProperties(MachinalnyOutOfOrderPageProperties.class)
public class MachinalnyOutOfOrderPageAutoConfiguration {

    @Bean
    public OutOfOrderPageFilter testBean(){
        return new OutOfOrderPageFilter();
    }

}
