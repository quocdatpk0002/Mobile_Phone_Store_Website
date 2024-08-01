package rmi_phone;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO extends UnicastRemoteObject implements PhoneInterface {

    private MySQLConnection connection;

    protected PhoneDAO() throws RemoteException {
        super();
        connection = new MySQLConnection();
    }

    @Override
    public Phone find(String id) throws RemoteException {
        Connection cn = connection.getConnection(); 
        if (cn == null) return null;
        try {
            String query = "SELECT * FROM phone WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(query); 
            ps.setString(1, id); 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Phone phone = new Phone(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("basicInfo"),
                        rs.getDouble("price")
                );
                return phone;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int insert(Phone phone) throws RemoteException {
        Connection cn = connection.getConnection();
        if (cn == null) return -1;
        try {
            String query = "INSERT INTO phone (id, name, brand, basicInfo, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, phone.getId());
            ps.setString(2, phone.getName());
            ps.setString(3, phone.getBrand());
            ps.setString(4, phone.getBasicInfo());
            ps.setDouble(5, phone.getPrice());
            int affectedRows = ps.executeUpdate(); 
            if (affectedRows == 0) {
                throw new SQLException("Creating phone failed, no rows affected.");
            }
            return 1; 
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int delete(String id) throws RemoteException {
        Connection cn = connection.getConnection();
        if (cn == null) return -1;
        try {
            String query = "DELETE FROM phone WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int update(Phone phone) throws RemoteException {
        Connection cn = connection.getConnection();
        if (cn == null) return -1;
        try {
            String query = "UPDATE phone SET name = ?, brand = ?, basicInfo = ?, price = ? WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, phone.getName());
            ps.setString(2, phone.getBrand());
            ps.setString(3, phone.getBasicInfo());
            ps.setDouble(4, phone.getPrice());
            ps.setString(5, phone.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Phone> getAllRecords() throws RemoteException {
        List<Phone> phones = new ArrayList<>();
        Connection cn = connection.getConnection();
        if (cn == null) return phones; 
        try {
            String query = "SELECT * FROM phone";
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) { 			  
                Phone phone = new Phone(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("basicInfo"),
                        rs.getDouble("price")
                );
                phones.add(phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return phones;
    }
    @Override
    public String authenticateUser(String username, String password) throws RemoteException {
        Connection cn = connection.getConnection();
        if (cn == null) return null;
        try {
            String query = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public boolean registerUser(String username, String password, String role) throws RemoteException {
        Connection cn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            cn = this.connection.getConnection(); 
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            int affectedRows = pstmt.executeUpdate();
            result = affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error while registering user: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

