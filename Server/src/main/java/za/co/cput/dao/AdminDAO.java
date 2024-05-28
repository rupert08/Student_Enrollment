package za.co.cput.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.co.cput.database.DBConnection;
import za.co.cput.workers.Admin;
import za.co.cput.workers.Enrollment;
import za.co.cput.workers.Student;
import za.co.cput.workers.User;

public class AdminDAO {

  

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    public AdminDAO() {
        connection = DBConnection.dbConnect();
    }
        public Admin loginUser(String username, String password) {
       
        try {
            String query = "SELECT * FROM Admin WHERE AdminNum = ? AND Password = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
             rs = ps.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminNum(rs.getString("AdminNum"));
                admin.setPassword(rs.getString("Password"));
              
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if login fails
    }
    public boolean addAdmin(Admin admin) {
        try {
            String query = "INSERT INTO Admin (UserID) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, admin.getUserID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adminDeleteCourse(String courseId) {
    try {
        String query = "DELETE FROM Course WHERE CourseCode = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, courseId);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
}
