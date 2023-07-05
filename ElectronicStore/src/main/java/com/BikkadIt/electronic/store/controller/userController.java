package com.BikkadIt.electronic.store.controller;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.dtos.ApiResponse;
import com.BikkadIt.electronic.store.dtos.ImageResponse;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.service.FileService;
import com.BikkadIt.electronic.store.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/User")

public class userController
{

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;



    //create
    @PostMapping

    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        log.info("Request starting for service layer to create the user");
        UserDto newUser = userService.createUser(userDto);
        log.info("Request completed for service layer to create the user");
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }


    //update
    @PostMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(
            @Valid
            @PathVariable("userId")String userId,
            @RequestBody UserDto userDto ){
        log.info("Request starting for service layer to get the user by userId {} :",userId);
        UserDto userDto1 = userService.updateUser(userDto, userId);
        log.info("Request completed for service layer to get the user by userId {} :",userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

        //delete
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable String userId){
        log.info("Request starting for service layer to delete the user {} :",userId);
        userService.deletUser(userId);
        ApiResponse message = ApiResponse.builder()
                .message(AppConstants.USER_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Request completed for service layer to delete the user {} :",userId);
        return new ResponseEntity<>(message,HttpStatus.OK);

    }
    //get all
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserDto>>getAllUser(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        log.info("Request starting for service layer to get all user");
        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to get all user");
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }



    //get Single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>getById(@PathVariable String userId){

        log.info("Request starting for service layer to get user by Id {} :",userId);
        UserDto byId = this.userService.getById(userId);
        log.info("Request completed for service layer to get user bt id {} :",userId);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }


    //get By Email
    @GetMapping("/emailId/{emailId}")
    public ResponseEntity<UserDto>getByEmail(@PathVariable String emailId){
        log.info("Request starting for service layer to get the user by emailId {} :",emailId);
        UserDto byEmilId = this.userService.getByEmilId(emailId);
        log.info("Request completed for service layer to get the user by emailId {} :",emailId);
        return new ResponseEntity<>(byEmilId,HttpStatus.OK);
    }


    //search user
    @GetMapping("/keyWord/{keyWord}")
    public ResponseEntity<List<UserDto>>searchUser(@PathVariable String keyWord){
        log.info("Request starting for service layer to get the user by keyword {} :",keyWord);
        List<UserDto> userDto = this.userService.searchUser(keyWord);
        log.info("Request completed for service layer to get the user by keyword {} :",keyWord);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }



    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse>uploadUserImage(
            @PathVariable String userId,
            @RequestParam ("userImage")MultipartFile image) throws IOException {
        log.info("Request starting for service layer to uploadUserImage {} :",userId);
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getById(userId);
        user.setUserImage(imageName);
        UserDto userDto1 = userService.updateUser(user, userId);
        ImageResponse imageResponse=ImageResponse.builder()
                                    .imageName(imageName).success(true).message(AppConstants.IMAGE)
                                    .status(HttpStatus.CREATED).build();
        log.info("Request completed for service layer to uploadUserImage {} :",userId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }
    //serve user image

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getById(userId);
        log.info("Request start User image name : {}",user.getUserImage());
        InputStream resource = fileService.getResource(imageUploadPath, user.getUserImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Request Stop User image name : {}",user.getUserImage());
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
