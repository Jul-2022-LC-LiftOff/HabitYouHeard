package com.habityouheard.habityouheard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class HabitMeta {

    @Id
    @GeneratedValue
    private int id;

    private boolean completedHabit;

    private Date dateOfCompletion;

    public HabitMeta() {}
    
    public HabitMeta(boolean completedHabit, Date dateOfCompletion) {
        this.completedHabit = completedHabit;
        this.dateOfCompletion = dateOfCompletion;
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
}
