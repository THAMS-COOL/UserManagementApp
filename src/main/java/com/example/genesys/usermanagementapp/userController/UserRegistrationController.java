package com.example.genesys.usermanagementapp.userController;

import com.example.genesys.usermanagementapp.userService.UserRegistrationRequest;
import com.example.genesys.usermanagementapp.userService.UserRegistrationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user/registration")
public class UserRegistrationController {

    private UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public String registerUser(@RequestBody UserRegistrationRequest request){
        return userRegistrationService.registerUser(request);
    }
}
