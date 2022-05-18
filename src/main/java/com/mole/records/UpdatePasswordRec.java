package com.mole.records;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected 
@ReflectiveAccess
public record UpdatePasswordRec(
    @NotNull @NotBlank @Size(max = 64) String oldpassword, 
    @NotNull @NotBlank @Size(max = 64) String newpassword
) 
{ };
