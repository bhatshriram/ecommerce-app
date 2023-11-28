package com.productservice.productservice.controllers;

import com.productservice.productservice.dtos.ExceptionDto;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvices {

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException
                                                                                productNotFoundException) {

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage(productNotFoundException.getMessage());

        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
}
