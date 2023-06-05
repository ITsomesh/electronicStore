package com.BikkadIt.electronic.store.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException
{

    String resourceName;
    String fieldName;
    String fieldValue;


    public ResourceNotFoundException(String resourceName,String fieldName,String fieldValue){

        super(String.format("%s not found with %s:%s",resourceName,fieldName,fieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

}
