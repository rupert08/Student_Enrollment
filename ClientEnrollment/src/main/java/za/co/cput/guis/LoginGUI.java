package za.co.cput.guis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import za.co.cput.connections.ClientHandler;
import za.co.cput.workers.Admin;
import za.co.cput.workers.Student;
import za.co.cput.workers.User;

public class LoginGUI extends JFrame implements ActionListener {
    private JTextField userText;
    private JPasswordField passwordText;
    private JRadioButton adminRadioButton;
    private JRadioButton studentRadioButton;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton registrationButton;
    private JButton exitButton;
    private ClientHandler clientHandler;

    public LoginGUI() {
       
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel headingLabel = new JLabel("Student Enrollment System");
        headingLabel.setBounds(10, 10, 280, 25);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(headingLabel);

        adminRadioButton = new JRadioButton("Admin");
        adminRadioButton.setBackground(Color.lightGray);
        adminRadioButton.setBounds(10, 40, 100, 25);
        panel.add(adminRadioButton);

        studentRadioButton = new JRadioButton("Student");
        studentRadioButton.setBackground(Color.lightGray);
        studentRadioButton.setBounds(120, 40, 100, 25);
        studentRadioButton.setSelected(true); // Set the default radio button to "Student"
        panel.add(studentRadioButton);

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadioButton);
        roleGroup.add(studentRadioButton);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 70, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 70, 160, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 100, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 100, 160, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.GRAY);
        loginButton.setForeground(Color.white);
        loginButton.setBounds(10, 130, 80, 25);
        panel.add(loginButton);

        registrationButton = new JButton("Register");
        registrationButton.setBackground(Color.GRAY);
        registrationButton.setForeground(Color.white);
        registrationButton.setBounds(100, 130, 100, 25);
        panel.add(registrationButton);

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.GRAY);
        exitButton.setForeground(Color.white);
        exitButton.setBounds(210, 130, 80, 25);
        panel.add(exitButton);

        // Add action listeners
        loginButton.addActionListener(this);
        registrationButton.addActionListener(this);
        exitButton.addActionListener(this);
        adminRadioButton.addActionListener(this);
        studentRadioButton.addActionListener(this);
        
      this.clientHandler = ClientHandler.getInstance();
       
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
        String username = userText.getText();
        String password = new String(passwordText.getPassword());
        String userType ="" ;
        if(adminRadioButton.isSelected()){
        userType = "Admin";
        }
        else{
        userType = "Student";
        }
        
        try {
            if (userType.equals("Admin")) {
                // Admin login
                Admin adminUser = (Admin) clientHandler.loginAdmin(username, password);

                if (adminUser != null) {
                    // Valid admin credentials
                    JOptionPane.showMessageDialog(this, "Admin login successful! : " +adminUser.getAdminNum());
                    dispose();
                    new AdminGUI(adminUser.getAdminNum());
                } else {
                    // Invalid admin credentials
                    JOptionPane.showMessageDialog(this, "Invalid admin username or password. Please try again.");
                }
            } else if(userType.equals("Student"))  {
                // Student login
                Student studentUser = (Student) clientHandler.loginUser(username, password);

                if (studentUser != null) {
                    // Valid student credentials
                    JOptionPane.showMessageDialog(this, "Student login successful!\nYou are logged in as: " + studentUser.getStudentNum());
                    dispose();
                    new StudentEnrollmentGUI(studentUser.getStudentNum());
                } else {
                    // Invalid student credentials
                    JOptionPane.showMessageDialog(this, "Invalid student username or password. Please try again.");
                }
            }
        } catch (Exception exception) {
            // Handle any other exceptions here
            System.out.println(exception.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during login: " + exception.getMessage());
        }
    } else if (e.getSource() == registrationButton) {
       
        // Close the login page
        dispose(); 
        // Open the RegistrationGUI
        new RegistrationGUI();
    } else if (e.getSource() == exitButton) {
        try {
            // Close the connection
            clientHandler.closeConnection();
        } catch (Exception exception) {
            // Handle exceptions related to closing the connection
            JOptionPane.showMessageDialog(this, "An error occurred while closing the connection: " + exception.getMessage());
        }
        System.exit(0);
    } else if (e.getSource() == adminRadioButton || e.getSource() == studentRadioButton) {
        if (adminRadioButton.isSelected()) {
            userLabel.setText("Emp Nr:");
        }
        if (studentRadioButton.isSelected()) {
            userLabel.setText("Student Nr:");
        }
    }
}}
