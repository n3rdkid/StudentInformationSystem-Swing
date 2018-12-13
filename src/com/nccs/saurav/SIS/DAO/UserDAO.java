/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nccs.saurav.SIS.DAO;

import com.nccs.saurav.SIS.DTO.UserDTO;
import com.nccs.saurav.SIS.Database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserDAO {

    Connection con;
    Statement stmt;
    ResultSet rs;
    PreparedStatement pstmt;

    public UserDAO() {
        con = new ConnectionFactory().getConnection();
    }

    public void addUser(UserDTO user) {
        try {
            pstmt = con.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, user.getUserId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getFirstname());
            pstmt.setString(4, user.getMiddlename());
            pstmt.setString(5, user.getLastname());
            pstmt.setString(6, user.getPassword());
            pstmt.setString(7, user.getPermanentAddress());
            pstmt.setString(8, user.getTemporaryAddress());
            pstmt.setString(9, user.getContact());
            pstmt.setString(10, user.getEmail());
            int flag = pstmt.executeUpdate();
            flagCheck("Insert", flag);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void flagCheck(String operation, int flag) {
        if (flag > 0) {
            JOptionPane.showMessageDialog(null, operation + " Successful!");
        } else {
            JOptionPane.showMessageDialog(null, operation + " Failed!");
        }
    }

    public ResultSet getQueryResult() {
        String query = "SELECT userid,username,firstname,middlename,lastname,peraddress,temaddress,contact,email FROM user ";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public DefaultTableModel builtTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnName = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnName.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnName);
    }

    public UserDTO editUser(JTable table) {
        UserDTO user = new UserDTO();
        user.setUserId((Integer) table.getValueAt(table.getSelectedRow(), 0));
        user.setUsername((String) table.getValueAt(table.getSelectedRow(), 1));
        user.setFirstname((String) table.getValueAt(table.getSelectedRow(), 2));
        user.setMiddlename((String) table.getValueAt(table.getSelectedRow(), 3));
        user.setLastname((String) table.getValueAt(table.getSelectedRow(), 4));
        user.setPermanentAddress((String) table.getValueAt(table.getSelectedRow(), 5));
        user.setTemporaryAddress((String) table.getValueAt(table.getSelectedRow(), 6));
        user.setContact((String) table.getValueAt(table.getSelectedRow(), 7));
        user.setEmail((String) table.getValueAt(table.getSelectedRow(), 8));
        return user;
    }

    public void updateUser(UserDTO user) {
        String query = "UPDATE user SET firstname=?,middlename=?,lastname=?,peraddress=?,temaddress=?,contact=?,email=? WHERE userId=?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getMiddlename());
            pstmt.setString(3, user.getLastname());
            pstmt.setString(4, user.getPermanentAddress());
            pstmt.setString(5, user.getTemporaryAddress());
            pstmt.setString(6, user.getContact());
            pstmt.setString(7, user.getEmail());
            pstmt.setInt(8, user.getUserId());
            flagCheck("Update ", pstmt.executeUpdate());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(String value) {
        String query = "DELETE FROM user WHERE userid=? ";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(value));
            flagCheck("Delete ", pstmt.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Vector<String> getColumnNames(ResultSet rs) {
        Vector<String> columnName = new Vector<String>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnName.add(metaData.getColumnName(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnName;
    }

    public ResultSet searchUser(String name, String value) {
        String query;
        if (name.equalsIgnoreCase("userid")) {
            query = "SELECT userid,username,firstname,middlename,lastname,peraddress,temaddress,contact,email FROM user WHERE " + name + "=" + value;
        } else {
            query = "SELECT userid,username,firstname,middlename,lastname,peraddress,temaddress,contact,email FROM user WHERE " + name + "='" + value + "'";
        }
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Search not found!");
            } else {
                rs.beforeFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    boolean isOkay;

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        isOkay = false;
        String query = "UPDATE user SET password=? WHERE  username = ? AND password= ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.setString(3, oldPassword);
            if (pstmt.executeUpdate() > 0) {
                isOkay = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        
        }

        return isOkay;
    }

}
