package com.habityouheard.habityouheard.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HabitMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean completedHabit;

    private Date dateOfCompletion;

    @JsonIgnore
    @ManyToOne
    private Habit habit;

    public HabitMeta() {}
    
    public HabitMeta(boolean completedHabit, Habit habit) {
        this.completedHabit = completedHabit;
        this.habit = habit;

        Date newDate =  new Date();
        this.dateOfCompletion = newDate;
    }

    public boolean isCompletedHabit() {
        return completedHabit;
    }

    public int getId() {
        return id;
    }

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setCompletedHabit(boolean completedHabit) {
        this.completedHabit = completedHabit;
    }

    public void setDateOfCompletion(Date dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public Habit getHabit() {
        return habit;
    }
    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}
