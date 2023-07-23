package com.BikkadIt.electronic.store.electronic.store.service.Service;


import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.entities.User;
import com.BikkadIt.electronic.store.repository.UserRepo;
import com.BikkadIt.electronic.store.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepo userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    User user;

    @BeforeEach
    public void init(){
       user= User.builder()
                .name("Somesh")
                .emailId("so@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop.png")
                .build();
    }

    @Test
    public void createUserTest(){
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));

        System.out.println(user1.getName());
        System.out.println(user1.getEmailId());
        System.out.println(user1.getAbout());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Somesh",user1.getName());
        Assertions.assertEquals("so@gmail.com",user1.getEmailId());
        Assertions.assertEquals("Testing class",user1.getAbout());
    }

    @Test
    public void updateUserTest(){
        //user
        String userId="";
        UserDto userDto=UserDto.builder()
                .name("Rakesh")
                .emailId("rk@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop1.png")
                .build();
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto userDto1 = userService.updateUser(userDto, userId);
        UserDto map = modelMapper.map(user, UserDto.class);
        System.out.println(userDto1.getName());
        System.out.println(userDto1.getEmailId());
        System.out.println(userDto1.getUserImage());
        //Assertions.assertNotNull(userDto1);

        Assertions.assertEquals(userDto.getName(),userDto1.getName(),"Not Matched");
    }

    @Test
    public void deleteUserTest(){
        String userid="asdf";
        Mockito.when(userRepo.findById("asdf")).thenReturn(Optional.of(user));
        userService.deletUser(userid);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user);
        System.out.println("User Delete Test Passed");



    }
    @Test
    public void getAllUserTest(){
        User user1= User.builder()
                .name("Prakash")
                .emailId("so@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop.png")
                .build();

        User user2= User.builder()
                .name("Rahul")
                .emailId("so@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop.png")
                .build();


        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepo.findAll((Pageable)Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1,2,"name","ascending");
        Assertions.assertEquals(3,allUser.getContent().size());

    }
    @Test
    public void getUserByIdTest(){
        String userid="asdf";
        Mockito.when(userRepo.findById(userid)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getById(userid);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name Not Matched");



    }
    @Test
    public void getUserByEmailId(){
        String emailid="so@gmail.com";
        Mockito.when(userRepo.findByEmailId(emailid)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getByEmilId(emailid);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmailId(),userDto.getEmailId(),"Id Not Matched");

    }
    @Test
    public void searchUserTest(){
       User user1= User.builder()
                .name("Rakesh")
                .emailId("so@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop.png")
                .build();
      User  user2= User.builder()
                .name("Babalu")
                .emailId("so@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop.png")
                .build();
        User user3= User.builder()
                .name("Akash")
                .emailId("so@gmail.com")
                .gender("Male")
                .about("Testing class")
                .userImage("sop.png")
                .build();
        String keyword="Rakesh";
        Mockito.when(userRepo.findByNameContaining(keyword)).thenReturn(Arrays.asList(user1,user2,user3));
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertNotNull(userDtos);
        Assertions.assertEquals(3,userDtos.size());




    }



}
