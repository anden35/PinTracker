/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.helperclasses;

import java.util.ArrayList;
import org.pintracker.models.UsersDO;

/**
 *
 * @author Adam
 * @note Work in progress...
 */
public class AccountsRetriever {

    public AccountsRetriever() {
        JsonHandler data = new JsonHandler();
        UsersDO accounts;
    }
    
    private ArrayList<UsersDO> fetchAccounts(JsonHandler json, UsersDO accs){
        
        ArrayList<UsersDO> accounts = new ArrayList<UsersDO>();
        
        return accounts;
    }

//     this.accounts.addAll(new jsonHandler().fetchAccounts());
}
