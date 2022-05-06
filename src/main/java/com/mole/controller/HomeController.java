package com.mole.controller;

import com.mole.utils.Aes256;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)
public class HomeController {

    @Value("${micronaut.application.secretKey}")
    private String key;
    @Value("${micronaut.application.secretSalt}")
    private String salt;

    @Get(produces = MediaType.TEXT_PLAIN) 
    public String index() {
        String pw = Aes256.encrypt("test",key,salt);
        System.out.println(pw);

        return "Demo API"; 
    }
}
