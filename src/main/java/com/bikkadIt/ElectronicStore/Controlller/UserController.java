package com.bikkadIt.ElectronicStore.Controlller;

import com.bikkadIt.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadIt.ElectronicStore.dtos.ImageResponse;
import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.UserDto;
import com.bikkadIt.ElectronicStore.service.FileService;
import com.bikkadIt.ElectronicStore.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser
    (@PathVariable("userId") String userId,
     @Valid @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("user is deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,

            @RequestParam(value = "sortedBy", defaultValue = "name", required = false) String sortedBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir
    ) {
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortDir, sortedBy), HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        UserDto userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        UserDto user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keywords") String keywords) {
        List<UserDto> dtos = userService.searchUser(keywords);
        return new ResponseEntity<>(dtos, HttpStatus.CREATED);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage
    (@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);

        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //serve user image
    @GetMapping("/image/{userId}")
    public void setUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("user image name : {} ", user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
    //upload post image
//    public String uploadPostImage(@RequestParam MultipartFile image, @PathVariable String userId){
//        UserDto userById = this.userService.getUserById(userId);
//
//
//    }


}
