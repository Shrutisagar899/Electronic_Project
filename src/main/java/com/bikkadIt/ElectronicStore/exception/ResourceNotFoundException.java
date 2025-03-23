package com.bikkadIt.ElectronicStore.exception;

import lombok.Builder;
import org.springframework.validation.Errors;

@Builder
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("resourse not found");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

    public Errors getBindingResult() {
        return getBindingResult();
    }
}
