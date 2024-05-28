/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.co.cput.mains;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.apache.derby.impl.tools.sysinfo.Main;
import za.co.cput.connections.ClientHandler;
import za.co.cput.guis.AdminGUI;
import za.co.cput.guis.LoginGUI;
import za.co.cput.guis.RegistrationGUI;
import za.co.cput.guis.StudentEnrollmentGUI;
import za.co.cput.workers.Student;


/**
 *
 * @author ruper
 */
public class ClientMain {
     private static Student student;
    public static void main(String[] args) {
        
//        AdminGUI admin = new AdminGUI();
//        admin.setSize(800, 600);
    //      StudentEnrollmentGUI studentEnrollmentGUI = new StudentEnrollmentGUI("");   
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
        loginGUI.setSize(400,400);
        loginGUI.setDefaultCloseOperation(EXIT_ON_CLOSE);
     
        
    }
}
