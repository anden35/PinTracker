/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pintracker.helperclasses;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author 
 */
public class Hasher {
    public String cryptoHash(String inText) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance( "SHA-256" );
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }
        String text = inText;

        // Change this to UTF-16 if needed
        md.update( text.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();

        String hex = String.format( "%064x", new BigInteger( 1, digest ) );
        return hex ;
  }
    
    public String tearsOfMyEnemies(String username, String year) {
        
        String letterOne;
        String letterTwo;
        String salt;
        String lastTwoNumbers;
        
        Integer lengthOfUsername;
        
        
        
        lengthOfUsername = username.length();
                
        letterOne = username.substring(0, 1);
        letterTwo = username.substring(lengthOfUsername-1, lengthOfUsername);
        
        lastTwoNumbers = year.substring(8, 10);
        
        salt = letterOne + lastTwoNumbers + letterTwo;
        return salt;
    }
}



   
