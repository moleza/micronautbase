package com.mole.controller;

import com.mole.utils.Aes256Gcm;

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
        // String pw = Argon2Encode.encrypt("test");
        // System.out.println(pw);
        // Boolean check = Argon2Encode.verify(pw, "test");
        // System.out.println(check);
        String pw = Aes256Gcm.encrypt("sheldon@mole.co.za","12345678901234567890123456789012");
        System.out.println(pw);
        return "Demo API"; 
    }
}
