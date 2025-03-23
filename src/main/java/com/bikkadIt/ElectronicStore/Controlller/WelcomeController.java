package com.bikkadIt.ElectronicStore.Controlller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class WelcomeController {
    @GetMapping
    public String test() {
        return "welcome to bikkadit";
    }
}
