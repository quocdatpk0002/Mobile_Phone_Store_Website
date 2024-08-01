package rmi_phone;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLConnection extends UnicastRemoteObject {
    private static final String url = "jdbc:mysql://localhost:3306/manage_phone";
    private static final String user = "root";
    private static final String pw = "a@123456";

    protected MySQLConnection() throws RemoteException {
        super();
    }

    public Connection getConnection() throws RemoteException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("There is a request from client ...");
            return DriverManager.getConnection(url, user, pw);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String authenticateUser(String username, String password) throws RemoteException {
        Connection cn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            cn = this.getConnection(); 
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role"); 
            } else {
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

