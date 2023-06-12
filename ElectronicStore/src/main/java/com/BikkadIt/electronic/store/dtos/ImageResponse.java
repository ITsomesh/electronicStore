package com.BikkadIt.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ImageResponse {

    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;
}
