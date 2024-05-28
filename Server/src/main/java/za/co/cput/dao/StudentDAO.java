package za.co.cput.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.co.cput.database.DBConnection;
import za.co.cput.workers.Student;

public class StudentDAO {

   private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    public StudentDAO() {
        connection = DBConnection.dbConnect();
    }

    public boolean addStudent(Student student) {
        try {
            String query = "INSERT INTO Student (StudentNum, UserID) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, student.getStudentNum());
            statement.setInt(2, student.getUserID()); // Assuming userID is known
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Student loginUser(String username, String password) {
       
        try {
            String query = "SELECT * FROM Student WHERE StudentNum = ? AND Password = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
             rs = ps.executeQuery();

            if (rs.next()) {
                Student stud = new Student();
                stud.setStudentNum(rs.getString("StudentNum"));
                stud.setPassword(rs.getString("Password"));
               //stud.setRole(rs.getString("Role"));
                return stud;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if login fails
    }
}
