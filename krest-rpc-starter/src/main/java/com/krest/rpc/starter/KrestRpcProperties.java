package com.krest.rpc.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "krest.rpc")
public class KrestRpcProperties {
    String host;
    Integer port;
}
