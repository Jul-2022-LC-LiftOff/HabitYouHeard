package com.habityouheard.habityouheard.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.mapping.Map;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private int lastPointValue;

    @OneToMany(mappedBy = "habit", targetEntity=HabitMeta.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HabitMeta> habitMetaList = new ArrayList<>();

    private int streak;

    private int lastStreak;

    private Boolean isActive;

    private String startDate;

    @JsonIgnore
    @ManyToOne
    private User user;

    public Habit(){}

    public Habit(String name, String description, List<String> selectedDays, int pointValue, int streak, User user, String startDate, int lastStreak, int lastPointValue) {
        this.name = name;
        this.description = description;
        this.selectedDays = selectedDays;
        this.pointValue = 0;
        this.streak = streak;
        this.user = user;
        this.isActive = true;
        this.startDate = startDate;
        this.lastPointValue = lastPointValue;
        this.lastStreak = lastStreak;
    }

    public int getId() {
        return id;
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

    public int getLastPointValue() {
        return lastPointValue;
    }

    public void setLastPointValue(int lastPointValue) {
        this.lastPointValue = lastPointValue;
    }

    public List<HabitMeta> getHabitMetaList() {
        return habitMetaList;
    }

    public int getStreak() {
        return streak;
    }

    public void updateStreak(boolean completedHabit) {
        streak = completedHabit ? lastStreak + 1 : 0;
    }

    public int getlastStreak() { return lastStreak; }

    public void setLastStreak(int lastStreak) {
        this.lastStreak = lastStreak;
    }

    //create new habitMeta method (upend habitMeta list up top)

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStartDate(){ return startDate; }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

//TODO 1: add updatePointsMethods()
    //TODO 2: add job(s)

}