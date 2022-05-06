package com.mole.exceptions;

import com.mole.utils.ErrorMessage;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Requires(classes = NotFoundException.class)
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundException, HttpResponse<ErrorMessage>> {

    @Override
    public HttpResponse<ErrorMessage> handle(HttpRequest request, NotFoundException exception) {
        return HttpResponse.notFound(new ErrorMessage(exception.getMessage(), "NOT_FOUND"));
    }
}