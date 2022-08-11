package com.habityouheard.habityouheard.Controllers;

import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("create")
    public ResponseEntity<String> createUser( @Valid @RequestBody User createUser ){
        userRepository.save(createUser);
        return new ResponseEntity<String>("Created", HttpStatus.CREATED);

    }
    @PostMapping("delete")
    public ResponseEntity<String> deleteUser(User deleteUser){
        userRepository.delete(deleteUser);
        return new ResponseEntity<String>("Deleted", HttpStatus.ACCEPTED);
    }
    @GetMapping("{id}")
    public User getUserid(int id){
        return userRepository.getReferenceById(id);
    }

}
