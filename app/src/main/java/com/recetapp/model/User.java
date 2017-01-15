package com.recetapp.model;

import java.util.HashMap; //Edicion en rama master
import java.util.List;
import java.util.Map;


public class User {
    private String id;
    private String name;
    private String email;


    public User(String name, String id, String email) {
        this.name =  name;
        this.id = id;
        this.email = email;
    }

    //Default constructor in order to deserialize objects from firebase
    public User() {
    }

    private List<String> recipes;

    public User(String name, String email) {
        this.name =  name;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public List<String> getRecipes() {
        return recipes;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("name", name);
        result.put("email", email);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
