package com.example.genesys.usermanagementapp.userService;

import com.example.genesys.usermanagementapp.appUserModel.AppUser;
import com.example.genesys.usermanagementapp.respository.AppUserRepository;
import com.example.genesys.usermanagementapp.respository.UserRepository;
import com.example.genesys.usermanagementapp.userModel.User;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
//    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if(userExists){
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);
//      TODO: Send confirmation token



        return "User App works";
    }

    public ResponseEntity<User> createUser(UserRegistrationRequest request){
        try {
            request.setDate(new Date());
            User createUser = userRepository.save(new User(request.getName(),
                    request.getEmail(), request.getPassword(),request.getDate()));
//            System.out.println("Created User: " + request.toString());
            return new ResponseEntity<>(createUser, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<User>> getAllUsers(String name) {
        try {
            List<User> users = new ArrayList<User>();
            if (name == null) {
                userRepository.findAll().forEach(users::add);
            } else {
                userRepository.findByName(name).forEach(users::add);
            }
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> updateUser(Long id, User user) {
        Optional<User> userData = userRepository.findById(id);
        System.out.println("User Data: " + userData);
        if(userData.isPresent()){
            User updateUserDetails = userData.get();
            updateUserDetails.setName(user.getName());
            updateUserDetails.setEmail(user.getEmail());
            updateUserDetails.setDate(new Date());
            return new ResponseEntity<>(userRepository.save(updateUserDetails), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


