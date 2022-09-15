package com.publicClass;

public class User {

    private int Id;
    private String Name;
    private String Mail;
    private String Password;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public int getId() { return Id; }

    public void setId(int id) { Id = id; }
}
