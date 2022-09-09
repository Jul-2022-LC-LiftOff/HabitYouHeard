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
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ResponseEntity<String> createHabit(@RequestBody @Valid Habit newHabit, @RequestHeader(value="Authorization") String authToken, Errors errors) {
        System.out.println(authToken);
        if (authToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userReference = userRepository.findByAuthToken(authToken);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!userReference.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = (User) userReference.get();
        newHabit.setUser(user);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        newHabit.setStartDate(dateFormat.format(date));
        user.getHabits().add(newHabit);
        entityManager.persist(user);
        entityManager.flush();

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