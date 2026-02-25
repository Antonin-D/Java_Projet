package com.mycompany.app.model;

import java.time.LocalDate;

public class Person {
    private Integer id;
    private String lastname;
    private String firstname;
    private String nickname;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private LocalDate birthDate;

    public Person(Integer id, String lastname, String firstname, String nickname, String phoneNumber, String address, String emailAddress, LocalDate birthDate) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

    public Person(String lastname, String firstname, String nickname, String phoneNumber, String address, String emailAddress, LocalDate birthDate) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

    // --- TOUS LES GETTERS ET SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLastname() { return lastname; }
    public String getFirstname() { return firstname; }
    public String getNickname() { return nickname; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public String getEmailAddress() { return emailAddress; }
    public LocalDate getBirthDate() { return birthDate; }
}