package za.co.cput.guis;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import za.co.cput.connections.ClientHandler;
import za.co.cput.workers.Course;
import za.co.cput.workers.Enrollment;
import za.co.cput.workers.Student;
import za.co.cput.workers.User;

public class AdminGUI extends JFrame implements ActionListener {
    private JButton addStudentButton;
    private JButton removeStudentButton; // New button
    private JButton addCourseButton;
    private JButton removeCourseButton; // New button
    private JButton addEnrolledStudentButton;
    private JButton removeEnrolledStudentButton; // New button
    private JButton searchStudentsButton;
    private JButton refreshButton;
    private JTextField searchTextField;
    private JButton logoutButton;

    private JTable studentTable;
    private DefaultTableModel studentTableModel;

    private JTable courseTable;
    private DefaultTableModel courseTableModel;

    private JTable enrolledStudentsTable;
    private DefaultTableModel enrolledStudentsTableModel;
    private ClientHandler clientHandler;
    private ArrayList<User> students = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Enrollment> enrolledStudents = new ArrayList<>(); // New list

    public AdminGUI(String adminNum) {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.lightGray);
        add(topPanel, BorderLayout.NORTH);

        logoutButton = new JButton("Logout");
       
        topPanel.add(logoutButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.lightGray);
        centerPanel.setLayout(new GridLayout(2, 2, 5, 5));
        add(centerPanel, BorderLayout.CENTER);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.lightGray);
        panel1.setLayout(new BorderLayout());
        centerPanel.add(panel1);

        JLabel studentTableLabel = new JLabel("Student Table"); // New JLabel
        panel1.add(studentTableLabel, BorderLayout.NORTH);

        studentTableModel = new DefaultTableModel(new String[]{"User ID","Name", "Surname"}, 0);
        studentTable = new JTable(studentTableModel);
        panel1.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel studentButtonPanel = new JPanel(); // New sub-panel
        studentButtonPanel.setBackground(Color.lightGray);
        studentButtonPanel.setLayout(new FlowLayout());
        addStudentButton = new JButton("Add Student");
        removeStudentButton = new JButton("Remove Student");
        studentButtonPanel.add(addStudentButton);
        studentButtonPanel.add(removeStudentButton);
        panel1.add(studentButtonPanel, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.lightGray);
        panel2.setLayout(new BorderLayout());
        centerPanel.add(panel2);

        JLabel courseTableLabel = new JLabel("Course Table"); // New JLabel
        panel2.add(courseTableLabel, BorderLayout.NORTH);

        courseTableModel = new DefaultTableModel(new String[]{"Course Code", "Title", "Sub1", "Sub2", "Sub3"}, 0);
        courseTable = new JTable(courseTableModel);
        panel2.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        JPanel courseButtonPanel = new JPanel(); // New sub-panel
        courseButtonPanel.setBackground(Color.lightGray);
        courseButtonPanel.setLayout(new FlowLayout());
        addCourseButton = new JButton("Add Course");
        removeCourseButton = new JButton("Remove Course");
        courseButtonPanel.add(addCourseButton);
        courseButtonPanel.add(removeCourseButton);
        panel2.add(courseButtonPanel, BorderLayout.SOUTH);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.lightGray);
        panel3.setLayout(new BorderLayout());
        centerPanel.add(panel3);

        JLabel enrolledStudentsTableLabel = new JLabel("Enrolled Students Table"); // New JLabel
        panel3.add(enrolledStudentsTableLabel, BorderLayout.NORTH);

        enrolledStudentsTableModel = new DefaultTableModel(new String[]{"Enrollment Number", "Student Number", "Course Code"}, 0);
        enrolledStudentsTable = new JTable(enrolledStudentsTableModel);
        panel3.add(new JScrollPane(enrolledStudentsTable), BorderLayout.CENTER);

        JPanel enrolledButtonPanel = new JPanel(); // New sub-panel
        enrolledButtonPanel.setBackground(Color.lightGray);
        enrolledButtonPanel.setLayout(new FlowLayout());
        addEnrolledStudentButton = new JButton("Add Enrolled Student");
        removeEnrolledStudentButton = new JButton("Remove Enrolled Student");
        enrolledButtonPanel.add(addEnrolledStudentButton);
        enrolledButtonPanel.add(removeEnrolledStudentButton);
        panel3.add(enrolledButtonPanel, BorderLayout.SOUTH);

        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.lightGray);
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerPanel.add(panel4);

        searchTextField = new JTextField(20);
        searchStudentsButton = new JButton("Search Students");
        refreshButton = new JButton("Refresh");
        

        panel4.add(searchTextField);
        panel4.add(searchStudentsButton);
        panel4.add(refreshButton);

        addStudentButton.addActionListener(this);
        logoutButton.addActionListener(this);
        addCourseButton.addActionListener(this);
        addEnrolledStudentButton.addActionListener(this);
        removeStudentButton.addActionListener(this);
        removeCourseButton.addActionListener(this);
        removeEnrolledStudentButton.addActionListener(this); // Added action listener
        searchStudentsButton.addActionListener(this);
        refreshButton.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        clientHandler = ClientHandler.getInstance();
        // Populate the students table
        populateStudentsTable();
        populateCourseTable();
        populateEnrollTable();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            // Implement add student functionality here
            String name = JOptionPane.showInputDialog(this, "Enter student name:");
            String surname = JOptionPane.showInputDialog(this, "Enter student surname:");

            User user = new User(name, surname);
