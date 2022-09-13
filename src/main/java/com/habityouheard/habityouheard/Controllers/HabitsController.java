package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.HabitRepository;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    HabitRepository habitRepository;

    @GetMapping("{id}")
    public ResponseEntity<List<Habit>> returnUserHabits(@PathVariable int id){
        Optional <User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            User user = (User) optUser.get();
            List<Habit> habits = user.getHabits();
            return ResponseEntity.ok().body(habits);
        }
        return ResponseEntity.ok().body(new ArrayList<>());
    }
    @GetMapping("{id}/all")
    public ResponseEntity<Habit> viewAllActiveHabits(@PathVariable int id) {
        Optional optHabit = habitRepository.findById(id);
        if (optHabit.isPresent()) {
            Habit habit = (Habit) optHabit.get();
            habitRepository.findAllActiveHabits(id);
            return ResponseEntity.ok().body(habit);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
