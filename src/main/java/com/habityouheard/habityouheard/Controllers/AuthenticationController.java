package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Micah Young
 */
@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;
    @PostMapping ("assign/token")
    public ResponseEntity<Object> provideTokenAndUserId(@RequestBody Map<String, String> json) {
        Optional<User> optUser = userRepository.findByUsername(json.get("username"));
        Map<String,String> responseBody = new HashMap<>();

        if(optUser.isPresent()){

            User user = (User) optUser.get();


            if(BCrypt.checkpw(json.get("password"),user.getPassword())){

                String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                String hashCode = BCrypt.hashpw(date + user.getUsername() ,BCrypt.gensalt(10));
                user.setAuthToken(hashCode);
                responseBody.put("token",hashCode);
                responseBody.put("userId",String.valueOf(user.getId()));
                userRepository.save(user);
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }

        } else {
            responseBody.put("errorMessage","User Not Found");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);

        }
        responseBody.put("errorMessage","Invalid Password");

        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
    }


    @PostMapping("get/user")
    public User getUserByToken(@RequestBody Map<String, String> json) {
        System.out.println(json.get("authToken"));
        Optional<User> optUser = userRepository.findByAuthToken(json.get("authToken"));
        if(optUser.isPresent()){
            User user = (User) optUser.get();
            System.out.println(user.toString());
            System.out.println(json.get("authToken"));
            return user;
        }

        return new User();
    }


}
