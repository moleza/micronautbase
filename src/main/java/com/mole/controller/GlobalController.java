package com.mole.controller;

import java.util.List;

import com.mole.entity.Global;
import com.mole.entity.GlobalIdTextDTO;
import com.mole.exceptions.NotFoundException;
import com.mole.repository.GlobalRepository;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@Controller("/api/global")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class GlobalController {
    private final GlobalRepository repositoryGlobal;

    @Secured({ "ADMIN","USER" })
    @Get("/{id}")
    public Global findById(@PathVariable long id) {
        return repositoryGlobal.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id), "ID", "Global"));
    }

    @Secured({ "ADMIN","USER" })
    @Get("/code/{code}")
    public List<GlobalIdTextDTO> findByCodeAndEnabled(@PathVariable String code) {
        return repositoryGlobal.findAllByCodeAndEnabled(code, true);
    }
}
