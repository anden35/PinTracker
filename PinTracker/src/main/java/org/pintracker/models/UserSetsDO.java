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
public class UserSetsDO implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Properties">
    private Long setID;
    private String setName;
    private Integer releaseYear;
    private Integer totalPins;
// </editor-fold>    
    
// <editor-fold defaultstate="collapsed" desc="Getters and Setters">
     public Long getSetID() {
        return setID;
    }

    public void setSetID(Long setID) {
        this.setID = setID;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getTotalPins() {
        return totalPins;
    }

    public void setTotalPins(Integer totalPins) {
        this.totalPins = totalPins;
    }
// </editor-fold>    

// <editor-fold defaultstate="collapsed" desc="Constructors">
    public UserSetsDO(Long setID, String setName, Integer releaseYear, Integer totalPins) {
        this.setID = setID;
        this.setName = setName;
        this.releaseYear = releaseYear;
        this.totalPins = totalPins;
    }
// </editor-fold>

}
