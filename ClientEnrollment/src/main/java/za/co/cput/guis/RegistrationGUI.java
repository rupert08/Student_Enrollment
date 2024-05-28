package za.co.cput.guis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import za.co.cput.connections.ClientHandler;
import za.co.cput.workers.User;

public class RegistrationGUI extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextField surnameField;
    private JRadioButton adminRadioButton;
    private JRadioButton studentRadioButton;
    private JButton registerButton;
    private JButton loginButton;
    private JButton exitButton;
    private ClientHandler clientHandler;
    
    public RegistrationGUI() {
        
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);
        panel.setBackground(Color.lightGray);

        JLabel headingLabel = new JLabel("User Registration");
        headingLabel.setBounds(10, 10, 320, 25);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(headingLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 40, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 40, 200, 25);
        panel.add(nameField);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setBounds(10, 70, 80, 25);
        panel.add(surnameLabel);

        surnameField = new JTextField(20);
        surnameField.setBounds(100, 70, 200, 25);
        panel.add(surnameField);

        adminRadioButton = new JRadioButton("Admin");
        adminRadioButton.setBackground(Color.lightGray);
       
        adminRadioButton.setBounds(10, 100, 100, 25);
        panel.add(adminRadioButton);

        studentRadioButton = new JRadioButton("Student");
        studentRadioButton.setBackground(Color.lightGray);
       
        studentRadioButton.setBounds(120, 100, 100, 25);
        panel.add(studentRadioButton);

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadioButton);
        roleGroup.add(studentRadioButton);

        registerButton = new JButton("Register");
        registerButton.setBackground(Color.gray);
        registerButton.setForeground(Color.white);
        registerButton.setBounds(10, 130, 100, 25);
        panel.add(registerButton);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.GRAY);
        loginButton.setForeground(Color.white);
        loginButton.setBounds(120, 130, 100, 25);
        panel.add(loginButton);

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.gray);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(230, 130, 100, 25);
        panel.add(exitButton);

        // Add action listeners
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        
    this.clientHandler = ClientHandler.getInstance(); // Initialize the ClientHandler
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String role = adminRadioButton.isSelected() ? "Admin" : "Student";

            if (!name.isEmpty() && !surname.isEmpty()) {
                // Create a User object with the provided information
                User user = new User(name, surname, role);

                // Use the clientHandler to register the user
                boolean registrationResult = clientHandler.registerUser(user);

                if (registrationResult) {
                    JOptionPane.showMessageDialog(null, "Registration successful!"+user.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Name and Surname are required fields.");
            }
        } else if (e.getSource() == loginButton) {
            // Implement your login logic here by opening the LoginGUI and passing control
            dispose(); // Close the registration page
           new LoginGUI().setSize(400, 250); // Pass the clientHandler to the LoginGUI
           
        } else if (e.getSource() == exitButton) {
            // Close the application
            clientHandler.closeConnection();
            System.exit(0);
        }
    }
}
