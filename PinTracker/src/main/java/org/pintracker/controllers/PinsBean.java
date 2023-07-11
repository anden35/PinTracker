/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.controllers;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.pintracker.helperclasses.StoredProcedures;
import org.pintracker.models.PinsDO;
import org.pintracker.models.UserCollectionDO;

/**
 *
 * @author Adam
 */
@ManagedBean
@SessionScoped
public class PinsBean implements Serializable {

    private ArrayList<File> pins = new ArrayList<>();
    private StoredProcedures sp = new StoredProcedures();
    ArrayList<PinsDO> pinsList = new ArrayList<>();

    public PinsBean() {
        System.out.println("Making PinsBean");

    }

    @PostConstruct
    public void init() {
        System.out.println("Loading Pins Array");
        loadPinsArray();
    }

    public ArrayList<File> getPins() {
        return pins;
    }

    public void setPins(ArrayList<File> pins) {
        this.pins = pins;
    }

    public ArrayList<PinsDO> getPinsList() {
        return pinsList;
    }

    public void setPinsList(ArrayList<PinsDO> pinsList) {
        this.pinsList = pinsList;
    }

    public void filterPinsList(ArrayList<PinsDO> pins, String filter) {
        ArrayList<PinsDO> pinsCopy = new ArrayList<>(pins);

        for (PinsDO pin : pinsCopy) {
            if (pin.getImageName().contains(filter)) {
                pinsCopy.remove(pin);
            }
        }

    }

    public void loadPinsArray() {
        PinsDO pinsDO = new PinsDO();
        Collection<UserCollectionDO> userInv = sp.getUserInv(54);

        //ArrayList<PinsDO> pinsList = new ArrayList<>();
        URL resource = this.getClass().getClassLoader().getResource("../../resources/images/pinImages/");
       
//        File file = new File(resource.getPath());
//        System.out.println("FILE FOUND " + file.exists());
//        System.out.println("IS A FILE: " + file.isFile());

        //ArrayList<File> newPinlist = new ArrayList<>();
        Long i = 0L;
        Boolean disabled = false;

        Iterator a = userInv.iterator();

        while (a.hasNext()) {
            UserCollectionDO userColl = (UserCollectionDO) a.next();
            
           
            //public PinsDO(Integer pinID, String pinName, String pathName, String imageName, String description, Integer setID, Boolean disabled);
            String imageName = userColl.getPinID().toString() + "-1.jpg"; //Chance of bugs
            String path = ("../../resources/images/pinImages/");
            
            System.out.println(path + imageName);
             
            pinsList.add(new PinsDO( userColl.getPinID() , userColl.getPinName(),path,imageName, userColl.getPinDesc(), userColl.getSetId(), userColl.getSetName(), userColl.getWant()));
            pins.add(new File(path + imageName));
            
//            for (File f : file.listFiles()) {
//                if (f != null) {
//                    i++;
//                    System.out.println("I IS " + f);
//
//                    // this is a temp thing
//                    // TODO: change PinDO or change everything back to Long
//                    Long temp = Long.valueOf(userColl.getPinID());
//                    pinsList.add(new PinsDO( temp, f.getName(), f.getAbsolutePath(), f.getName(), f.getName(), Long.MIN_VALUE, userColl.getWant()));
//                    pins.add(new File(f.getAbsolutePath()));
//                }
//            }
            
        }
    }

}
