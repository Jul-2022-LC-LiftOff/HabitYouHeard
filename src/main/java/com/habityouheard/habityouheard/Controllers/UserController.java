package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

///api/user
@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /// creating a user
    @PostMapping("create")
    public ResponseEntity<String> createUser( @Valid @RequestBody User createUser ){
        String hashedPassword = BCrypt.hashpw(createUser.getPassword(),BCrypt.gensalt(10));
        createUser.setPassword(hashedPassword);
        userRepository.save(createUser);
        return new ResponseEntity<String>("Created", HttpStatus.CREATED);

    }

    ///deleting a user
    @PostMapping("delete")
    public ResponseEntity<String> deleteUser(User deleteUser){
        userRepository.delete(deleteUser);
        return new ResponseEntity<String>("Deleted", HttpStatus.ACCEPTED);
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getUserid(@PathVariable int id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            return ResponseEntity.ok().body(user);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

