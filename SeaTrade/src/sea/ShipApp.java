package sea;

import java.io.*;
import java.net.*;

public class ShipApp {
    private Socket socket;
    private DataOutputStream toCompanyApp;
    private DataInputStream fromCompanyApp;

    public ShipApp(String companyAppAddress, int port) throws IOException {
        // Establish a connection to the CompanyApp
        this.socket = new Socket(companyAppAddress, port);
        this.toCompanyApp = new DataOutputStream(socket.getOutputStream());
        this.fromCompanyApp = new DataInputStream(socket.getInputStream());
    }

    public void connectToCompany(String shipName) throws IOException {
        // Protocol to connect the ship to the company
        toCompanyApp.writeUTF("CONNECT " + shipName);
        toCompanyApp.flush();
    }

    public void receiveAndExecuteCommands() throws IOException {
        // Listening loop for commands from the CompanyApp
        while (true) {
            String command = fromCompanyApp.readUTF();
            executeCommand(command);
        }
    }

    private void executeCommand(String command) {
        // Placeholder for command execution logic
        // This would be where you parse the command and take appropriate action
    }

    public void disconnectFromCompany() throws IOException {
        // Protocol to disconnect the ship from the company
        toCompanyApp.writeUTF("DISCONNECT");
        toCompanyApp.flush();
    }

    public void closeResources() throws IOException {
        // Close all resources
        toCompanyApp.close();
        fromCompanyApp.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            ShipApp shipApp = new ShipApp("localhost", 8151);
            shipApp.connectToCompany("MyShipName");

            // The ship app would remain in a listening state, executing commands as they come in
            shipApp.receiveAndExecuteCommands();

            // At some point, if the ship needs to disconnect
            shipApp.disconnectFromCompany();
            shipApp.closeResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
