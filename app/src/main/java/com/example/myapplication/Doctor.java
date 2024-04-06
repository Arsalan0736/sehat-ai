package com.example.myapplication;


public class Doctor {

    private String Name;
    private String qualification;
    private String address;
    private String hpr_id;
    private String gender;
    private String mobileNumber;
    private String username;
    private String password;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Doctor(String name, String qualification, String address, String hpr_id, String gender, String mobileNumber, String username, String password, String email) {
        Name = name;
        this.qualification = qualification;
        this.address = address;
        this.hpr_id = hpr_id;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHpr_id() {
        return hpr_id;
    }

    public void setHpr_id(String hpr_id) {
        this.hpr_id = hpr_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}