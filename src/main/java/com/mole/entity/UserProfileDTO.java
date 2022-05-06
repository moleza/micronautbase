package com.mole.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Introspected
@Getter
@Setter
@ToString
public class UserProfileDTO {

    private String mobile;
    private String gender;
    private String birthdate;
   
}
