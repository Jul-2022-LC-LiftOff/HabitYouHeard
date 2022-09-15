package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.models.Habit;
import com.habityouheard.habityouheard.models.HabitMeta;
import com.habityouheard.habityouheard.models.User;
import com.habityouheard.habityouheard.repositories.HabitMetaRepository;
import com.habityouheard.habityouheard.repositories.HabitRepository;
import com.habityouheard.habityouheard.repositories.UserRepository;
import com.habityouheard.habityouheard.services.SendGridEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Micah Young
 */

@RestController
@CrossOrigin
@RequestMapping("api/email")
public class EmailController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    HabitMetaRepository habitMetaRepository;

    @GetMapping("/send")
    public String sendEmail(@RequestBody Map<String, String> json) {
        System.out.println(json.get("email"));
        System.out.println(json.get("message"));

        SendGridEmail.sendEmail(json.get("message"), json.get("email"));
        return "Email sent";

    }

    @Scheduled(cron = "0 55 11 * * * ")
    @GetMapping("send/affirmOrDeny")
    public String sendAffirmOrDenyEmail(){
        //Custom query in UserRepository that gets all users with at least one habit that has a selectedDay matching today's date.
        //Get each of those habits in a collection
        //email both the affirmation and deaffirmation endpoints with the specific habit id


        Map<User, List<Habit>> emailList = new HashMap<>();

        for(User user : userRepository.findAll()){

            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
            for(Habit habit : user.getHabits()){
                if(habit.getSelectedDays().contains(day)){
                    Optional<HabitMeta> latestHabitMetaReference = habitMetaRepository.findTodaysByHabitId(habit.getId());

                    if(latestHabitMetaReference.isPresent()){
                        HabitMeta habitMeta = (HabitMeta) latestHabitMetaReference.get();
                        if(!habitMeta.isCompletedHabit()){
                            continue;
                        }
                    } else {
                        if(emailList.containsKey(user)){
                            emailList.get(user).add(habit);
                        } else {
                            emailList.put(user, new ArrayList<>());
                            emailList.get(user).add(habit);
                        }
                    }
                }
            }
        }

        for(Map.Entry<User, List<Habit>> entry : emailList.entrySet()){
            String text = "Greetings, " + entry.getKey().getUsername() + "\n" + " You have " + entry.getValue().size() + " habits to affirm.\n" + " \"<a clicktracking=\"off\" href=\"http://localhost:3000\"> Click Me To Go Affirm! </a>" + "\n";



            String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head><body>"+  text + "</body></html>";

            SendGridEmail.sendEmail(message,entry.getKey().getEmail());

        }


        return "No";
    }


}