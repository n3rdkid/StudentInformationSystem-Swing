/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nccs.saurav.SIS.main;
//Data Acess Object DAO
//Data Transfer Object DTO --- Models

import com.nccs.saurav.SIS.GUI.LoginDialog;

/**
 *
 * @author Saurav Adhikari
 */
public class StudentInformationSystem {
    public static void main(String[] args) {
        LoginDialog ld=new LoginDialog();
        ld.setTitle("User Login");
        ld.setLocationRelativeTo(null);
        ld.setVisible(true);
    }  
}
