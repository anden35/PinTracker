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
public class PinsDO {
    
// <editor-fold defaultstate="collapsed" desc="Properties">
    private Integer pinID;
     private Integer setID;
    private String pinName;
    private String imageName;
    private String description;
    private String pathName;
   private String setName;
    
    private Boolean disabled;
    
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Constructors">
    public PinsDO(Integer pinID, String pinName, String pathName, String imageName, String description, Integer setID, String setName, Boolean disabled) {
        this.pinID = pinID;
        this.pinName = pinName;
        this.pathName = pathName;
        this.imageName = imageName;
        this.description = description;
        this.setID = setID;
        this.setName = setName;
        this.disabled = disabled;
    }

    public PinsDO() {
        System.out.println("Making new empty PinsDO");
    }
    public String getSetName() {
        return setName;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public void setSetName(String setName) {
        this.setName = setName;
    }

    public Integer getPinID() {
        return pinID;
    }

    public void setPinID(Integer pinID) {
        this.pinID = pinID;
    }

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSetID() {
        return setID;
    }

    public void setSetID(Integer setID) {
        this.setID = setID;
    }
    
        public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
    
    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    
// </editor-fold>



}
