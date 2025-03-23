package com.bikkadIt.ElectronicStore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.BindParam;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    //public Object updateUser;
    @Id
    //@GeneratedValue(strategy = )
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email",unique = true)
    private String email;
    @Column(name = "user_password",length = 10)
    private String password;
    private String gender;
    @Column(length = 1000)
    private String about;
    @Column(name = "user_image_name")
    private String ImageName;
}
