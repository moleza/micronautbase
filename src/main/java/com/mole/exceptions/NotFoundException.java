package com.mole.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    private final String itemId;
    private final String itemType;
    private final String entity;

    @Override
    public String getMessage() {
        return entity+" with "+itemType+" : '" + itemId + "' was not found";
    }
}