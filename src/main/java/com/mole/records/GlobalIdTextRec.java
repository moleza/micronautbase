package com.mole.records;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected 
@ReflectiveAccess
public record GlobalIdTextRec (    
    @NotNull @NotBlank @PositiveOrZero Long id,
    @NotNull @NotBlank @Size(max = 255) String text
) 
{ };
