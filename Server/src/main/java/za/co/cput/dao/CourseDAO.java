package za.co.cput.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.cput.database.DBConnection;
import za.co.cput.workers.Course;

public class CourseDAO {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    public CourseDAO() {
      connection = DBConnection.dbConnect();
    }

    public boolean addCourse(Course course) {
        try {
            String query = "INSERT INTO Course (CourseCode, Title, Subject1, Subject2, Subject3) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, course.getCourseCode());
            statement.setString(2, course.getTitle());
            statement.setString(3, course.getSub1());
            statement.setString(4, course.getSub2());
            statement.setString(5, course.getSub3());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public boolean adminDeleteCourse(Course courseCode) {
    try {
        String query = "DELETE FROM Course WHERE CourseCode = ?";
        
        ps = connection.prepareStatement(query);
        ps.setString(1, courseCode.getCourseCode());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

   

    // Add more methods for course-specific operations

//    public List<String> populateCourseList() {
        public ArrayList<String> populateCourseList() {
         List<String> courses = new ArrayList<>();
       
         try {
        String query = "SELECT CourseCode FROM Course";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        
         while(rs.next()){
        String courseCode =rs.getString("CourseCode");
        
        courses.add(courseCode);
            System.out.println(courseCode);
           
        }
        
    } catch (SQLException e) {
        System.out.println("An error occurred while populating the course list:");
        e.printStackTrace();
        // Rethrow the exception if needed or handle it further
    }
      
        return  (ArrayList<String>) courses;
       
        }

   public Course getCourseByCode(String courseCode) {
    Course course = null;
    String query = "SELECT CourseCode, Title, Subject1, Subject2, Subject3 FROM Course WHERE CourseCode = ?";
    
    try{ 
        ps = connection.prepareStatement(query); 
        ps.setString(1, courseCode);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            // Retrieve course details from the database
            String title = rs.getString("Title");
            String sub1 = rs.getString("Subject1");
            String sub2 = rs.getString("Subject2");
            String sub3 = rs.getString("Subject3");
            
            course = new Course(courseCode, title, sub1, sub2, sub3);
            System.out.println(course);
           
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return course;
}
}
