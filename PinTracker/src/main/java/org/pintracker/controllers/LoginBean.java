package org.pintracker.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.pintracker.database.JNDIDataSource;
import org.pintracker.helperclasses.Hasher;
import org.pintracker.helperclasses.SessionUtils;
import org.pintracker.helperclasses.StoredProcedures;
import org.pintracker.models.UsersDO;

@ManagedBean
@SessionScoped

public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String pwd;
    private String msg;
    private String user;
    private final String LOGINSUCCESS = "Logged in Successfully";
    private final String LOGINFAIL = "Login failed.. Bad Username or Password";
    private HashMap<String, UsersDO> logins = new HashMap<>();
    private UsersDO person;
    private JNDIDataSource ds;

    public LoginBean() {
        ds = new JNDIDataSource();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UsersDO getPerson() {
        return person;
    }

    public void setPerson(UsersDO person) {
        this.person = person;
    }
    
    public boolean validate(String username, String password) {

        boolean validPassword = false;
        String newHashedPW;
        String hashFromDB;
        String salt;
        String birthYear;
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, String> hash = new HashMap<>();
        Hasher hashish = new Hasher();
        StoredProcedures sp = new StoredProcedures(this.ds);
        System.out.println("Username Available: " + sp.checkUsername(username));

        list.addAll(sp.getLoginInfo(username));
        //List needs to be 2 elements in size for both 1st(0) and 2nd(1) element.
        if (list.size() > 1) {
            hash.put("HASHWORD", list.get(1));
            hash.put("BIRTHYEAR", list.get(0));
        }
        System.out.println("size of list " + list.size());

        hashFromDB = hash.get("HASHWORD");
        birthYear = hash.get("BIRTHYEAR");

        salt = hashish.tearsOfMyEnemies(username, birthYear);
        newHashedPW = hashish.cryptoHash(password + salt);

        if (hashFromDB.compareTo(newHashedPW) == 0) {
            System.out.println("Password Hashes match.");
            validPassword = true;
        }

        System.out.println("hashFromDB :" + hashFromDB);
        System.out.println("newHashedPW:" + newHashedPW);
        System.out.println("salt " + salt);
        System.out.println("birthYear " + birthYear);

        return validPassword;
    }

    //validate login
    public String validateUsernamePassword() {
        System.out.println("user:pwd" + user + " : " + pwd);
        boolean valid = this.validate(user, pwd);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            System.out.println(LOGINSUCCESS);
            person = getUserInfo(user);
            return "success";
        } else {
            System.out.println("in else");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, LOGINFAIL,
                            "Please enter correct username and Password"));
            System.out.println(LOGINFAIL);
            return "logout";
        }
    }
    
    public UsersDO getUserInfo(String userName){
        StoredProcedures sp = new StoredProcedures(this.ds);
        return sp.getUserInfo(userName);
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "logout";
    }
}
