package com.BikkadIt.electronic.store.dtos;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=5,max=20,message = "Enter Valid UserName  must be 5 to 20 Character !!")
    @NotBlank
    private String name;

    @Email(message = "Enter Valid EmailId !!")
    @Pattern(regexp = "[a-z0-9][-a-z0-9.-]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message ="Invalid User" )
    @NotBlank
    private String emailId;

    @Size(min=5,max =10)
    @NotBlank
    private String password;

    @Size(min=4,max=6,message = "Enter Gender !!")
    @NotBlank
    private String gender;

    @Size(min=5,max=20,message = "Enter About !!")
    @NotBlank
    private String about;

    private String userImage;
}
