package com.bikkadIt.ElectronicStore.dtos;

import com.bikkadIt.ElectronicStore.Validator.ImageNameValid;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UserDto updatedDto;
    private String userId;
    //@Size(min = 3,max = 15,message = "Invalid Name !!")
    private String name;
    // @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$" ,message = "Invalid user email id")
//@NotBlank(message =  "email is required")
    private String email;
    //@NotBlank(message = "Invalid is required")
    private String password;
    // @Size(min = 4,max = 6,message = "Invalid gender")
    private String gender;
    // @NotBlank(message = "write something about yourself")
    private String about;

    // @ImageNameValid
    private String ImageName;

}
