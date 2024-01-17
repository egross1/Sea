package sea;

import java.io.*;
import java.net.*;
import java.sql.*;

public class CompanyApp {
    private Socket socket;
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private Connection dbConnection;

    public CompanyApp(String serverAddress, int port) throws IOException, ClassNotFoundException, SQLException {
        // Establish a connection to the SeaTrade server
        this.socket = new Socket(serverAddress, port);
        this.toServer = new DataOutputStream(socket.getOutputStream());
        this.fromServer = new DataInputStream(socket.getInputStream());

        // Establish a connection to the database
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/seatrade", "user", "password");
    }

    public void registerWithServer(String companyName) throws IOException {
        // Protocol to register the company with the server
        toServer.writeUTF("REGISTER " + companyName);
        toServer.flush();
    }

    public void updateData() throws IOException, SQLException {
        // Fetch new data from server and update local database
        String data = fromServer.readUTF();
        // Assuming data is in JSON format and needs to be parsed and inserted into the database
        // This is a placeholder for the actual data processing logic
    }

    public void sendCommandToShip(String command) throws IOException {
        // Send commands to the ShipApp
        toServer.writeUTF("COMMAND " + command);
        toServer.flush();
    }

   
    public void closeResources() throws IOException, SQLException {
        // Close all resources
        toServer.close();
        fromServer.close();
        socket.close();
        dbConnection.close();
    }

    // Additional methods for business logic can be added here
    // For example, handling user registration, managing ship data, etc.

    public static void main(String[] args) {
        try {
            CompanyApp companyApp = new CompanyApp("localhost", 8150);
            companyApp.registerWithServer("MyCompanyName");
            
            // More logic to interact with the server and database
            
            companyApp.closeResources();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
