package com.machinalny.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.machinalny")
public class MachinalnyOutOfOrderPageProperties {

    private boolean enabled;
    private String pathToReturnPage;

    public String getPathToReturnPage() {
        return pathToReturnPage;
    }

    public void setPathToReturnPage(String pathToReturnPage) {
        this.pathToReturnPage = pathToReturnPage;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
