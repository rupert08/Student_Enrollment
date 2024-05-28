package za.co.cput.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import za.co.cput.workers.Admin;
import za.co.cput.workers.Course;
import za.co.cput.workers.Enrollment;
import za.co.cput.workers.Student;
import za.co.cput.workers.User;

public class ClientHandler {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket clientSocket;
    private static ClientHandler instance;

    // Constructor for the ClientHandler class
    public ClientHandler() {
        // Establish a connection to the server
        connectToServer();
    }

    // Singleton pattern: Get or create an instance of ClientHandler
    public static ClientHandler getInstance() {
        if (instance == null) {
            instance = new ClientHandler();
        }
        return instance;
    }

    // Establish a connection to the server
    public boolean connectToServer() {
        try {
            clientSocket = new Socket("localhost", 8008); // Connect to the server
            System.out.println("Connected to the Server");
            // Set up input and output streams for communication
            getStreams();
            return true;
        } catch (IOException e) {
            // Handle the exception with informative error messages
            System.out.println("Connection to the server failed. Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Set up input and output streams for communication
    public boolean getStreams() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream()); // Create output stream
            in = new ObjectInputStream(clientSocket.getInputStream()); // Create input stream
            System.out.println("Opened Streams for communication");
            return true;
        } catch (IOException e) {
            // Handle the exception with informative error messages
            System.out.println("Failed to open communication streams. Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Close the connection to the server
    public void closeConnection() {
        try {
            // Close the ObjectOutputStream
            out.close();
            // Close the ObjectInputStream
            in.close();
            // Close the Socket
            clientSocket.close();
            System.out.println("All Connections have been closed successfully.");
        } catch (IOException e) {
            // Handle exceptions when closing resources and display an error message
            System.out.println("Error closing connections: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error closing connections: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    // Register a user with the server
    public boolean registerUser(User user) {
        try {
            out.writeObject("registerUser"); // Send the method name
            out.writeObject(user); // Send the User object
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof Boolean) {
                return (Boolean) response; // Server returns a boolean indicating success or failure
            } if (response instanceof String) {
                return (boolean) response;
            } else {
                String errorMessage = "Unexpected response from the server during registration.";
                JOptionPane.showMessageDialog(null, errorMessage, "Registration Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            String errorMessage = "An error occurred during registration: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMessage, "Registration Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Add a new course with the server
    public boolean AddNewCourse(Course course) {
        try {
            out.writeObject("AddCourse"); // Send the method name
            out.writeObject(course); // Send the User object
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof Boolean) {
                return (Boolean) response; // Server returns a boolean indicating success or failure
            } if (response instanceof String) {
                return (boolean) response;
            } else {
                String errorMessage = "Unexpected response from the server during adding a course.";
                JOptionPane.showMessageDialog(null, errorMessage, "Course Addition Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false; // Handle exceptions as registration failure
        }
    }

    // Log in a student user with the server
    public Student loginUser(String username, String password) {
        try {
            out.writeObject("loginUser"); // Send the method name
            // Send username & password
            out.writeObject(username); 
            out.writeObject(password);  
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof Student) {
                return (Student) response; // Return the User object
            } else {
                return null; // Handle login failure (e.g., return null or throw an exception)
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exeption when trying to logout"+e.getMessage());
            return null; // Handle exceptions as login failure
        }finally {
            closeConnection(); // Make sure to close the connection in all cases
        }
    }

    // Log in an admin user with the server
    public Admin loginAdmin(String username, String password) {
        try {
            out.writeObject("loginAdmin"); // Send the method name
            out.writeObject(username); // Send username
            out.writeObject(password); // Send password
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof Admin) {
                return (Admin) response; // Return the User object
            } else {
                return null; // Handle login failure (e.g., return null or throw an exception)
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exeption when trying to login"+e.getMessage());
            return null; // Handle exceptions as login failure
        }
    }

    // Get a list of all student users from the server
    public ArrayList<User> getAllStudents() {
        try {
            out.writeObject("getAllStudents"); // Send the method name
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof ArrayList) {
                return (ArrayList<User>) response; // Return the list of students
            } else {
                // Handle the case where the server response is not as expected
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, e.g., connection errors or deserialization issues
            System.out.println("IO || CNF Exeption when trying getAllStudents"+e.getMessage());
            return new ArrayList<>();
        }
    }

    // Get a list of all courses from the server
    public ArrayList<Course> getAllCourses() {
        try {
            out.writeObject("getAllCourses"); // Send the method name
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof ArrayList) {
                return (ArrayList<Course>) response; // Return the list of courses
            } else {
                // Handle the case where the server response is not as expected
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, e.g., connection errors or deserialization issues
            System.out.println("IO || CNF Exeption when trying getAllCourses" +e.getMessage());
            return new ArrayList<>();
        }
    }

    // Get a list of all enrollments from the server
    public ArrayList<Enrollment> getAllEnrollments() {
        try {
            out.writeObject("getAllEnrollments"); // Send the method name
            out.flush();
            Object response = in.readObject(); // Read the server response

            if (response instanceof ArrayList) {
                return (ArrayList<Enrollment>) response; // Return the list of enrollments
            } else {
                // Handle the case where the server response is not as expected
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, e.g., connection errors or deserialization issues
            System.out.println("IO || CNF Exeption when trying getAllEnrollments: " +e.getMessage());
            return new ArrayList<>();
        }
    }

    // Log out the current user from the server
    public void logoutUser() {
        try {
            String response = (String) in.readObject();
            out.writeObject("logoutUser");
            out.flush();

            System.out.println(response);
            if ("Successful logout".equals(response) && response instanceof String) {
                // Handle successful logout, for example, return to the login screen
                System.out.println("Logout was successful.");
                
            } else {
                System.out.println("Logout failed. Server response: " + response);
            }
        } catch (IOException e) {
           System.out.println("IO exception when logout: "+ e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("CNF exception when logout: "+ ex.getMessage());
        }finally{
        closeConnection();
        }
        
    }

    // Check if a student is already enrolled in a course
    public boolean isStudentAlreadyEnrolled(String studentNum) {
        try {
            out.writeObject("isStudentAlreadyEnrolled");
            out.writeObject(studentNum);
            out.flush();

            Object response = in.readObject();
            if (response instanceof Boolean) {
                return (boolean) response;
            } else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exception in isStudentAlreadyEnrolled: "+ e.getMessage() );
            return false;
        }
    }

    // Enroll a student in a course
    public boolean enrollInCourse(Enrollment enrollment) {
        try {
            out.writeObject("enrollInCourse");
            out.writeObject(enrollment);
            out.flush();

            Object response = in.readObject();

            if (response instanceof Boolean) {
                return (boolean) response;
            } else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
          System.out.println("IO || CNF Exception in enrollInCourse: : "+ e.getMessage() );
        }
        return false;
    }

    // Remove a student from the server
    public boolean removeStudent(User studentUser) {
        try {
            out.writeObject("removeStudent"); // Send the method name
            out.writeObject(studentUser); // Send the selected student to remove
            out.flush();

            Object response = in.readObject(); // Read the server response

            if (response instanceof Boolean) {
                return (Boolean) response; // Return whether the removal was successful
            } else {
                // Handle the case where the server response is not as expected
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, e.g., connection errors or deserialization issues
            System.out.println("IO || CNF Exception in removeStudent: "+ e.getMessage() );
            return false;
        }
    }

    // Delete a course from the server
    public boolean deleteCourse(Course courseId) {
        try {
            out.writeObject("deleteCourse");
            out.writeObject(courseId);
            out.flush();

            Object response = in.readObject();

            if (response instanceof Boolean) {
                return (Boolean) response;
            } else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exception in deleteCourse: "+ e.getMessage() );
            return false;
        }
    }

    // Remove an enrolled student from a course
    public boolean removeEnrolledStudent(Enrollment enrolledStudent) {
        try {
            out.writeObject("removeEnrolledStudent"); // Send the method name
            out.writeObject(enrolledStudent); // Send the selected student to remove
            out.flush();

            Object response = in.readObject(); // Read the server response

            if (response instanceof Boolean) {
                return (Boolean) response; // Return whether the removal was successful
            } else {
                // Handle the case where the server response is not as expected
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, e.g., connection errors or deserialization issues
           System.out.println("IO || CNF Exception in removeEnrolledStudent: "+ e.getMessage() );
            return false;
        }
    }

    // Populate a JComboBox with a list of courses
    public void populateCourseCombobox(JComboBox<String> courseComboBox) {
        try {
            out.writeObject("getCourseList"); // Send the method name to get the course list

            Object response = in.readObject(); // Read the server response

            if (response instanceof ArrayList) {
                ArrayList<String> courses = (ArrayList<String>) response;
                // Populate the course combo box with the retrieved courses
                courseComboBox.removeAllItems();
                for (String course : courses) {
                    if (courses != null) {
                        courseComboBox.addItem(course);
                    }
                }
            } else {
                System.out.println("Could not populate the combobox");
            }
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("IO || CNF Exception in populataComboBox: "+ e.getMessage() );
            // Handle exceptions
        }
    }

    // Fetch course details from the server
    public Course fetchCourseDetailsFromServer(String selectedCourseCode) {
        try {
            out.writeObject("getCourseDetails"); // Send the method name
            out.writeObject(selectedCourseCode); // Send the selected course code
            out.flush();

            Object response = in.readObject();
            System.out.println("Populated the table with this: " + response);

            if (response instanceof Course) {
                return (Course) response;
            } else {
                return null; // Handle if course details retrieval fails
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO || CNF Exception in ferchCourseDetailsFromServer: "+ e.getMessage() );
            return null; // Handle exceptions
        }
    }
}
