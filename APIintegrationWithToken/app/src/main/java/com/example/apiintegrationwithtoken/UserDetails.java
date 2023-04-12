package com.example.apiintegrationwithtoken;

import java.io.Serializable;

public class UserDetails implements Serializable {

    int id=0;
    String name="";
    String email="";
    String gender="";

    String status="";


    public UserDetails() {
    }

    public UserDetails(int id, String name, String email, String gender, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    @Override
    public String toString() {
        return id + name + email + gender+ status;
    }
}
