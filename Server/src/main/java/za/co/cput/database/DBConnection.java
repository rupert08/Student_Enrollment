package za.co.cput.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
    
    private static String url = "jdbc:derby://localhost:1527/StudentEnrollmentSystem";
    private static String user = "username";
    private static String password = "password";
    
    private static Connection con;

    public DBConnection() {
    }

    public static Connection dbConnect() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(url, user, password);
                System.out.println("DB has been connected");
            } catch (SQLException ex) {
                // Handle the exception with a JOptionPane message
                JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return con;
    }

    public static void closeDBConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database connection has been closed.");
            } catch (SQLException e) {
                // Handle the exception with a JOptionPane message
                JOptionPane.showMessageDialog(null, "Error while closing the database connection: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