//            boolean success = clientHandler.adminRegisterUser(user);
            
//            if(success){
//                students.add(user);
//                studentTableModel.addRow(new String[]{String.valueOf(user.getUserID()),user.getName(), user.getSurname()});
//                JOptionPane.showMessageDialog(this, " added the student: "+ user.toString() );
//                populateStudentsTable();
//            }
//            else {
//                // Handle the case when the server operation fails
//                JOptionPane.showMessageDialog(this, "Failed to add the student.");}
//  
        } else if (e.getSource() == logoutButton) {
            // Implement logout functionality here
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                dispose();
                new LoginGUI();
            }
        } else if (e.getSource() == addCourseButton) {
            // Implement add course functionality here
            String courseCode = JOptionPane.showInputDialog(this, "Enter course code:");
            String title = JOptionPane.showInputDialog(this, "Enter course title:");
            String sub1 = JOptionPane.showInputDialog(this, "Enter subject 1:");
            String sub2 = JOptionPane.showInputDialog(this, "Enter subject 2:");
            String sub3 = JOptionPane.showInputDialog(this, "Enter subject 3:");
            Course newCourse = new Course(courseCode, title, sub1, sub2, sub3);
            boolean success = clientHandler.AddNewCourse(newCourse);
            
            if(success){
                courses.add(newCourse);
                courseTableModel.addRow(new String[]{newCourse.getCourseCode(), newCourse.getTitle(), newCourse.getSub1(), newCourse.getSub2(), newCourse.getSub3()});
                populateCourseTable();
            }
            else {
                // Handle the case when the server operation fails
                JOptionPane.showMessageDialog(this, "Failed to add the student.");
            }
  
        
        } else if (e.getSource() == addEnrolledStudentButton) {
            // Implement add enrolled student functionality here
           
            String studentNumber = JOptionPane.showInputDialog(this, "Enter Student Number:");
            String courseCode = JOptionPane.showInputDialog(this, "Enter Course Code");
            Enrollment enrolledStudent = new Enrollment( studentNumber, courseCode);
            
            boolean success = clientHandler.enrollInCourse(enrolledStudent);
            if(success){
            enrolledStudents.add(enrolledStudent);
            enrolledStudentsTableModel.addRow(new String[]{String.valueOf(enrolledStudent.getEnrollID()), studentNumber, courseCode});
            JOptionPane.showMessageDialog(this, "Student Successfully Enrolled : "
                                            + enrolledStudent.toString());
            populateEnrollTable();
            }else {
                // Handle the case when the server operation fails
                JOptionPane.showMessageDialog(this, "Failed to Enroll the student.");}
  
        } else if (e.getSource() == removeStudentButton) {
            // Implement remove student functionality here
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                int deletion =JOptionPane.showConfirmDialog(this, "Are you sure you want to Delete this Student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if(deletion == JOptionPane.YES_OPTION){
                User selectedUser = students.get(selectedRow);
                boolean success = clientHandler.removeStudent(selectedUser);
                if(success){
                students.remove(selectedRow);
                studentTableModel.removeRow(selectedRow);}
               JOptionPane.showMessageDialog(this, "Succesfully deleted : " + selectedUser.getName());

                populateStudentsTable();
            }else {
            // Handle the case where the server-side removal was not successful
            // You might show an error message to the user or take appropriate action
            JOptionPane.showMessageDialog(this, "Failed to remove the student from the server.");
        }
            }
        } else if (e.getSource() == removeCourseButton) {
            // Implement remove course functionality here
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                
                Course selectedCourse = courses.get(selectedRow);
                boolean success = clientHandler.deleteCourse(selectedCourse);
                if(success){
                courses.remove(selectedRow);
                courseTableModel.removeRow(selectedRow);
                 JOptionPane.showMessageDialog(this, "Successfully deleted:"+selectedCourse.getCourseCode());
                populateCourseTable();
                }else{ JOptionPane.showMessageDialog(this, "Failed to remove the Course from the server/database.");
                }
                
                // Notify the server to remove the student (assuming you have a corresponding method in your ClientHandler)
            
            }
        } else if (e.getSource() == removeEnrolledStudentButton) {
            // Implement remove enrolled student functionality here
            int selectedRow = enrolledStudentsTable.getSelectedRow();
            if (selectedRow != -1) {
                
                Enrollment selectedEnrollment = enrolledStudents.get(selectedRow);
                boolean success = clientHandler.removeEnrolledStudent(selectedEnrollment);
                if(success){
                enrolledStudents.remove(selectedRow);
                enrolledStudentsTableModel.removeRow(selectedRow);
                 JOptionPane.showMessageDialog(this, "Student: ."+selectedEnrollment.getStudentNum() +
                                                "Successfully Removed From: "+selectedEnrollment.getCourseCode() 
                                                   + "course");
                 
                 populateEnrollTable();
                }else{
                JOptionPane.showMessageDialog(this, "Failed to Remove the student from the database/server.");
                }}
        } else if (e.getSource() == searchStudentsButton) {
            String query = searchTextField.getText().trim();
            searchStudentByNumber(query);
    
} else if (e.getSource() == refreshButton) {
    refreshtables();
    }}
   private void refreshtables() {
 
    populateEnrollTable();
    populateStudentsTable();
    populateCourseTable();
}


     private void populateStudentsTable() {
        try {
            studentTableModel.setRowCount(0);
            ArrayList<User> studentsFromServer = clientHandler.getAllStudents(); // You need to implement this method in your ClientHandler
                
            for (User user : studentsFromServer) {
                students.add(user);
                studentTableModel.addRow(new String[]{String.valueOf(user.getUserID()),user.getName(), user.getSurname()});
            }
        } catch (Exception e) {
            // Handle any exceptions, e.g., connection errors or database issues
            JOptionPane.showMessageDialog(this, "Failed to populate the students table.");
            e.printStackTrace();
        }
    }
     private void populateCourseTable() {
        try {
            courseTableModel.setRowCount(0);
            ArrayList<Course> CoursesFromServer = clientHandler.getAllCourses(); // You need to implement this method in your ClientHandler
                
            for (Course course: CoursesFromServer) {
                courses.add(course);
                courseTableModel.addRow(new String[]{course.getCourseCode(),course.getTitle(), course.getSub1()
                                                ,course.getSub2(),course.getSub3()});
            }
        } catch (Exception e) {
            // Handle any exceptions, e.g., connection errors or database issues
            JOptionPane.showMessageDialog(this, "Failed to populate the Course table.");
            e.printStackTrace();
        }
    }
     private void populateEnrollTable() {
        try {
            enrolledStudentsTableModel.setRowCount(0);
         
            ArrayList<Enrollment> EnrollmentsFromServer = clientHandler.getAllEnrollments(); // You need to implement this method in your ClientHandler
                
            for (Enrollment enroll: EnrollmentsFromServer) {
                enrolledStudents.add(enroll);
                enrolledStudentsTableModel.addRow(new String[]{String.valueOf(enroll.getEnrollID()),enroll.getStudentNum(),enroll.getCourseCode()});
            }
        } catch (Exception e) {
            // Handle any exceptions, e.g., connection errors or database issues
            JOptionPane.showMessageDialog(this, "Failed to populate the Course table.");
            e.printStackTrace();
        }
    }
     private void searchStudentByNumber(String studentNumber) {
    enrolledStudentsTableModel.setRowCount(0); // Clear the current table
    for (Enrollment enrolledStudent : enrolledStudents) {
        if (enrolledStudent.getStudentNum().equalsIgnoreCase(studentNumber)) {
            enrolledStudentsTableModel.addRow(new String[]{
                String.valueOf(enrolledStudent.getEnrollID()).toUpperCase(),
                enrolledStudent.getStudentNum().toUpperCase(),
                enrolledStudent.getCourseCode().toUpperCase()
            });
        }else {
            if (enrolledStudent.getCourseCode().equalsIgnoreCase(studentNumber)) {
                enrolledStudentsTableModel.addRow(new String[]{
                String.valueOf(enrolledStudent.getEnrollID()).toUpperCase(),
                enrolledStudent.getStudentNum().toUpperCase(),
                enrolledStudent.getCourseCode().toUpperCase()
            });
    }
}   }
}}
