package com.example.genesys.usermanagementapp.userService;


import com.example.genesys.usermanagementapp.registration.EmailValidator;
import com.example.genesys.usermanagementapp.userModel.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserRegistrationService {

    private UserService userService;
    private EmailValidator emailValidator;

    public UserRegistrationService(UserService userService, EmailValidator emailValidator) {
        this.userService = userService;
        this.emailValidator = emailValidator;
    }

    public String registerUser(UserRegistrationRequest request) {
        request.setDate(new Date());
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return userService.signUpUser(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getDate()
                )
        );
    }
}
