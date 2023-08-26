package com.bhaskar.questionservice.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
