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
public class Student implements Serializable{
    private String studentNum;
    private int userID;
    private String password;

     public Student() {
        }
     
    public Student(String studentNum, int userID, String password) {
        this.studentNum = studentNum;
        this.userID = userID;
        this.password = password;
    }

    public Student(String studentNum, String password) {
        this.studentNum = studentNum;
        this.password = password;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your Login details is as follow: ");
        sb.append("StudentNum :").append(studentNum);
        sb.append("Password :").append(password);
        return sb.toString();
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getSurname() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
