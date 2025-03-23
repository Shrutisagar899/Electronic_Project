package com.bikkadIt.ElectronicStore.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage {
    private String message;
    private boolean success;
    private HttpStatus status;

}
