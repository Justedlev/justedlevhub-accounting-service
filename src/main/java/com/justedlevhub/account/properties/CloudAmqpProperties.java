package com.justedlevhub.account.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Getter
@Setter
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "cloudamqp")
public class CloudAmqpProperties {
    private String host;
    private String port;
    private String password;
    private String username;
    private String virtualHost;
    private Queue queue;

    @Getter
    @Setter
    @ConfigurationPropertiesScan
    @ConfigurationProperties(prefix = "cloudamqp.queue")
    public static class Queue {
        private String sendTemplateMail;
    }
}
