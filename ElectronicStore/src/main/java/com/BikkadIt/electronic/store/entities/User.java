package com.BikkadIt.electronic.store.entities;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    
    @Column(name="user_name")
    private String name;

    @Column(name="user_emailId",unique = true)
    private String emailId;

    @Column(name="user_password")
    private String password;

    @Column(name="user_gender")
    private String gender;

    @Column(name="user_about",length = 1000)
    private String about;

    @Column(name="user_Image")
    private String userImage;

}
