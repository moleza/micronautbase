package com.mole.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

@Introspected
@Getter
@Setter
public class UserDTO {

    private String name;
    private String surname;
    private Integer level;
    
}
