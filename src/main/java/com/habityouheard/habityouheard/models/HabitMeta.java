package com.habityouheard.habityouheard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class HabitMeta {

    @GeneratedValue
    @Id
    private int id;

    private boolean completedHabit;

    @ManyToOne
    private Habit habit;

    private Date dateOfCompletion;

    public HabitMeta() {

    }
    public HabitMeta(boolean completedHabit, Habit habit, Date dateOfCompletion) {
        this.completedHabit = completedHabit;
        this.habit = habit;
        this.dateOfCompletion = dateOfCompletion;
    }

    public boolean isCompletedHabit() {
        return completedHabit;
    }

    public int getId() {
        return id;
    }

    public Habit getHabit() {
        return habit;
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
}
