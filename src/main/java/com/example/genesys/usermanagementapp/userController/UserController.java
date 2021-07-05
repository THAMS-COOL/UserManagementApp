package com.example.genesys.usermanagementapp.userController;


import com.example.genesys.usermanagementapp.userModel.User;
import com.example.genesys.usermanagementapp.userService.UserRegistrationRequest;
import com.example.genesys.usermanagementapp.userService.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {


    UserService userService;

    @GetMapping("/")
    public String home(){
        return "welcome";
    }

    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationRequest request){
          return userService.createUser(request);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name){
     return userService.getAllUsers(name);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return ("Deleted User Id: " + id + " Successfully");
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        return userService.deleteAllUsers();
    }
}
