package za.co.cput.handlers;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.cput.dao.AdminDAO;
import za.co.cput.dao.CourseDAO;
import za.co.cput.dao.EnrollmentDAO;
import za.co.cput.dao.StudentDAO;
import za.co.cput.dao.UserDAO;
import za.co.cput.workers.Admin;
import za.co.cput.workers.Course;
import za.co.cput.workers.Enrollment;
import za.co.cput.workers.Student;
import za.co.cput.workers.User;

public class ServerHandler {


    private ServerSocket serverSocket;
    private Socket clientSocket;
    private UserDAO userDAO;
    private EnrollmentDAO enrollmentDAO;
    private CourseDAO courseDAO;
    private AdminDAO adminDAO;
    private StudentDAO studentDAO;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static ArrayList<User> students = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Enrollment> enrolledStudents = new ArrayList<>();

    public ServerHandler() {
        int port = 8008;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);
            // Create database connection

            userDAO = new UserDAO();
            enrollmentDAO = new EnrollmentDAO();
            courseDAO = new CourseDAO();
            adminDAO = new AdminDAO();
            studentDAO = new StudentDAO();

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while starting the server:");
            e.printStackTrace();

        }

    }

    private void handleClient(Socket clientSocket) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                String response = (String) in.readObject();//Reads a String value in from the ClientSide
                if ("registerUser".equals(response)) {
                    getRegisterUser(in, out);
                } else if ("loginUser".equals(response)) {
                    getStudentLoginUser(in, out);
                } else if ("loginAdmin".equals(response)) {
                    getAdminLoginUser(in, out);
                }else if ("logoutUser".equals(response)) {
                    logoutUser(in, out);
                } else if ("getCourseList".equals(response)) {
                    populateCourseComboBox(in, out);
                } else if ("getCourseDetails".equals(response)) {
                    // Read the selectedCourseCode from the client

                    getFetchCourseDetails(in, out);
                } // Inside the while loop of handleClient
                else if ("isStudentAlreadyEnrolled".equals(response)) {
                    isStudentAlreadyEnrolled(in, out);
                } else if ("enrollInCourse".equals(response)) {
                    enrollStudentInCourse(in, out);
                } else if ("getAllStudents".equals(response)) {
                    getAllStudents(in, out);
                } else if ("removeStudent".equals(response)) {
                    getRemoveStudent(in, out);
                } else if ("getAllCourses".equals(response)) {
                    getAllCourses(in, out);
                } else if ("getAllEnrollments".equals(response)) {
                    getAllEnrollments(in, out);
                } else if ("AddCourse".equals(response)) {
                    getAddNewCourse(in, out);
                } else if ("deleteCourse".equals(response)) {
                    getAdminDeleteCourse(in, out);
                } else if ("removeEnrolledStudent".equals(response)) {
                    getAdminDeleteEnrolledStudent(in, out);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exception in handleClient: "+ e.getMessage() );
        }
    }

    private void getRegisterUser(ObjectInputStream in, ObjectOutputStream out) {
        try {
            User user = (User) in.readObject();
//            Call method from the UserDAO class, to register a user into the database
            boolean registrationResult = userDAO.registerUser(user);

            out.writeObject(registrationResult);
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in getRegisterUser: "+ e.getMessage() );
        }
    }
    public void getAdminLoginUser(ObjectInputStream in, ObjectOutputStream out) {
        try {
            String username = (String) in.readObject();
            String password = (String) in.readObject();
            Admin loggedInUser = adminDAO.loginUser(username, password);
            out.writeObject(loggedInUser);
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in getAdminLoginUser: "+ e.getMessage() );
        }
    }
    private void getStudentLoginUser(ObjectInputStream in, ObjectOutputStream out) {
        try {
            String username = (String) in.readObject();
            String password = (String) in.readObject();
            Student loggedInUser = studentDAO.loginUser(username, password);
            out.writeObject(loggedInUser);
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in getStudentLoginUser: "+ e.getMessage() );
        }
    }

    private void populateCourseComboBox(ObjectInputStream in, ObjectOutputStream out) {
        try {

            ArrayList<String> courses = courseDAO.populateCourseList();
            out.writeObject(courses);
            out.flush();
        } catch (IOException e) {
            System.out.println("IO Exception in populateCourseComboBox: "+ e.getMessage() );
        }

    }

    private void isStudentAlreadyEnrolled(ObjectInputStream in, ObjectOutputStream out) {
        try {
            String studentNum = (String) in.readObject();
            boolean isEnrolled = enrollmentDAO.isStudentAlreadyEnrolled(studentNum);
            out.writeObject(isEnrolled);
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exception in isStudentAlreadyEnrolled: "+ e.getMessage() );
        }
    }

    private void enrollStudentInCourse(ObjectInputStream in, ObjectOutputStream out) {
        try {
            Enrollment enrollment = (Enrollment) in.readObject();

            // Read the Enrollment object
            boolean enrollmentResult = enrollmentDAO.enrollStudentInCourse(enrollment);
            out.writeObject(enrollmentResult);
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in enrollStudentInCourse: "+ e.getMessage() );
        }
    }

    private void getAllStudents(ObjectInputStream in, ObjectOutputStream out) {
        try {
            // Fetch the list of all students from the database
            ArrayList<User> allStudents = userDAO.getAllStudents();

            // Send the list of students to the client
            out.writeObject(allStudents);
            out.flush();
        } catch (IOException e) {
            System.out.println("IO || CNF Exception in getAllStudents: "+ e.getMessage() );
        }
    }

    private void getRemoveStudent(ObjectInputStream in, ObjectOutputStream out) {
        try {
            // Read the student to remove from the client
            User studentToRemove = (User) in.readObject();

            // Call the DAO method to remove the student from the database
            boolean removalResult = userDAO.removeStudent(studentToRemove);

            if (removalResult) { // Send the removal result back to the client
                out.writeObject(removalResult);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
             System.out.println("IO || CNF Exception in getRemoveStudent: "+ e.getMessage() );
        }
    }

    private void getAllCourses(ObjectInputStream in, ObjectOutputStream out) {
        try {
            // Fetch the list of all students from the database
            ArrayList<Course> allCourses = userDAO.getAllCourses();

            // Send the list of students to the client
            out.writeObject(allCourses);
            out.flush();
        } catch (IOException e) {
            System.out.println("IO Exception in getAllCourses: "+ e.getMessage() );
        }
    }

    private void getAllEnrollments(ObjectInputStream in, ObjectOutputStream out) {
        try {
            // Fetch the list of all students from the database
            ArrayList<Enrollment> allEnrollments = userDAO.getAllEnrollments();

            // Send the list of students to the client
            out.writeObject(allEnrollments);
            out.flush();
        } catch (IOException e) {
             System.out.println("IO Exception in getAllEnrollments: "+ e.getMessage() );
              
        }
    }

    private void getAddNewCourse(ObjectInputStream in, ObjectOutputStream out) {
        try {
            Course course = (Course) in.readObject();
            boolean courseAddResult = courseDAO.addCourse(course);
            out.writeObject(courseAddResult);
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in getAddNewCourse: "+ e.getMessage() );
           
        }
    }


    private void getAdminDeleteCourse(ObjectInputStream in, ObjectOutputStream out) {
        try {
            Course courseCode = (Course) in.readObject();
            boolean adminDeleteCourseResult = courseDAO.adminDeleteCourse(courseCode);
            out.writeObject(adminDeleteCourseResult);
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in getAdminDeleteCourse: "+ e.getMessage() );
          
        }
    }

    private void getAdminDeleteEnrolledStudent(ObjectInputStream in, ObjectOutputStream out) {
        try {
            Enrollment enrollment = (Enrollment) in.readObject();
            boolean adminDeleteCourseResult = enrollmentDAO.deleteCourseEnrolledStudent(enrollment);
            out.writeObject(adminDeleteCourseResult);
        } catch (IOException | ClassNotFoundException e) {
             System.out.println("IO || CNF Exception in getAdminDeleteEnrolledStudent: "+ e.getMessage() );
             
        }
    }

    private void getFetchCourseDetails(ObjectInputStream in, ObjectOutputStream out) {
        try {
            String selectedCourseCode = (String) in.readObject();
            // Retrieve course details based on the selected course code from the database
            Course course = courseDAO.getCourseByCode(selectedCourseCode);

            if (course != null) {
                out.writeObject(course); // Send the course details to the client
            } else {
                out.writeObject(null); // Handle the case where the course is not found
            }
        } catch (IOException e) {
             System.out.println("IOException in getFetchCourseDetails: "+ e.getMessage() );
        } catch (ClassNotFoundException ex) {
             System.out.println("CNF Exception in getFetchCourseDetails: "+ ex.getMessage() );
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void logoutUser(ObjectInputStream in, ObjectOutputStream out) {

        try {
            String logout = "Succesful logout";
            out.writeObject(logout);
            out.flush();
            

        } catch (IOException ex) {
             System.out.println("IOException in logoutUser: "+ ex.getMessage() );
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        closeConnection();
        }
    }

    public void closeConnection() {
        try {
// Close the ObjectOutputStream
            if (out != null) {
                out.close();
            }
// Close the ObjectInputStream
            if (in != null) {
                in.close();
            }

            // Close the Socket
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }

            System.out.println("All Connections have been closed successfully.");
        } catch (IOException e) {
            // Handle exceptions when closing resources
            System.out.println("Error closing connections: " + e.getMessage());
            e.printStackTrace();
        }
    }

}