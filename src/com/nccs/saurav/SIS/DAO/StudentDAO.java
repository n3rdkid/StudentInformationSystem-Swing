/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nccs.saurav.SIS.DAO;

import com.nccs.saurav.SIS.DTO.StudentDTO;
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

/**
 *
 * @author dipesh
 */
public class StudentDAO {

    Connection con;
    Statement stmt;
    ResultSet rs;
    PreparedStatement pstmt;

    public StudentDAO() {
        con = new ConnectionFactory().getConnection();
    }

    public void addStudent(StudentDTO student) {
        try {
            pstmt = con.prepareStatement("INSERT INTO student VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getMiddleName());
            pstmt.setString(4, student.getLastName());
            pstmt.setString(5, student.getGender());
            pstmt.setString(6, student.getPermanentAddress());
            pstmt.setString(7, student.getTemporaryAddress());
            pstmt.setString(8, student.getEmail());
            pstmt.setString(9, student.getMobile());
            pstmt.setString(10, student.getProgram());
            pstmt.setString(11, student.getSemester());
            pstmt.setString(12, student.getSection());
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
        String query = "SELECT * FROM student ";
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

    public void delete(String value) {
        String query = "DELETE FROM student WHERE studentId=? ";
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

    public void updateStudent(StudentDTO student) {
        String query = "UPDATE student SET firstname=?,middlename=?,lastname=?,permaddress=?,tempaddress=?,email=?,mobile=?,program=?,semester=?,gender=?,section=? WHERE studentId=?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getMiddleName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getPermanentAddress());
            pstmt.setString(5, student.getTemporaryAddress());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getMobile());
            pstmt.setString(8, student.getProgram());
            pstmt.setString(9, student.getSemester());
            pstmt.setString(10, student.getGender());
            pstmt.setString(11, student.getSection());
            pstmt.setInt(12, student.getStudentId());
            flagCheck("Update ", pstmt.executeUpdate());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StudentDTO editStudent(JTable table) {
        StudentDTO student = new StudentDTO();
        student.setStudentId((Integer) table.getValueAt(table.getSelectedRow(), 0));
        student.setFirstName((String) table.getValueAt(table.getSelectedRow(), 1));
        student.setMiddleName((String) table.getValueAt(table.getSelectedRow(), 2));
        student.setLastName((String) table.getValueAt(table.getSelectedRow(), 3));
        student.setGender((String) table.getValueAt(table.getSelectedRow(), 4));
        student.setPermanentAddress((String) table.getValueAt(table.getSelectedRow(), 5));
        student.setTemporaryAddress((String) table.getValueAt(table.getSelectedRow(), 6));
        student.setEmail((String) table.getValueAt(table.getSelectedRow(), 7));
        student.setMobile((String) table.getValueAt(table.getSelectedRow(), 8));
        student.setProgram((String) table.getValueAt(table.getSelectedRow(), 9));
        student.setSemester((String) table.getValueAt(table.getSelectedRow(), 10));
        student.setSection((String) table.getValueAt(table.getSelectedRow(), 11));
        return student;
    }

    public ResultSet searchStudent(String name, String value) {
        String query;
        if (name.equalsIgnoreCase("studentId")) {
            query = "SELECT * FROM student WHERE " + name + "=" + value;
        } else {
            query = "SELECT * FROM student WHERE " + name + "='" + value + "'";
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
}

