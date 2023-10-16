package com.c88.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpHeaders.*;

@FeignClient(name = "c88-auth", path = "/auth")
public interface AuthFeignClient {

    @PostMapping("/oauth/token")
    Object token(@RequestHeader(AUTHORIZATION) String authorization,
                 @RequestParam Map<String, String> parameters);

    @GetMapping("/oauth/googleRecaptcha/{userName}")
    boolean googleRecaptcha(@PathVariable("userName") String userName, @RequestHeader("token") String token);// feign client 不能 return void，才會使用 errorDecoder


    @DeleteMapping("/oauth/clean")
    public boolean cleanToken(@RequestParam(value = "clientId") String clientId,
                              @RequestParam(value = "username", required = false) String username);



    }
