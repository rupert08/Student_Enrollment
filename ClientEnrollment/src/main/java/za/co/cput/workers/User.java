/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.cput.workers;

import java.io.Serializable;





/**
 *
 * @author ruper
 */
public class User implements Serializable{
   

 
    
    private int userID;
    private String name;
    private String surname;
    private String role;
    
    
    // Constructor, getters, setters, etc.

    public User () {
    }

    public User(int userID) {
        this.userID = userID;
    }


    public User(String name, String surname, String role) {
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

public User(int userID, String name, String surname) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

   


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("name=").append(name);
        sb.append(", surname=").append(surname);
//        sb.append(", password=").append(password);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}


