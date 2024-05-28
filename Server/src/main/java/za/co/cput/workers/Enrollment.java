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
public class Enrollment implements Serializable{
    private int enrollID;
    private String studentNum;
    private String courseCode;

    public Enrollment() {
    }

    public Enrollment(int enrollID, String studentNum, String courseID) {
        this.enrollID = enrollID;
        this.studentNum = studentNum;
        this.courseCode = courseID;
    }

    public Enrollment(String studentNum, String courseID) {
        this.studentNum = studentNum;
        this.courseCode = courseID;
    }

    public int getEnrollID() {
        return enrollID;
    }

    public void setEnrollID(int enrollID) {
        this.enrollID = enrollID;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Enrollment{");
        sb.append("enrollID=").append(enrollID);
        sb.append(", studentNum=").append(studentNum);
        sb.append(", courseID=").append(courseCode);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
