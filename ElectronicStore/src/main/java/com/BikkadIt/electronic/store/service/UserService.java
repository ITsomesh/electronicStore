package com.BikkadIt.electronic.store.service;

import com.BikkadIt.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //Create User
    UserDto createUser(UserDto userDto);

    //Update User
    UserDto updateUser(UserDto userDto,String  userId);

    //Get All User
    List<UserDto>getAllUser();

    //Delete User
    void deletUser(String userId);

    //Get User By Id
    UserDto getById(String userId);

    //Get single user by email
    UserDto getByEmilId(String emailId);

    //Search user
    List<UserDto> searchUser (String Keyword);
}
