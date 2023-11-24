package com.swx.rpc.consumer.controller;

import com.rrtv.rpc.client.annotation.RpcAutowired;
import com.swx.rpc.api.service.HelloWorldService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class HelloController {
    @RpcAutowired(version = "1.0")
    private HelloWorldService helloWordService;

    @GetMapping("/hello/world")
    public ResponseEntity<String> pullServiceInfo(@RequestParam("name") String name) {
            return org.springframework.http.ResponseEntity.ok(helloWordService.sayHello(name));
    }

}
