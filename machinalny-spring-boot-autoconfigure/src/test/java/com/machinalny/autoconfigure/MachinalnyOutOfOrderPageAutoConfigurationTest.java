package com.machinalny.autoconfigure;

import com.machinalny.filter.OutOfOrderPageFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MachinalnyOutOfOrderPageAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MachinalnyOutOfOrderPageAutoConfiguration.class));


    @Test
    void outOfOrderFilterIsPresent(){
        this.contextRunner.run((context -> {
            assertThat(context).hasSingleBean(OutOfOrderPageFilter.class);
        }));
    }
}