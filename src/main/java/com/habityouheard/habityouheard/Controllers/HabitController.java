package com.habityouheard.habityouheard.controllers;

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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
    EntityManager entityManager;
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
    @Transactional
    @PostMapping("create")
    public ResponseEntity<String> createHabit(@RequestBody @Valid Habit newHabit, Errors errors) {
        Optional<User> userReference = userRepository.findById(1);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!userReference.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println(userReference);
        User user = (User) userReference.get();
        newHabit.setUser(user);
        System.out.println(user);
        user.getHabits().add(newHabit);
        entityManager.persist(user);
        entityManager.flush();

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
    @Transactional
    @PostMapping("{id}/affirm")
    public ResponseEntity<HabitMeta> affirmHabitToday(@PathVariable(value= "id") int id) {
        Optional<HabitMeta> latestHabitMetaReference = habitMetaRepository.findTodaysByHabitId(id);


        if (latestHabitMetaReference.isPresent()) {
            HabitMeta habitMeta = (HabitMeta) latestHabitMetaReference.get();
            habitMeta.setCompletedHabit(true);
            entityManager.persist(habitMeta);
            entityManager.flush();
            return ResponseEntity.ok().body(habitMeta);
        } else {
            Optional<Habit> habitReference = habitRepository.findById(id);
            Habit habit = (Habit) habitReference.get();
            HabitMeta newHabitMeta = new HabitMeta(true, habit);
            habit.getHabitMetaList().add(newHabitMeta);
            entityManager.persist(habit);
            entityManager.flush();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    @Transactional
    @PostMapping("{id}/defirm")
    public ResponseEntity<HabitMeta> defirmHabitToday(@PathVariable(value= "id") int id) {
        Optional<HabitMeta> latestHabitMetaReference = habitMetaRepository.findTodaysByHabitId(id);


        if (latestHabitMetaReference.isPresent()) {
            HabitMeta habitMeta = (HabitMeta) latestHabitMetaReference.get();
            habitMeta.setCompletedHabit(false);
            entityManager.persist(habitMeta);
            entityManager.flush();
            return ResponseEntity.ok().body(habitMeta);
        } else {
            Optional<Habit> habitReference = habitRepository.findById(id);
            Habit habit = (Habit) habitReference.get();
            HabitMeta newHabitMeta = new HabitMeta(false, habit);
            habit.getHabitMetaList().add(newHabitMeta);
            entityManager.persist(habit);
            entityManager.flush();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}