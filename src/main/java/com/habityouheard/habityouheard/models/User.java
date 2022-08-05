package com.habityouheard.habityouheard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Entity
public class User {

    @Email (message = "Not a valid email.")
    private String email;

    @NotBlank (message = "Username must not be empty.")
    private String username;

    @NotBlank (message = "Password must not be empty.")
    private String password;

    @OneToMany
    private List<Habit> habits = new ArrayList<>();

    private int points;

    @Id
    @GeneratedValue
    private int id;

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
