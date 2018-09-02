package com.example.manikesh.jsonparsing;

/**
 * Created by Manikesh on 4/27/2018.
 */


public class Customers {
    String name;
    String email;
    String phone;
    // Alt + Insert to see getter/setter and constructor option
    // constructor
    public  Customers(){

    }

    public Customers(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

