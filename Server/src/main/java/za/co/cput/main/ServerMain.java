/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.co.cput.main;

import java.sql.SQLException;
import za.co.cput.database.DBConnection;
import za.co.cput.handlers.ServerHandler;

/**
 *
 * @author ruper
 */
public class ServerMain {

    public static void main(String[] args) throws SQLException {
        DBConnection dBConnection = new DBConnection();
        ServerHandler serverHandler = new ServerHandler();
        
    }
}
