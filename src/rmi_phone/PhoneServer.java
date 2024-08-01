package rmi_phone;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PhoneServer {
    public static void main(String[] args) {
        try {
            PhoneInterface server = new PhoneDAO();
            Registry registry = LocateRegistry.createRegistry(7777);
            registry.bind("Server", server);
            System.err.println("Server is running ...");
        } catch (Exception e) {
            System.err.println("Exception: " + e);
            e.printStackTrace();
        }
    }
}
