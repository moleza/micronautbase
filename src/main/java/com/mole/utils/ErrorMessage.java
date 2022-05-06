package com.mole.utils;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;

@Value
@Introspected
public class ErrorMessage {
    String message;
    String errorCode;
}
