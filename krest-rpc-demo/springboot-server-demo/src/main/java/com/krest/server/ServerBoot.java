package com.krest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.krest")
public class ServerBoot {
    public static void main(String[] args) {
        SpringApplication.run(ServerBoot.class, args);
    }
}
