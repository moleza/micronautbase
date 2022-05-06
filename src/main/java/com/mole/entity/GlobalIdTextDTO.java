package com.mole.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

@Introspected
@Getter
@Setter
public class GlobalIdTextDTO {

    private Long id;
    private String text;
    
}
