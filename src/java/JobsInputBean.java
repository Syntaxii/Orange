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
@Named(value = "jobsInputBean")
@RequestScoped
public class JobsInputBean {
     //create Strings to manipulate items from webpage
    private String UserID;
    private String JID;
    private String String1;
    private String String2;
    private String password;
    private String pay;
    private String location;
    private String title;
    private String role;
    private String startingDate;
    private String endingDate;
    private String JDescription;
    private String UserName;

    public void doStuff() {
        int count = 0;
        setString2("started");
        //check to see if username and password are null
        if ((getUserName() != null) && (getPassword() != null)) {
            try {
                //database connection
                Class.forName("oracle.jdbc.driver.OracleDriver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                //find UserID given UserName and password
                setString1("Select USERS.UserID from USERS JOIN METAINFID ON USERS.UserID = METAINFID.UserID "
                            + "JOIN METAINFO ON METAINFID.metainfID = METAINFO.metainfID "
                            + "where USERS.userName = \'" + getUserName() + "\' and METAINFO.password = \'" + getPassword() + "\'");
                    setString2(getString1());
                    ResultSet rs = st.executeQuery(getString1());
                    StringBuilder sb = new StringBuilder();
                    if (rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            setUserID(rs.getString(1));
                        }
                    }
                //make sure UserID isnt null
                if(UserID != null){
                //make sure JID is the largest JID
                setString1("SELECT JID from JOBS;");
                setString2(getString1());
                rs = st.executeQuery(getString1());
                if (rs.next()) {
                    rs.previous();
                    while (rs.next()) {
                        sb.append(rs.getString(1));
                        count++;
                    }
                    setString2(Integer.toString(count));
                } else {
                    setString2("try again");
                }
                count++;
                setString2(Integer.toString(count));
                setJID(Integer.toString(count));
                setString2("got here");
                //insert a new job with all values from webpage
                setString1("INSERT INTO JOBS VALUES (\'" + getUserID() + "\',\'" + getJID() + "\',\'"
                        + getPay() + "\',\'" + getLocation() + "\',\'"
                        + getTitle() + "\',\'" + getRole() + "\',\'" + getJDescription() + "\',\'" 
                        + getStartingDate() + "\',\'" + getEndingDate() + "\');");
                setString2(getString1());
                st.executeUpdate(getString1());
                setString2("successful");
                }else{
                    setString2("wrong username or password, please try again");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                

            }

        }

    }

    /**
     * @return the UserID
     */
    public String getUserID() {
        return UserID;
    }

    /**
     * @param UserID the UserID to set
     */
    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    /**
     * @return the String1
     */
    public String getString1() {
        return String1;
    }

    /**
     * @param String1 the String1 to set
     */
    public void setString1(String String1) {
        this.String1 = String1;
    }

    /**
     * @return the String2
     */
    public String getString2() {
        return String2;
    }

    /**
     * @param String2 the String2 to set
     */
    public void setString2(String String2) {
        this.String2 = String2;
    }

    /**
     * @return the JID
     */
    public String getJID() {
        return JID;
    }

    /**
     * @param JID the JID to set
     */
    public void setJID(String JID) {
        this.JID = JID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the pay
     */
    public String getPay() {
        return pay;
    }

    /**
     * @param pay the pay to set
     */
    public void setPay(String pay) {
        this.pay = pay;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the JDescription
     */
    public String getJDescription() {
        return JDescription;
    }

    /**
     * @param JDescription the JDescription to set
     */
    public void setJDescription(String JDescription) {
        this.JDescription = JDescription;
    }

    /**
     * @return the startingDate
     */
    public String getStartingDate() {
        return startingDate;
    }

    /**
     * @param startingDate the startingDate to set
     */
    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * @return the endingDate
     */
    public String getEndingDate() {
        return endingDate;
    }

    /**
     * @param endingDate the endingDate to set
     */
    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    /**
     * @return the UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param UserName the UserName to set
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

}
