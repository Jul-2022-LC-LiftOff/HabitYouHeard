package com.habityouheard.habityouheard.Controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
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
import java.util.List;
import java.util.Optional;

///api/Habit
@CrossOrigin
@RestController
@RequestMapping("api/habit")
public class HabitController {

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
    @PostMapping("add")
    public ResponseEntity<String> processAddHabitForm(@RequestBody @Valid Habit newHabit, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        habitRepository.save(newHabit);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
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
}