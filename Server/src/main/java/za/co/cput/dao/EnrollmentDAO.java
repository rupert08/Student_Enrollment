package za.co.cput.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import za.co.cput.database.DBConnection;
import za.co.cput.handlers.ServerHandler;
import za.co.cput.workers.Course;
import za.co.cput.workers.Enrollment;

public class EnrollmentDAO {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public EnrollmentDAO() {
        connection = DBConnection.dbConnect();
    }
public boolean isStudentAlreadyEnrolled(String studentNum) {
    try {
        String query = "SELECT COUNT(*) FROM Enrollment WHERE StudentNum = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, studentNum);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; // If count > 0, the student is already enrolled
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // If an error occurs or the query fails
}

    
    public boolean enrollStudentInCourse(Enrollment enrollment) {
        try {
            String query = "INSERT INTO Enrollment (StudentNum, CourseCode) VALUES (?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, enrollment.getStudentNum()); 
            ps.setString(2, enrollment.getCourseCode());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public boolean deleteCourseEnrolledStudent(Enrollment enrollment) {
    try {
        String query = "DELETE FROM Enrollment WHERE EnrollNum = ?";
        
        ps = connection.prepareStatement(query);
        ps.setInt(1, enrollment.getEnrollID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

 
}
