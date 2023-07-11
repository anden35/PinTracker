/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.models;

/**
 *
 * @author Adam
 */
public class UserCollectionDO {

// <editor-fold defaultstate="collapsed" desc="Properties">
    private Long lindID;
    private Long userID;
    private Integer pinID;
    private boolean want;
    private String pinName;
    private String pinDesc;
    private Integer setId;
    private String setName;
    

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructors">
    public UserCollectionDO(Integer pinID, boolean want, String pinName, String pinDesc, Integer setId, String setName) {
        this.pinID = pinID;
        this.want = want;
        this.pinName = pinName;
        this.pinDesc = pinDesc;
        this.setId = setId;
        this.setName = setName;
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }

    public String getPinDesc() {
        return pinDesc;
    }

    public void setPinDesc(String pinDesc) {
        this.pinDesc = pinDesc;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }


    public void setSetName(String setName) {
        this.setName = setName;
    }

    public Long getlindID() {
        return lindID;
    }

    public void setlindID(Long lindID) {
        this.lindID = lindID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getPinID() {
        return pinID;
    }

    public void setPinID(Integer pinID) {
        this.pinID = pinID;
    }

    public boolean getWant() {
        return want;
    }

    public void setWant(boolean want) {
        this.want = want;
    }
// </editor-fold>

}
