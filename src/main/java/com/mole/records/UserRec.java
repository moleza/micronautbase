package com.mole.records;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected
@ReflectiveAccess
public record UserRec ( 
    @NotNull @NotBlank @Size(max = 50) String name,
    @NotNull @NotBlank @Size(max = 50) String surname,
    @NotNull @NotBlank @PositiveOrZero Integer level
)
{ };
