package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/habits")
public class HabitsController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("{userId}")
    public List<Habit> returnUserHabits(@PathVariable int userId){
        Optional<User> optUser = userRepository.findById(userId);
        if(optUser.isPresent()) { //Thought: what happens when the user doesn't have any habits? Does the list just remain empty?
            User user = (User) optUser.get();
            List<Habit> habits = user.getHabits();
            return habits;
        } else {
            //do we want to have a variable that contains whether the habit list has any habits? Perhaps it replaces the list with
                                                                             //some text like "You do not have any habits, would you like to make one?", and then has a hyperlink to the create page.
            return new ArrayList<Habit>();
        }
    }
}
