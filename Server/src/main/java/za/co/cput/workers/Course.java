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
public class Course implements Serializable{
    private String courseCode;
    private String title;
    private String sub1;
    private String sub2;
    private String sub3;

    public Course(String courseCode, String title, String sub1, String sub2, String sub3) {
        this.courseCode = courseCode;
        this.title = title;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
    }

    public Course() {
        }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course{");
        sb.append("courseCode=").append(courseCode);
        sb.append(", title=").append(title);
        sb.append(", sub1=").append(sub1);
        sb.append(", sub2=").append(sub2);
        sb.append(", sub3=").append(sub3);
        sb.append('}');
        return sb.toString();
    }
    
    
}
