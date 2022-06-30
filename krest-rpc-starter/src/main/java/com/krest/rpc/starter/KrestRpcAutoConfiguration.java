package com.krest.rpc.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KrestRpcProperties.class)
public class KrestRpcAutoConfiguration {

    private KrestRpcProperties configProperties;

    public KrestRpcAutoConfiguration(KrestRpcProperties configProperties) {
        this.configProperties = configProperties;
    }

    /**
     * 实例化 KrestJobService并载入Spring IoC容器
     */
    @Bean
    @ConditionalOnMissingBean
    public KrestRpcService krestJobService() {
        // 在这个方法中，可以实现注册服务的方法
        KrestRpcService krestJobService = new KrestRpcService(this.configProperties);
        return krestJobService;
    }
}
