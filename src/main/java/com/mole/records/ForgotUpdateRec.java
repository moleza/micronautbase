package com.mole.records;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected 
@ReflectiveAccess
public record ForgotUpdateRec(
    @NotNull @NotBlank @Email String email, 
    @NotNull @NotBlank @Size(max = 64) String password, 
    @NotNull @NotBlank @Size(max = 255) String token
) 
{ };
