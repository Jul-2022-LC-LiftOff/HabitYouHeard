package com.habityouheard.habityouheard.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Habit {

    @NotBlank(message = "Habit name must not be empty.")
    @Size(max = 128, message = "Habit name must be less than 128 characters.")
    private String name;

    @Size(max = 512, message = "Description must be less than 512 characters.")
    private String description;

    @NotBlank(message = "At least one day must be selected.")
    private List<String> selectedDays = new ArrayList<>();

    //Todo: add Group object
    //private Group group;

    private int pointValue;

    @OneToMany
    private List<HabitMeta> habitMetaList = new ArrayList<>();

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue
    private int id;

    public Habit(){}

    public Habit(String name, String description, List<String> selectedDays, int pointValue, List<HabitMeta> habitMetaList) {
        this.name = name;
        this.description = description;
        this.selectedDays = selectedDays;
        this.pointValue = pointValue;
        this.habitMetaList = habitMetaList;
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
}
