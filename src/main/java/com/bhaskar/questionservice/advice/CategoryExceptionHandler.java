package com.bhaskar.questionservice.advice;


import com.bhaskar.questionservice.exception.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<Object> exception(CategoryNotFoundException ex){
        return  new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception ex){
        return  new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
    }

}
