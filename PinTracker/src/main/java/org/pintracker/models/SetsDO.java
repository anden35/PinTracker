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
public class SetsDO {
    
// <editor-fold defaultstate="collapsed" desc="Properties">
    private Long setID;
    private String setName;
    private Long releaseYear;
    private Long totalPins;
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Constructors">
        public SetsDO(Long setID, String setName, Long releaseYear, Long totalPins) {
        this.setID = setID;
        this.setName = setName;
        this.releaseYear = releaseYear;
        this.totalPins = totalPins;
    }
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

    public Long getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Long getTotalPins() {
        return totalPins;
    }

    public void setTotalPins(Long totalPins) {
        this.totalPins = totalPins;
    }
// </editor-fold> 

}

    
   




