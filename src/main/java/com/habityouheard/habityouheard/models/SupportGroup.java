package com.habityouheard.habityouheard.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SupportGroup {

    @Id
    @GeneratedValue
    private int id;

    @Size(max = 30, message = "Must be less than 30 characters.")
    @NotBlank (message = "Enter a Group Name.")
    private String name;

    @Size(max = 302, message = "Must be less than 302 characters.")
    private String description;

    @NotBlank(message = "Enter the Url of a image to use as the Group emblem")
    private String emblemURL;

    //TODO:Mapping//
    @OneToMany(mappedBy = "supportGroup")
    private User user;

    @ManyToOne
    private List<String> supportGroupEmail = new ArrayList<String>();

    public SupportGroup(String name, String description, String emblemURL, User user, List<String> supportGroupEmail) {
        this.name = name;
        this.description = description;
        this.emblemURL = emblemURL;
        this.user = user;
        this.supportGroupEmail = supportGroupEmail;
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

    public String getEmblemURL() {
        return emblemURL;
    }

    public void setEmblemURL(String emblemURL) {
        this.emblemURL = emblemURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getSupportGroupEmail() {
        return supportGroupEmail;
    }

    public void setSupportGroupEmail(List<String> supportGroupEmail) {
        this.supportGroupEmail = supportGroupEmail;
    }
}
