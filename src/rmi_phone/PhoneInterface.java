package rmi_phone;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PhoneInterface extends Remote {
    Phone find(String id) throws RemoteException;
    int insert(Phone phone) throws RemoteException;
    int delete(String id) throws RemoteException;
    int update(Phone phone) throws RemoteException;
    List<Phone> getAllRecords() throws RemoteException;
    String authenticateUser(String username, String password) throws RemoteException;
    boolean registerUser(String username, String password, String role) throws RemoteException;
}
