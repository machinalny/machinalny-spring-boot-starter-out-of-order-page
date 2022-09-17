package com.machinalny.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("machinalny")
public class MachinalnyOutOfOrderPageProperties {

    private String outOfOrderMessage;

    public String getOutOfOrderMessage() {
        return outOfOrderMessage;
    }

    public void setOutOfOrderMessage(String outOfOrderMessage) {
        this.outOfOrderMessage = outOfOrderMessage;
    }
}
