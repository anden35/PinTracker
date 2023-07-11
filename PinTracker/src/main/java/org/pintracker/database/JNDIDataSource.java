



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * This class connects to a JNDI data source.
 * <p>
 * Implements AutoClosable.
 *
 * @author Adam
 */
public class JNDIDataSource implements AutoCloseable, Serializable {

    Context ctx = null;
    Connection con = null;
    DataSource ds = null;
    Statement stmt = null;
    ResultSet rs = null;
    String ctxLookup = "java:/comp/env/jdbc/PinApp";

    public JNDIDataSource() {
        this.getConnection();
    }

    public JNDIDataSource(String context) {
        this.ctxLookup = context;
        this.getConnection();
    }

    /**
     * Opens the connection to the database specificed in ctxLookup matching the container's server.xml resource name.
     * 
     * @return con The connection object obtained.
     */
    protected Connection getConnection() {

        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(ctxLookup);
            con = ds.getConnection();
            stmt = con.createStatement();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    /**
     * Call to get a connection from the database.
     * 
     * @return con The connection object.
     */
    public Connection getCon() {
        System.out.println("con is " + con);
        return con;
    }

    @Override
    public void close() throws Exception {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(JNDIDataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException ex) {
                Logger.getLogger(JNDIDataSource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String toString() {
        String string = new String();
        if (ctx != null) {
            string = ctxLookup;
        } else {
            string = "null";
        }
        return string;
    }

}
