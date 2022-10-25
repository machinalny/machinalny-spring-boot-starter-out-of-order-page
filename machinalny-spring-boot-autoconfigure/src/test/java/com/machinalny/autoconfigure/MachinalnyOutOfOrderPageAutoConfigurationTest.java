package com.machinalny.autoconfigure;

import com.machinalny.filter.OutOfOrderPageFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class MachinalnyOutOfOrderPageAutoConfigurationTest {


    @Test
    void outOfOrderFilterIsNotPresentByDefault() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(WebMvcAutoConfiguration.class,
                        MachinalnyOutOfOrderPageAutoConfiguration.class));
        contextRunner.run((context) -> assertThat(context).doesNotHaveBean(OutOfOrderPageFilter.class));
    }

    @Test
    void outOfOrderFilterIsPresentWhenEnabled() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(WebMvcAutoConfiguration.class,
                        MachinalnyOutOfOrderPageAutoConfiguration.class));
        contextRunner.withPropertyValues("spring.machinalny.enabled=true")
                .run((context) -> assertThat(context).hasSingleBean(OutOfOrderPageFilter.class));
    }

    @Test
    void defaultOutOfOrderFilterBacksOff() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(WebMvcAutoConfiguration.class,
                        MachinalnyOutOfOrderPageAutoConfiguration.class));
        contextRunner.withUserConfiguration(OutOfOrderPageFilterConfig.class).run((context) -> {
            assertThat(context).hasSingleBean(OutOfOrderPageFilter.class);
            assertThat(context.getBean(OutOfOrderPageFilter.class))
                    .isSameAs(context.getBean(OutOfOrderPageFilterConfig.class).myOutOfOrderPageFilter());
        });
    }

    @Test
    void outOfOrderFilterIsIgnoredIfLibraryIsNotPresent() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(WebMvcAutoConfiguration.class));
        contextRunner.withClassLoader(new FilteredClassLoader(OutOfOrderPageFilter.class))
                .run((context) -> assertThat(context).doesNotHaveBean("outOfOrderPageFilter"));
    }

    @Test
    void outOfOrderFilterIsNotPresentWhenDisabled() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(WebMvcAutoConfiguration.class,
                        MachinalnyOutOfOrderPageAutoConfiguration.class));
        contextRunner.withPropertyValues("spring.machinalny.enabled=false")
                .run((context) -> assertThat(context).doesNotHaveBean(OutOfOrderPageFilter.class));
    }

    @Test
    void outOfOrderFilterMessageIsConfigured() {
        final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(WebMvcAutoConfiguration.class,
                        MachinalnyOutOfOrderPageAutoConfiguration.class));
        contextRunner.withPropertyValues("spring.machinalny.pathToReturnPage=/static/outOfOrderPage.html", "spring.machinalny.enabled=true")
                .run((context) -> {
                    assertThat(context).hasSingleBean(OutOfOrderPageFilter.class);
                    assertThat(context.getBean(OutOfOrderPageFilter.class).getMaintenancePageFilePath()).isEqualTo("/static/outOfOrderPage.html");
                });
    }


    @Configuration
    static class OutOfOrderPageFilterConfig {

        @Bean
        public OutOfOrderPageFilter myOutOfOrderPageFilter() {
            return new OutOfOrderPageFilter();
        }

    }
}