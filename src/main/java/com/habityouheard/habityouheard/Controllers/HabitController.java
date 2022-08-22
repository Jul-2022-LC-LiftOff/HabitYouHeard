package com.habityouheard.habityouheard.Controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.HabitMetaRepository;
import com.habityouheard.habityouheard.repositories.HabitRepository;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

///api/Habit
@CrossOrigin
@RestController
@RequestMapping("api/habit")
public class HabitController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HabitRepository habitRepository;
    @Autowired
    HabitMetaRepository habitMetaRepository;

    // get a habit
    @GetMapping("{id}")
    public ResponseEntity<Habit> viewHabit(@PathVariable int id) {
        Optional optHabit = habitRepository.findById(id);
        if (optHabit.isPresent()) {
            Habit habit = (Habit) optHabit.get();
            return ResponseEntity.ok().body(habit);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // add a habit
    @PostMapping("create")
    public ResponseEntity<String> createHabit(@RequestBody @Valid Habit newHabit, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        habitRepository.save(newHabit);

        Optional<User> userReference = userRepository.findById(12);


        if (userReference.isPresent()) {
            User user = (User) userReference.get();
            List habitList = user.getHabits();
            habitList.add(newHabit);
            user.setHabits(habitList);
//            userReference.save()
        }
        return new ResponseEntity<>("created", HttpStatus.CREATED);
        // TODO: will need to add some way to fill the pivot table
    }

    // delete a habit
    @DeleteMapping("{id}/delete")
    public ResponseEntity<Long> deleteHabitById(@PathVariable(value= "id") int id) {

        if (!habitRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        habitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // affirm
    @PutMapping("{id}/affirm")
    public ResponseEntity<HabitMeta> affirmHabitToday(@PathVariable(value= "id") int id, @RequestBody @Valid HabitMeta newHabitMeta) {
        Optional<HabitMeta> latestHabitMetaReference = habitMetaRepository.findTodaysByHabitId(id);
        Optional optHabitMeta = habitRepository.findById(id);

        if (latestHabitMetaReference.isPresent()) {
            HabitMeta habitMeta = (HabitMeta) latestHabitMetaReference.get();
            return ResponseEntity.ok().body(habitMeta);
        } else {
            habitMetaRepository.save(newHabitMeta);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

}