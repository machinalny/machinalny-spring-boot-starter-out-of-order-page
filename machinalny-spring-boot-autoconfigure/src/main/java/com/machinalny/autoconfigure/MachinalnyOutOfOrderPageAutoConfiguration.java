package com.machinalny.autoconfigure;

import com.machinalny.filter.OutOfOrderPageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties(MachinalnyOutOfOrderPageProperties.class)
public class MachinalnyOutOfOrderPageAutoConfiguration {


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "spring.machinalny.enabled")
    static class DefaultMachinalnyOutOfOrderPageConfiguration {

        Logger log = LoggerFactory.getLogger("OutOfOrderPageAutoConfiguration");

        @Bean
        @Order(Integer.MIN_VALUE)
        @ConditionalOnMissingFilterBean(OutOfOrderPageFilter.class)
        @ConditionalOnProperty(name = "spring.machinalny.pathToReturnPage", matchIfMissing = true, havingValue = "out_of_order_page.html")
        public OutOfOrderPageFilter outOfOrderPageFilter() {
            return new OutOfOrderPageFilter();
        }


        @Bean
        @Order(Integer.MIN_VALUE)
        @ConditionalOnMissingFilterBean(OutOfOrderPageFilter.class)
        @ConditionalOnProperty(name = "spring.machinalny.pathToReturnPage")
        public OutOfOrderPageFilter outOfOrderPageFilterWithCustomMessage(MachinalnyOutOfOrderPageProperties properties, ResourceLoader resourceLoader) {
            Resource resource = resourceLoader.getResource(properties.getPathToReturnPage());
            if (!isOutOfOrderPageResourceValid(resource)){
                log.error("Unable to locate proper out of order page, please ensure that your page is in *.html format and placed in resources/static");
                log.warn("Fallback to default maintenance page");
                return new OutOfOrderPageFilter();
            }
            return new OutOfOrderPageFilter(properties.getPathToReturnPage());
        }


        private boolean isOutOfOrderPageResourceValid(Resource resource){
            return resource.exists() && resource.isFile() && resource.isReadable() && resource.getFilename().endsWith(".html");

        }
    }
}
