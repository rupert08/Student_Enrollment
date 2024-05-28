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
public class Admin implements Serializable{

    private String adminNum;
    private int userID;
    private String password;

    public Admin(){
    }
    
    
    public Admin(String adminNum, String password) {
        this.adminNum = adminNum;
        this.password = password;
    }

    
    public Admin(String adminNum, int userID) {
        this.adminNum = adminNum;
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(String adminNum) {
        this.adminNum = adminNum;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Admin{");
        sb.append("adminNum=").append(adminNum);
        sb.append(", userID=").append(userID);
        sb.append('}');
        return sb.toString();
    }
    
    


    
}
