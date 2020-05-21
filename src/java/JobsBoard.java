/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author brettexley
 */
@Named(value = "jobsBoard")
@RequestScoped
public class JobsBoard {
    //create Strings to manipulate items from webpage
    private String string1;
    private String string2;
    private String UserID;
    //connects to database and populates the job board
    public void doStuff() {
        try {
            //connection for database
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            //the string1 is the select statement for the database
            setString1("SELECT Users.USERNAME, PERFORMER.email, PERFORMER.phoneNUM, JOBS.JID, JOBS.pay, JOBS.location, "
                    + "JOBS.title, JOBS.role, JOBS.jdescription, JOBS.startDate, " 
                    + "JOBS.endDate from JOBS JOIN USERS ON JOBS.UserID = USERS.UserID JOIN PERFORMER ON USERS.UserID = PERFORMER.UserID;");
            //resultset contains all the information from the select statement
            ResultSet rs = st.executeQuery(getString1());
            //use a stringbuilder to show on the webpage
            StringBuilder sb = new StringBuilder();
            if (rs.next()) {
                rs.previous();
                //add what elements are appearing 
               
                while (rs.next()) {
                    sb.append("User Name: ");
                    sb.append(rs.getString(1));
                    sb.append(" ");
                    sb.append("Email: ");
                    sb.append(rs.getString(2));
                    sb.append(" ");
                    sb.append("Phone Number: ");
                    sb.append(rs.getString(3));
                    sb.append(" ");
                    sb.append("JID: ");
                    sb.append(rs.getString(4));
                    sb.append(" ");
                    sb.append("Pay: ");
                    sb.append(rs.getString(5));
                    sb.append(" ");
                    sb.append("Location: ");
                    sb.append(rs.getString(6));
                    sb.append("Title: ");
                    sb.append(rs.getString(7));
                    sb.append(" ");
                    sb.append("Role: ");
                    sb.append(rs.getString(8));
                    sb.append(" ");
                    sb.append("Description: ");
                    sb.append(rs.getString(9));
                    sb.append(" ");
                    sb.append("Start Date: ");
                    sb.append(rs.getString(10));
                    sb.append(" ");
                    sb.append("End Date: ");
                    sb.append(rs.getString(11));
                    sb.append(" ");
                    
                    
                    sb.append("\n");
                    sb.append("\n");
                }
            }
            setString2(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the string1
     */
    public String getString1() {
        return string1;
    }

    /**
     * @param string1 the string1 to set
     */
    public void setString1(String string1) {
        this.string1 = string1;
    }

    /**
     * @return the string2
     */
    public String getString2() {
        return string2;
    }

    /**
     * @param string2 the string2 to set
     */
    public void setString2(String string2) {
        this.string2 = string2;
    }
}
