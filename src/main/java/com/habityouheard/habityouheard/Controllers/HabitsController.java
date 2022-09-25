package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.HabitRepository;
import com.habityouheard.habityouheard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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


        @Transactional
        @GetMapping("test") // remove after testing
//        @Scheduled(cron = "0 10 00 * * * ")
        public List<HashMap> defirmRemainingHabitsForYesterday() {
            List<Habit> allHabits = habitRepository.findAllScheduledHabitsForDay();
            HashMap<Integer, List<Integer>> affirmScores = new HashMap<Integer, List<Integer>>();
            HashMap<Integer, List<Integer>> defirmScores = new HashMap<Integer, List<Integer>>();

            for (int i = 0; i < allHabits.size(); i++) {
                Integer currentPoints= allHabits.get(i).getPointValue();
                Integer currentStreak = allHabits.get(i).getStreak();
                int streakBonus = allHabits.get(i).getStreak() >= 7 ? 1 : 0;
                Integer affirmedPoints = currentPoints + 11111 + streakBonus;
                Integer affirmedStreak = currentStreak + 1;
                List<Integer> affirmedData = new ArrayList<>();
                affirmedData.add(affirmedPoints, affirmedStreak);

                affirmScores.put(allHabits.get(i).getId(), affirmedData);

                Integer defirmedPoints = currentPoints - 1;
                Integer defirmedStreak = 0;
                List<Integer> defirmedData = new ArrayList<>();
                affirmedData.add(defirmedPoints, defirmedStreak);

                defirmScores.put(allHabits.get(i).getId(), defirmedData);

            }
            List<HashMap> scoresForToday = new ArrayList<HashMap>();
            scoresForToday.add(affirmScores);
            scoresForToday.add(defirmScores);
            return scoresForToday;

        }
}
