package com.habityouheard.habityouheard.models;

import org.hibernate.mapping.Map;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
public class Habit {
    
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Habit name must not be empty.")
    @Size(max = 128, message = "Habit name must be less than 128 characters.")
    private String name;

    @Size(max = 512, message = "Description must be less than 512 characters.")
    private String description;

    //Element Collection used here to solve : "Caused by: org.hibernate.MappingException: Could not determine type for: java.util.List, at table: habit, for columns: [org.hibernate.mapping.Column(selected_days)]"
    @ElementCollection
    @NotEmpty(message = "At least one day must be selected.")
    private List<String> selectedDays = new ArrayList<>();

    //Todo: add Group object
    //private Group group;

    private int pointValue;

    @OneToMany
    private List<HabitMeta> habitMetaList = new ArrayList<>();

    private int streak;

    public Habit(){}

    public Habit(String name, String description, List<String> selectedDays, int pointValue, int streak) {
        this.name = name;
        this.description = description;
        this.selectedDays = selectedDays;
        this.pointValue = pointValue;
        this.streak = streak;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<String> selectedDays) {
        this.selectedDays = selectedDays;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public List<HabitMeta> getHabitMetaList() {
        return habitMetaList;
    }

    public int getStreak() {
        return streak;
    }

    public void updateStreak(boolean completedHabit) {
        streak = (completedHabit) ? streak++ : 0;
    }

    //create new habitMeta method (upend habitMeta list up top)
    public HashMap<String, Object> createHabitMeta(Boolean affirmedHabit, int habitId) {
        HashMap<String, Object> habitMetaHash = new HashMap<String,Object>();
        Date date= new Date();
        habitMetaHash.put("completed_habit", affirmedHabit);
        habitMetaHash.put("date_of_completion", date);
        habitMetaHash.put("habit_id", habitId);
        return habitMetaHash;
    }


    //TODO 1: add updatePointsMethods()
    //TODO 2: add job(s)

}