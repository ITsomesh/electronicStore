package com.BikkadIt.electronic.store.controller;


import com.BikkadIt.electronic.store.dtos.ApiResponse;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/User")
public class userController
{
    private Logger logger = LoggerFactory.getLogger(userController.class);

    @Autowired
    private UserService userService;

    /**
     * @auther Er~some
     * @apiNote This api is used to create user
     * @param userDto
     * @return
     */

    //create
    @PostMapping

    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Request starting for service layer to create the user");
        UserDto newUser = userService.createUser(userDto);
        logger.info("Request completed for service layer to create the user");
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }

    /**
     * @auther Er~Some
     * @apiNote This api is used to get the user by userId
     * @param userId
     * @param userDto
     * @return
     */
    //update
    @PostMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(
            @Valid
            @PathVariable("userId")String userId,
            @RequestBody UserDto userDto ){
        logger.info("Request starting for service layer to get the user by userId",userId);
        UserDto userDto1 = userService.updateUser(userDto, userId);
        logger.info("Request completed for service layer to get the user by userId",userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    /**
     * @auther Er~some
     * @apiNote This api is user to delete the user by userId
     * @param userId
     * @return
     */
    //delete
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable String userId){
        logger.info("Request starting for service layer to delete the user");
        userService.deletUser(userId);
        ApiResponse message = ApiResponse.builder()
                .message("User Deleted Successfully")
                .success(true)
                //.status(HttpStatus.OK)
                .build();
        logger.info("Request completed for service layer to delete the user ");
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    /**
     * @auther Er~some
     * @return
     */
    //get all
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>>getAllUser(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false)int pageSize
    ){

        logger.info("Request starting for service layer to get all user");
        List<UserDto> allUser = userService.getAllUser(pageNumber,pageSize);
        logger.info("Request completed for service layer to get all user");
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }

    /**
     * @auther Er~some
     * @param userId
     * @return
     */

    //get Single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>getById(@PathVariable String userId){

        logger.info("Request starting for service layer to get user by Id");
        UserDto byId = this.userService.getById(userId);
        logger.info("Request completed for service layer to get user bt id");
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }

    /**
     * @auther Er~some
     * @param emailId
     * @return
     */
    //get By Email
    @GetMapping("/emailId/{emailId}")
    public ResponseEntity<UserDto>getByEmail(@PathVariable String emailId){
        logger.info("Request starting for service layer to get the user by emailId");
        UserDto byEmilId = this.userService.getByEmilId(emailId);
        logger.info("Request completed for service layer to get the user by emailId");
        return new ResponseEntity<>(byEmilId,HttpStatus.OK);
    }

    /**
     * @auther Er~some
     * @param keyWord
     * @return
     */
    //search user
    @GetMapping("/keyWord/{keyWord}")
    public ResponseEntity<List<UserDto>>searchUser(@PathVariable String keyWord){
        logger.info("Request starting for service layer to get the user by keyword");
        List<UserDto> userDto = this.userService.searchUser(keyWord);
        logger.info("Request completed for service layer to get the user by keyword");
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}
