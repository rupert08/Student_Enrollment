package za.co.cput.dao;

import za.co.cput.workers.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import za.co.cput.database.DBConnection;
import za.co.cput.handlers.ServerHandler;
import za.co.cput.workers.Admin;
import za.co.cput.workers.Course;
import za.co.cput.workers.Enrollment;
import za.co.cput.workers.Student;

public class UserDAO {
  
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
  
    public UserDAO() {
        connection = DBConnection.dbConnect();
      
    }


    
public boolean adminRegisterUser(User user) {
        try {
            String query = "INSERT INTO UserTable (Name, Surname) VALUES (?, ?)";
//            String password = generateRandomNumericPassword(6);
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());           
            int rowsAffected = ps.executeUpdate();
            
            insertStudent();
          
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Succuss! The information has beeen saved");
                return true; // Registration success
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Registration failure
    }
    public boolean registerUser(User user) {
        try {
            String query = "INSERT INTO UserTable (Name, Surname, Role) VALUES (?, ?, ?)";
//            String password = generateRandomNumericPassword(6);
            
            
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());           
            ps.setString(3, user.getRole());
            int rowsAffected = ps.executeUpdate();
            
            if (user.getRole().equalsIgnoreCase("Student")){
            insertStudent();
            }
            else if(user.getRole().equalsIgnoreCase("Admin")){
            insertAdmin();
            }
            
                
               
           
            
             
                
            
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Succuss! The information has beeen saved");
                return true; // Registration success
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Registration failure
    }
    public boolean insertAdmin() {
        try {
            String query = "INSERT INTO Admin (AdminNum, UserID, Password) VALUES (?,?,?)";
            String adminNr = "A"+generateRandomNum(5);
            int userID = getLastUserID();
            String password = generateRandomNumericPassword(6);
            ps = connection.prepareStatement(query);
            ps.setString(1, adminNr);
            ps.setInt(2, userID);
            ps.setString(3, password);
            
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                Admin  admin = new Admin(adminNr, password);
                JOptionPane.showMessageDialog(null, "Succuss! The information has beeen saved"+admin.toString());
                return true; // Registration success
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Registration failure
    }
    public boolean insertStudent() {
        try {
            String query = "INSERT INTO Student (StudentNum, UserID, Password) VALUES (?, ?,?)";
            String studNr = "S"+generateRandomNum(5);
            int userID = getLastUserID();
            String password = generateRandomNumericPassword(6);
            ps = connection.prepareStatement(query);
            ps.setString(1, studNr);
            ps.setInt(2, userID);
            ps.setString(3, password);
            
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                Student stud = new Student(studNr, password);
                JOptionPane.showMessageDialog(null, "Succuss! The information has beeen saved"+stud.toString());
                return true; // Registration success
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Registration failure
    }
    
    
    public String generateRandomNumericPassword(int length) {
    StringBuilder password = new StringBuilder();
    Random random = new Random();
    

    for (int i = 0; i < length; i++) {
        password.append(random.nextInt(10)); // Append a random digit (0-9)
    }

    return password.toString();
}   
    
    
    public String generateRandomNum(int length) {
    StringBuilder studNr = new StringBuilder();
    Random random = new Random();
    
    
   
    for (int i = 0; i < length; i++) {
        studNr.append(random.nextInt(5)); // Append a random digit (0-9)
    }

    return studNr.toString();
}
    
public int getLastUserID() {
    try {
        String query = "SELECT MAX(UserID) FROM UserTable";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1); // Retrieve the last UserID
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return -1; // Return -1 if no UserID is found or an error occurs
}
    
public ArrayList<User> getAllStudents() {
    ArrayList<User> students = new ArrayList<>();

    try {
        String query = "SELECT * FROM UserTable WHERE Role = 'Student'";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        while (rs.next()) {
            User student = new User();
            student.setUserID(rs.getInt("UserID"));
            student.setName(rs.getString("Name"));
            student.setSurname(rs.getString("Surname"));
            students.add(student);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return students;
}
public boolean removeStudent(User student) {
        try {
            // Your SQL DELETE query to remove the student from the database
            String query = "DELETE FROM UserTable WHERE UserID =?";
            
            // Set up the PreparedStatement with the student's unique identifier
            ps = connection.prepareStatement(query);
            ps.setInt(1, student.getUserID());

            // Execute the delete operation
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Deletion successful
            }         
// If one or more rows were affected, the removal was successful
//            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions, e.g., database connection errors
        }

        return false; // Return false if the removal fails
    }
    public ArrayList<Course> getAllCourses() {
    ArrayList<Course> courses = new ArrayList<>();

    try {
        String query = "SELECT * FROM Course ";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        while (rs.next()) {
            Course course = new Course();
            course.setCourseCode(rs.getString("CourseCode"));
            course.setTitle(rs.getString("Title"));
            course.setSub1(rs.getString("Subject1"));
            course.setSub2(rs.getString("Subject2"));
            course.setSub3(rs.getString("Subject3"));
            courses.add(course);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return courses;
}
    public ArrayList<Enrollment> getAllEnrollments() {
    ArrayList<Enrollment> enrollments = new ArrayList<>();

    try {
        String query = "SELECT * FROM Enrollment ";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        while (rs.next()) {
            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollID(rs.getInt("EnrollNum"));
            enrollment.setStudentNum(rs.getString("StudentNum"));
            enrollment.setCourseCode(rs.getString("CourseCode"));
            enrollments.add(enrollment);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return enrollments;
}
}
