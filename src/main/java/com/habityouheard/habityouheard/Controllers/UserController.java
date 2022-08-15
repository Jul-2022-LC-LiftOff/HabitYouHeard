package com.habityouheard.habityouheard.Controllers;

import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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
        userRepository.save(createUser);
        return new ResponseEntity<String>("Created", HttpStatus.CREATED);

    }

    ///deleting a user
    @PostMapping("delete")
    public ResponseEntity<String> deleteUser(User deleteUser){
        userRepository.delete(deleteUser);
        return new ResponseEntity<String>("Deleted", HttpStatus.ACCEPTED);
    }
}
