package za.co.cput.guis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.table.DefaultTableModel;
import za.co.cput.connections.ClientHandler;
import za.co.cput.workers.Course;
import za.co.cput.workers.Enrollment;
import za.co.cput.workers.User;

public class StudentEnrollmentGUI extends JFrame implements ActionListener {

    private JComboBox<String> courseDropdown;
    private JButton enrollButton;
    private JButton logoutButton;
    private JTable courseDetailsTable;
    private DefaultTableModel tableModel;
    private String loggedInUser = "";
    private ClientHandler clientHandler;
    private JTextArea studentNumTextArea; // Add the text area for StudentNum

    public StudentEnrollmentGUI(String studentNum) { // Accept StudentNum as a parameter
        setTitle("Course Enrollment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new FlowLayout());

        JLabel user = new JLabel("Student: ");
        studentNumTextArea = new JTextArea(studentNum);
        studentNumTextArea.setEditable(false); // Make the text area non-editable
        panel.add(user, BorderLayout.NORTH);
        panel.add(studentNumTextArea, BorderLayout.NORTH);

        courseDropdown = new JComboBox<>();
        panel.add(courseDropdown, BorderLayout.CENTER);

        enrollButton = new JButton("Enroll");
        panel.add(enrollButton, BorderLayout.WEST);

        logoutButton = new JButton("Logout");
        panel.add(logoutButton, BorderLayout.SOUTH);

        courseDetailsTable = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Code");
        tableModel.addColumn("Title");
        tableModel.addColumn("Sub1");
        tableModel.addColumn("Sub2");
        tableModel.addColumn("Sub3");
        courseDetailsTable.setModel(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(courseDetailsTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        courseDropdown.addActionListener(this);
        //courseDropdown.addItemListener((ItemListener) this);
        enrollButton.addActionListener(this);
        logoutButton.addActionListener(this);
        //clientHandler = new ClientHandler();

        this.clientHandler = ClientHandler.getInstance();
        populateCourseComboBox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enrollButton) {
            String courseCode = (String) courseDropdown.getSelectedItem();
            String studnNum = studentNumTextArea.getText();
            if (!courseCode.isEmpty() && !studnNum.isEmpty()) {
                boolean isEnrolled = clientHandler.isStudentAlreadyEnrolled(studnNum);
                if (isEnrolled) {
                    // The student is already enrolled, so display a message
                    JOptionPane.showMessageDialog(null, "You are already enrolled in a course.");
                } else {
                    // The student is not enrolled, so proceed with enrollment
                    Enrollment enrollment = new Enrollment(studnNum, courseCode);
                    boolean enrolledResult = clientHandler.enrollInCourse(enrollment);
                    JOptionPane.showMessageDialog(null, "Registration successful! " + enrollment.getStudentNum());
                    
                    if (enrolledResult) {
                        JOptionPane.showMessageDialog(null, "Registration successful! " + enrollment.getStudentNum());
                    } 
                }
            } else {
                JOptionPane.showMessageDialog(null, "Name and Surname are required fields.");
            }
        } else if (e.getSource() == logoutButton) {
            clientHandler.logoutUser();
            dispose();
            new LoginGUI();
        } else if (e.getSource() == courseDropdown) {
            String selectedCourseCode = (String) courseDropdown.getSelectedItem();

            if (selectedCourseCode != null) {
                Course selectedCourse = clientHandler.fetchCourseDetailsFromServer(selectedCourseCode);
                if (selectedCourse != null) {
                    // Clear the table
                    tableModel.setRowCount(0);

                    // Populate the table with course details
                    tableModel.addRow(new Object[]{
                        selectedCourse.getCourseCode(),
                        selectedCourse.getTitle(),
                        selectedCourse.getSub1(),
                        selectedCourse.getSub2(),
                        selectedCourse.getSub3()
                    });
                } else {
                    // Handle if course details retrieval fails
                }
            } else {
                // Handle if no course is selected
            }
        }
    }

    private void populateCourseComboBox() {
        JComboBox<String> courseComboBox = courseDropdown;
        clientHandler.populateCourseCombobox(courseComboBox);

    }

}
