package com.BikkadIt.electronic.store.exception;


import com.BikkadIt.electronic.store.dtos.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse>resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

       logger.info("Exception Handler Started !!");
        ApiResponse build = ApiResponse.builder()
                            .message(ex.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                             .success(true).build();
        return new ResponseEntity<>(build,HttpStatus.NOT_FOUND);

    }




}
