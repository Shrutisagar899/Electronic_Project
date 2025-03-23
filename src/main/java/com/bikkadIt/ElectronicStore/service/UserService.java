package com.bikkadIt.ElectronicStore.service;

import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService {
    //create
    UserDto createUser(UserDto user);


    //update

   // UserDto updateuser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto>getAllUser();

    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUserById(String UserId);

    UserDto getUserByEmail(String UserEmail);

    List<UserDto> searchUser(String Keyword);

    UserDto updateUser(UserDto userDto, String userId);

}
