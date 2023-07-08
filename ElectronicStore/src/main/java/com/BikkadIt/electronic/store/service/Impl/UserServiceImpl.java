package com.BikkadIt.electronic.store.service.Impl;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.controller.userController;
import com.BikkadIt.electronic.store.dtos.ApiResponse;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.BikkadIt.electronic.store.helper.Helper;
import com.BikkadIt.electronic.store.repository.UserRepo;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.entities.User;
import com.BikkadIt.electronic.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {

        log.info("Request starting to save the user");
        //generate unique id
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user=dtoToEntity(userDto);
        User saveUser = userRepo.save(user);
        UserDto newDto=entityToDto(saveUser);
        log.info("Request completed to save the user");
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Request started to Update the user");
        User user = userRepo
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setUserImage(userDto.getUserImage());
        User updatedUser = userRepo.save(user);
        UserDto updateDto = entityToDto(updatedUser);
        log.info("Request completed to updated user");
        return updateDto;
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        log.info("Request started to get all user");
        Sort sort = (sortDir.equalsIgnoreCase("desc"))
                    ? (Sort.by(sortBy).descending())
                    : (Sort.by(sortBy).ascending());

        Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);

        Page<User> page = userRepo.findAll(pageable);
//        List<User> content = page.getContent();
//        List<UserDto> collect = content.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
//        log.info("Request completed to get all user");
//        PageableResponse<UserDto>response=new PageableResponse<>();
//        response.setContent(collect);
//        response.setPageNumber(page.getNumber());
//        response.setPageSize(page.getSize());
//        response.setTotalElements(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());
//        return response;
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;

    }

    @Override
    public void deletUser(String userId) {
        log.info("Request Started to delete user");
        User user = userRepo
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        //delete user profile image

        String fullPath = imagePath + user.getUserImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            log.info("User Image not found !!");
        }catch (IOException e){
            throw new RuntimeException();
        }
        log.info("Request completed to delete user");
        userRepo.delete(user);

    }

    @Override
    public UserDto getById(String userId) {
        log.info("Request Started to get user by Id");
        User userNotFound = userRepo
                .findById(userId)
                .orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUND));
        log.info("Request completed to get user by Id");
        return entityToDto(userNotFound);
    }

    @Override
    public UserDto getByEmilId(String emailId) {
        log.info("Request started to get user by emailId");
        User user = userRepo.findByEmailId(emailId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        log.info("Request completed to get user by emailId");
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String Keyword) {
        log.info("Request started to search user by keyword");
        List<User> byNameContaining = userRepo.findByNameContaining(Keyword);
        List<UserDto> collect = byNameContaining.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("Request completed to search user by keyword");
        return collect;
    }

    private UserDto entityToDto(User saveUser) {

//        UserDto userDto=new UserDto().builder()
//                .userId(saveUser.getUserId())
//                .name(saveUser.getName())
//                .emailId(saveUser.getEmailId())
//                .password(saveUser.getPassword())
//                .gender(saveUser.getGender())
//                .about(saveUser.getAbout())
//                .userImage(saveUser.getUserImage())
//                .build();
        return modelMapper.map(saveUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {

//        User user = new User().builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .emailId(userDto.getEmailId())
//                .password(userDto.getPassword())
//                .gender(userDto.getGender())
//                .about(userDto.getAbout())
//                .userImage(userDto.getUserImage())
//                .build();
        return modelMapper.map(userDto,User.class);
    }
}
