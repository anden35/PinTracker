package org.pintracker.helperclasses;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

@FacesValidator("registration.usernameValidator")
public class UsernameValidator implements Validator, ClientValidator {
   
    private StoredProcedures sp;
    
    public UsernameValidator() {
        sp = new StoredProcedures();
        
    }

    

    private boolean  checkUsernameAvailable(String inUsername){
        Boolean userNameAvail = false;
        
        System.out.println("Checking if username is available for UsernameValidator");
        userNameAvail = sp.checkUsername(inUsername);
        System.out.println("And the answer is: " + userNameAvail + " for username " + inUsername);
        
        return userNameAvail;
        
    }
    
//Change return type to boolean and if value is null, return false so it can be used for primefaces components that use booleans
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException{
//        checkUsernameAvailable(value.toString()); //Comment out?  The call using the value is used below in the if statement and this line would fail with null pointer if left this way.
        if(value == null) {
            return;
        }
        
        if (!checkUsernameAvailable(value.toString())){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", 
                       "Username " +  "'" + value + "' is not available. Please pick a different username. "));
        }
    } 

    public Map<String, Object> getMetadata() {
        return null;
    }

    
    public String getValidatorId() {
        return "custom.usernameValidator";
    }

}