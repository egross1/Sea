package sea;

import java.io.*;
import java.net.*;
import java.sql.*;

public class CompanyApp implements AutoCloseable {
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
        // Connection string without a password
        this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/seatrade", "root", "");
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
        // Placeholder for actual data processing logic
    }

    public void sendCommandToShip(String command) throws IOException {
        // Send commands to the ShipApp
        toServer.writeUTF("COMMAND " + command);
        toServer.flush();
        jbjbjh();
    }

    @Override
    public void close() throws IOException, SQLException {
        // Close all resources
        if (toServer != null) toServer.close();
        if (fromServer != null) fromServer.close();
        if (socket != null) socket.close();
        if (dbConnection != null) dbConnection.close();
    }

    public static void main(String[] args) {
        try (CompanyApp companyApp = new CompanyApp("localhost", 8150)) {
            companyApp.registerWithServer("MyCompanyName");
            // More logic to interact with the server and database
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
