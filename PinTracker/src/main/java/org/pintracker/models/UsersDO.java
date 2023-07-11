/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Adam
 */
public class UsersDO implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Properties">
    private Long userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date DOB;
    private String emailAddress;
    private Date joinDate;
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructors">
    public UsersDO(Long userID, String username, String password, String firstName, String lastName, Date DOB, String emailAddress, Date joinDate) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.emailAddress = emailAddress;
        this.joinDate = joinDate;
    }
    
        public UsersDO() {
            System.out.println("Creating NULL UsersDO");
        this.userID = null;
        this.username = null;
        this.password = null;
        this.firstName = null;
        this.lastName = null;
        this.DOB = null;
        this.emailAddress = null;
        this.joinDate = null;
    }

    // </editor-fold>
}
