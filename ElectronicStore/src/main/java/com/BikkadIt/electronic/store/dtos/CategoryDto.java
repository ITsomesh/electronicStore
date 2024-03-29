package com.BikkadIt.electronic.store.dtos;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Service
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min=4,max=10)
    private String tittle;
    @NotBlank
    private String description;
    private String coverImage;
}
