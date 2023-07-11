/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.pintracker.helperclasses.Hasher;
import org.pintracker.helperclasses.StoredProcedures;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class RegisterUserBean implements Serializable {

    private String username;
    private String fristName;
    private String lastName;
    private String dob;
    private String email;
    private String pw;
    private String pwHash;

    private Boolean userNameAvail = false;

    private UIComponent component;
    private UIComponent registeredGrowlComponent;

    private StoredProcedures sp;

    public RegisterUserBean() {
        sp = new StoredProcedures();
    }

    public void printVars(ActionEvent actionEvent) {

        setPwHash();

        System.out.println(username);
        System.out.println(fristName);
        System.out.println(lastName);
        System.out.println(dob);
        System.out.println(email);
        System.out.println(pw);
        System.out.println(pwHash);
    }

    public void registerUser(ActionEvent actionEvent) {
        setPwHash();
        sendUserInfoToDb();
    }

// <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        try {
            Date date = new SimpleDateFormat("mm/dd/yyyy").parse(dob);
            String formatDate = new SimpleDateFormat("yyyy/mm/dd").format(date);
            this.dob = formatDate;
        } catch (ParseException e) {
            //I am going to get yell at for not using this..... 
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public Boolean getUserNameAvail() {
        return userNameAvail;
    }

    public void setUserNameAvail(Boolean userNameAvail) {
        this.userNameAvail = userNameAvail;
    }

    public UIComponent getRegisteredGrowlComponent() {
        return registeredGrowlComponent;
    }

    public void setRegisteredGrowlComponent(UIComponent registeredGrowlComponent) {
        this.registeredGrowlComponent = registeredGrowlComponent;
    }

    //</editor-fold>
    private void setPwHash() {
        String salt;

        Hasher hs = new Hasher();

        salt = hs.tearsOfMyEnemies(username, dob);

        pwHash = hs.cryptoHash(pw + salt);
    }

    public void checkUsernameAvailable() {

        System.out.println("Checking if username is available");
        userNameAvail = sp.checkUsername(username);

        if (!userNameAvail) {
            //send msg to page saying try again
            System.out.println("PICK A NEW FUCKING NAME. " + username);
            FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null));
        }

    }

    private void sendUserInfoToDb() {
        Boolean result;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        result = sp.registerNewUser(username, pwHash, fristName, lastName, dob, email);
        if (result) {

            ExternalContext externalContext = facesContext.getExternalContext();

            System.out.println("I was Successful in registrating a new user with the following info:" + username + "-" + pwHash + "-" + fristName + "-" + lastName + "-" + dob + "-" + email);
            FacesMessage message = new FacesMessage("Registration Successful");

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(registeredGrowlComponent.getClientId(context), message);

            externalContext.getFlash().setKeepMessages(true);
            try {
                externalContext.redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(RegisterUserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesMessage message = new FacesMessage("Failed to registrator");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(registeredGrowlComponent.getClientId(context), message);
            System.out.println("Was I able to use the info to the DB? " + result);
        }

    }

    @PostConstruct
    public void init() {
        System.out.println("Making RegisterUserBean");
    }
}
