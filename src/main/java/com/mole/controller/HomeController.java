package com.mole.controller;

import com.mole.utils.Argon2Encode;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)
public class HomeController {
    @Get(produces = MediaType.TEXT_PLAIN) 
    public String index() {
        String pw = Argon2Encode.encrypt("test");
        System.out.println(pw);
        Boolean check = Argon2Encode.verify(pw, "test");
        System.out.println(check);
        return "Demo API"; 
    }
}
