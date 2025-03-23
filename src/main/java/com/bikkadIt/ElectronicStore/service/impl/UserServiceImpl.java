package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.UserDto;
import com.bikkadIt.ElectronicStore.entity.User;
import com.bikkadIt.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.ElectronicStore.repository.UserRepository;
import com.bikkadIt.ElectronicStore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository useRepository;
    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

//    public UserServiceImpl(UseRepository useRepository, ModelMapper mapper) {
//        this.useRepository = useRepository;
//        this.mapper = mapper;
//    }


    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = useRepository.save(user);
        // entity -->dto
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = useRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("this is user Repository"));
        //this.useRepository.delete(user);

        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            logger.info("User_Image not found Foldre");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<UserDto> getAllUser() {
        return null;
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = useRepository.findAll(pageable);
        List<User> users = page.getContent();
        List<UserDto> dtoList = users.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(dtoList);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalEelements(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastpage(page.isLast());
        return pageableResponse;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = useRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //mapper.entityToDto
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String Email) {
        User user = useRepository.findByEmail(Email)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with given"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String Keyword) {
        List<User> users = useRepository.findByNameContaining(Keyword);

        return users.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = useRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        user.setName(userDto.getName());
        //Email update
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User user1 = useRepository.save(user);
        UserDto updatedDto = entityToDto(user1);
        return userDto;
    }

    private UserDto entityToDto(Object savedUser) {

        return mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        //se manually method dtoToEntity
        return mapper.map(userDto, User.class);
    }
}