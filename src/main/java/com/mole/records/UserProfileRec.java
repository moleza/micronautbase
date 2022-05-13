package com.mole.records;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected
@ReflectiveAccess
public record UserProfileRec(
    @NotNull @NotBlank @Size(max = 15) String mobile,
    @NotNull @NotBlank @Size(max = 7) String gender,
    @Nullable String birthdate
)
{ };
