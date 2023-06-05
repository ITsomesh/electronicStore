package com.BikkadIt.electronic.store.dtos;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=5,max=10,message = "Enter User Name")
    @NotBlank
    private String name;

    @Email(message = "Enter EmailId")
    @NotBlank
    private String emailId;

    @Size(min=5,max =10)
    @NotBlank
    private String password;

    @Size(message = "Enter Gender")
    @NotBlank
    private String gender;

    @NotBlank
    private String about;

    private String userImage;
}
