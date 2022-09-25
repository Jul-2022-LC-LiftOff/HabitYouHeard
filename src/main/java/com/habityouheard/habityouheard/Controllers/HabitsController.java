package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.HabitMetaRepository;
import com.habityouheard.habityouheard.repositories.HabitRepository;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("api/habits")
public class HabitsController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HabitRepository habitRepository;
    @Autowired
    HabitMetaRepository habitMetaRepository;
    @Autowired
    EntityManager entityManager;

    @GetMapping("")
    public ResponseEntity <List<Habit>> viewAllActiveHabits(@RequestHeader(value="Authorization") String authToken) {

        if (authToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userReference = userRepository.findByAuthToken(authToken);

        if (!userReference.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = (User) userReference.get();
        List<Habit> activeHabits = habitRepository.findAllActiveHabits(user.getId());
        return ResponseEntity.ok().body(activeHabits);

    }

    @GetMapping("all")
    public ResponseEntity<List<Habit>> returnUserHabits(@RequestHeader(value="Authorization") String authToken){

        if (authToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userReference = userRepository.findByAuthToken(authToken);
        if(userReference.isPresent()){
            User user = (User) userReference.get();
            List<Habit> habits = user.getHabits();
            return ResponseEntity.ok().body(habits);
        }
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("inactive")
    public ResponseEntity <List<Habit>> viewAllInactiveHabits(@RequestHeader(value="Authorization") String authToken) {

        if (authToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userReference = userRepository.findByAuthToken(authToken);

        if (!userReference.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = (User) userReference.get();
        List<Habit> activeHabits = habitRepository.findAllInactiveHabits(user.getId());
        return ResponseEntity.ok().body(activeHabits);

    }

//    @Scheduled(cron = "0 27 13 * * * ")


//        @Scheduled(cron = "0 01 00 * * * ")
    @Transactional
    @GetMapping("test") // remove after testing
    public void defirmRemainingHabitsForYesterday() {
        List<Habit> allHabits = habitRepository.findAllScheduledHabitsForYesterday();
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < allHabits.size(); i++) {
            Optional<Date> latestDateReference = habitMetaRepository.findLatestDateByHabitId(allHabits.get(i).getId());
            if (latestDateReference.isEmpty()) {
                HabitMeta newHabitMeta = new HabitMeta(false, allHabits.get(i));
                allHabits.get(i).getHabitMetaList().add(newHabitMeta);
                entityManager.persist(allHabits.get(i));
                entityManager.flush();
                newHabitMeta.setDateOfCompletion(date);
                habitRepository.resetStreakToZero(allHabits.get(i).getId());
            } else {

                Date latestDate = (Date) latestDateReference.get();
                String simpleLatestDate = dateFormat.format(latestDate);
                String simpleDate = dateFormat.format(date);
                System.out.println(simpleLatestDate);
                System.out.println(simpleDate);
                if (!simpleLatestDate.equals(simpleDate)) {
                    HabitMeta newHabitMeta = new HabitMeta(false, allHabits.get(i));
                    allHabits.get(i).getHabitMetaList().add(newHabitMeta);
                    entityManager.persist(allHabits.get(i));
                    entityManager.flush();
                    newHabitMeta.setDateOfCompletion(date);
                    habitRepository.resetStreakToZero(allHabits.get(i).getId());
                }
            }
        }
    }
}
