package com.krest.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("server")
public class ServerController {

    @GetMapping("hello")
    public String sayHello() {
        return "hello";
    }
}
