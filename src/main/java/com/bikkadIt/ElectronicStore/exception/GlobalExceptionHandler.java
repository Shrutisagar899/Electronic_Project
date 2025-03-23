package com.bikkadIt.ElectronicStore.exception;

import com.bikkadIt.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourseNotFoundExceptionHandler(ResourceNotFoundException ex) {
        logger.info("Exception Handler invoked");
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();

        allErrors.forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex) {
        logger.info("Exception Handler invoked");
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}