/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.helperclasses;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pintracker.database.JNDIDataSource;
import org.pintracker.models.UsersDO;
import org.pintracker.models.UserCollectionDO;

/**
 * Helper class for calling pre-defined stored procedures in MySQL database.
 *
 * @author Adam
 */
public class StoredProcedures {

    // <editor-fold defaultstate="collapsed" desc="Properties">
    private JNDIDataSource ds;
    private final String INITCLASSMSG = "Creating a new StoredProcedures Class";
    private final String ISNULL = " is NULL";
    private final String ISEMPTY = " is Empty";
    private final String DBOUTPUTNULL = " The output parameter position was null for stored proc: ";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public StoredProcedures() {
        System.out.println(INITCLASSMSG);
        System.out.println("Really need to @Inject this ds if possible instead of creating a new instance each time.  Very database heavy operation.");
        ds = new JNDIDataSource();
    }

    public StoredProcedures(JNDIDataSource dataSource) {
        System.out.println(INITCLASSMSG);
        this.ds = dataSource;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Returns an Integer List representation of the user's Inventory.
     *
     * <br>Note - Uses JNDIDataSource created with the
     * StoredProcedures(JDNIDataSource ds) constructor.
     *
     * @param userID The user's unique id
     * @return userInv - An Integer List representation of the user's Inventory.
     * Empty list returned on failure.
     */
    public Collection<UserCollectionDO> getUserInv(Integer userID) {
        Collection<UserCollectionDO> userInv = new ArrayList();
        //Wont be able to look up user inventory on null userID, return an empty array
        if (userID == null) {
            logNullArg("userID");
            return userInv;
        }
        //Wont be able to connect to DataSource, return an empty array
        if (ds == null) {
            logNullObject(ds);
            return userInv;
        }

        try (CallableStatement cStmt = ds.getCon().prepareCall("{call getUserInv(?)}");) {
            cStmt.setInt(1, userID);
            ResultSet rs = cStmt.executeQuery();

            //If no resultset exists, return empty userInv.
            if (rs.wasNull()) {
                return userInv;
            }

            while (rs.next()) {
                boolean want = false;
                
                if (rs.getInt("Want") == 1) {
                    want = true;
                }     
                
                userInv.add(new UserCollectionDO(rs.getInt("PinId"), want ,rs.getString("PinName"),rs.getString("Description"),rs.getInt("SetID"),rs.getString("SetName")));
                

            }

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userInv;
    }

    /**
     * Register a new user.
     *
     * <br>Note - Uses JNDIDataSource created with the
     * StoredProcedures(JDNIDataSource ds) constructor.
     *
     * @param userName User's account name.
     * @param password User's password.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param dateOfBirth User's DOB.
     * @param emailAddress User's email address.
     * @return Boolean Successfully registered the user in the database
     */
    public Boolean registerNewUser(String userName, String password, String firstName, String lastName, String dateOfBirth, String emailAddress) {
        Boolean success = false;
        Integer rowCount = 0;

        if (userName == null) {
            return logNullArg("userName");
        } else if (password == null) {
            return logNullArg("password");
        } else if (firstName == null) {
            return logNullArg("firstName");
        } else if (lastName == null) {
            return logNullArg("lastName");
        } else if (dateOfBirth == null) {
            return logNullArg("dateOfBirth");
        } else if (emailAddress == null) {
            return logNullArg("emailAddress");
        }

        if (userName.isEmpty()) {
            return logEmptyArg("userName");
        } else if (password.isEmpty()) {
            return logEmptyArg("password");
        } else if (firstName.isEmpty()) {
            return logEmptyArg("firstName");
        } else if (lastName.isEmpty()) {
            return logEmptyArg("lastName");
        } else if (dateOfBirth.isEmpty()) {
            return logEmptyArg("dateOfBirth");
        } else if (emailAddress.isEmpty()) {
            return logEmptyArg("emailAddress");
        }

        try (CallableStatement cStmt = ds.getCon().prepareCall("{call registerNewUser(?,?,?,?,?,?)}");) {

            cStmt.setString(1, userName);
            cStmt.setString(2, password);
            cStmt.setString(3, firstName);
            cStmt.setString(4, lastName);
            cStmt.setString(5, dateOfBirth);
            cStmt.setString(6, emailAddress);
            rowCount = cStmt.executeUpdate();
            //Indicates successful addition of atleast one row to the database.
            if (rowCount > 0) {
                success = true;
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return success;
        }
    }

    /**
     * Get registered user's hashed password to validate on login attempt.
     *
     * @param userName
     * @return ArrayList<String> The hashed password(0) and birthyear(1) found
     * in the database for userName. If userName is not provided (null or
     * empty), an empty list is returned.
     */
    public ArrayList<String> getLoginInfo(String userName) {
        ArrayList<String> list = new ArrayList<>();
        //Only return the populated list if it is truly here.
        Boolean notNullParam = true;
        if (userName == null) {
            logNullArg("userName");
            return list;
        } else if (userName.isEmpty()) {
            logEmptyArg("userName");
            return list;
        }
        //trying out Optionals.. good luck!
        Optional<String> hashword = Optional.empty();
        Optional<String> birthYear = Optional.empty();
        try (CallableStatement cStmt = ds.getCon().prepareCall("{call getLoginInfo(?,?,?)}");) {

            cStmt.setString(1, userName);
            cStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            cStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

            cStmt.executeQuery();

            if (cStmt.getString(2) == null) {
                notNullParam = logNullDBOutput("getLoginInfo", 2);
            } else {
                hashword = Optional.of(cStmt.getString(2));
            }

            if (cStmt.getString(3) == null) {
                notNullParam = logNullDBOutput("getLoginInfo", 3);
            } else {
                birthYear = Optional.of(cStmt.getString(3));

            }
            //Only add the hashword and birthyear if output params from db aren't null.
            if (notNullParam) {
                hashword.ifPresent(list::add);
                birthYear.ifPresent(list::add);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return list;
        }

    }

    /**
     * Check if the Username supplied is already defined in the database.
     *
     * @param userName
     * @return Boolean Username availability. if userName is null or empty,
     * false is returned.
     */
    public Boolean checkUsername(String userName) {
        Boolean userNameAvail = false;

        if (userName == null) {
            return logNullArg("userName");
        } else if (userName.isEmpty()) {
            return logEmptyArg("userName");
        }

        try (CallableStatement cStmt = ds.getCon().prepareCall("{call checkUsername(?,?)}");) {

            cStmt.setString(1, userName);
            cStmt.registerOutParameter(2, java.sql.Types.BOOLEAN);

            cStmt.executeQuery();

            userNameAvail = cStmt.getBoolean(2);

        } catch (SQLException ex) {
            Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userNameAvail;
    }

    /**
     * Get Users information and store it in a UsersDO object.
     *
     * @param userName String
     * @return UsersDO currently logged in user information from the Users
     * table.
     */
    public UsersDO getUserInfo(String userName) {
        UsersDO user = new UsersDO();

        if (ds == null) {
            return user;
        }

        if (userName == null) {
            logNullArg("userName");
            return user;
        } else if (userName.isEmpty()) {
            logEmptyArg("userName");
            return user;
        }

        try (CallableStatement cStmt = ds.getCon().prepareCall("{call getUserInfo(?)}");) {

            cStmt.setString(1, userName);
            ResultSet rs = cStmt.executeQuery();

            //If no resultset exists, return empty userInv.
            if (rs.wasNull()) {
                return user;
            }

            while (rs.next()) {
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setPassword(rs.getString("Pw"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setDOB(rs.getDate("DOB"));
                user.setEmailAddress(rs.getString("Email"));
                user.setJoinDate(rs.getDate("JoinDate"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    //private methods
    /**
     * Logs which Class @code{name} was null when called.
     *
     * @param name
     * @return false
     */
    private Boolean logNullObject(Object name) {
        Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, name.getClass().getName().concat(ISNULL));
        return false;
    }

    /**
     * Logs which @code{arg} was NULL when called.
     *
     * @param arg
     * @return false
     */
    private Boolean logNullArg(String arg) {
        Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, arg.concat(ISNULL));
        return false;
    }

    /**
     * Logs which @code{arg} was EMPTY when called.
     *
     * @param arg
     * @return false
     */
    private Boolean logEmptyArg(String arg) {
        Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, arg.concat(ISEMPTY));
        return false;
    }

    /**
     *
     * @param num
     * @return false if the parameter index @{code num} is Null
     */
    private Boolean logNullDBOutput(String caller, Integer paramPos) {
        Logger.getLogger(StoredProcedures.class.getName()).log(Level.SEVERE, DBOUTPUTNULL.concat(caller).concat(" : ").concat(paramPos.toString()));
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Gets the StoredProcedures class ds Object
     *
     * @return JNDIDataSource Object
     */
    public JNDIDataSource getDs() {
        return ds;
    }

    /**
     * Sets the StoredProcedures class ds Object
     *
     * @param ds JNDIDataSource Object
     */
    public void setDs(JNDIDataSource ds) {
        this.ds = ds;
    }
    //</editor-fold>
}
