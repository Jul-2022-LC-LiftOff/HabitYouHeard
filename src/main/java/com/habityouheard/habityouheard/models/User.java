package com.habityouheard.habityouheard.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Email (message = "Not a valid email.")
    private String email;

    @Size(max = 30, message = "Username must be less than 30 characters.")
    @NotBlank (message = "Username must not be empty.")
    private String username;

    @Size(max = 128, message = "Password must be less than 128 characters, how do you even have that long of a password?")
    @NotBlank (message = "Password must not be empty.")
    private String password;

    @OneToMany(targetEntity=Habit.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Habit> habits = new ArrayList<>();

    private int points;

    public User() {}

    public User(String email, String username, String password, List<Habit> habits, int points) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.habits = habits;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
