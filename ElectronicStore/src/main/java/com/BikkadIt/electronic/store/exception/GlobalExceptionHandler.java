package com.BikkadIt.electronic.store.exception;


import com.BikkadIt.electronic.store.dtos.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse>resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        log.info("ResourceNotFoundException Exception Handler Started !!");
        ApiResponse build = ApiResponse.builder()
                            .message(ex.getMessage())
                            .status(HttpStatus.NOT_FOUND)
                            .success(true).build();
        log.info("ResourceNotFoundException Exception Handler Ended !!");
        return new ResponseEntity<>(build,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,Object>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {

        log.info("MethodArgumentNotValidException Exception Handler Started !!");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response=new HashMap<>();
        allErrors.stream().forEach(objectError ->
        {
            String defaultMessage = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,defaultMessage);
        });
        log.info("MethodArgumentNotValidException Exception Handler Ended !!");
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //bad APi Exception
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse>badApiResponse(BadApiRequest ex)
    {

        log.info(" BadApiRequest Exception Handler Started !!");
        ApiResponse build = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false).build();
        log.info(" BadApiRequest Exception Handler Ended !!");
        return new ResponseEntity<>(build,HttpStatus.BAD_REQUEST);

    }
}
