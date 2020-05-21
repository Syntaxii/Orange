/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author brettexley
 */
@Named(value = "logInUpdateBean")
@RequestScoped
public class LogInUpdateBean {

    /**
     * Creates a new instance of LogInUpdate
     */
    public LogInUpdateBean() {
    }
  //create Strings to manipulate items from webpage
    private String UserID;
    private String Password;
    private String newPassword;
    private String String1;
    private String string2;
    private String string3;
    private String userName;
    private String metainfID;
    private String prevpassID;
    private String date;
   
    /**
     * Creates a new instance of LogInBean
     */
    public void getResponse() {
        //make sure password isnt null
        if (getPassword() != null) {
            try {
                //database connection
                Class.forName("oracle.jdbc.driver.OracleDriver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                //string 1 is query 
                setString1("Select USERS.UserID from USERS JOIN METAINFID ON USERS.UserID = METAINFID.UserID "
                        + "JOIN METAINFO ON METAINFID.metainfID = METAINFO.metainfID "
                        + "where USERS.userName = \'" + getUserName() + "\' and METAINFO.password = \'" + getPassword() + "\';");
                //result set holds UserID 
                ResultSet rs = st.executeQuery(getString1());
                //set UserID
                StringBuilder sb = new StringBuilder();
                if (rs.next()) {
                    rs.previous();
                    while (rs.next()) {
                        setString3(rs.getString(1));
                        setUserID(rs.getString(1));
                    }

                } else {
                    setString2("wrong password or username, please try again");
                }
                //set string1 to metainfID
                if (UserID != null) {
                    setString1("SELECT metainfID FROM METAINFID JOIN USERS on USERS.UserID = METAINFID.UserID "
                            + "WHERE User.UserID = \'" + getUserID() + "\';");
                    rs = st.executeQuery(getString1());
                    if (rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            //set metainfid to the select statement above
                            setMetainfID(rs.getString(1));
                        }
                    }
                }
                //make sure metainfid is populated
                if (getMetainfID() != null) {
                    //set password to the new password
                    setString1("UPDATE METAINFO SET password = \'" + getNewPassword() + "\' "
                            + "WHERE METAINFID = \'" + getMetainfID() + "\';");
                    st.executeUpdate(getString1());
                    //get prevpassID where metainfid exists
                    setString1("SELECT PREVPASSID FROM PASSWORDHISTORY "
                            + "WHERE METAINFID = \'" + getMetainfID() + "\';");
                    st.executeQuery(getString1());
                    //set prevpassid from the query above
                    rs = st.executeQuery(getString1());
                    if (rs.next()) {
                        rs.previous();
                        while(rs.next()){
                        setPrevpassID(rs.getString(1));
                        }
                    }
                }
                //make sure prevpassid is not null
                if (prevpassID != null) {
                    //create a date and populate with current date
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.now();
                    setDate(dtf.format(localDate));
                    //update previous password with old password
                    setString1("UPDATE PREVPASS SET prevpass = \'" + getPassword()
                            + "\', \'" + getDate() + "\' WHERE prevpassID = \'" + getPrevpassID() + "\';");
                    st.executeUpdate(getString1());
                    setString2("successful");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                setString2("connection unsuccessful \n" + getString1());
            }

        } else {
            setString2("");
        }
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
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the metainfID
     */
    public String getMetainfID() {
        return metainfID;
    }

    /**
     * @param metainfID the metainfID to set
     */
    public void setMetainfID(String metainfID) {
        this.metainfID = metainfID;
    }

    /**
     * @return the prevpassID
     */
    public String getPrevpassID() {
        return prevpassID;
    }

    /**
     * @param prevpassID the prevpassID to set
     */
    public void setPrevpassID(String prevpassID) {
        this.prevpassID = prevpassID;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the string3
     */
    public String getString3() {
        return string3;
    }

    /**
     * @param string3 the string3 to set
     */
    public void setString3(String string3) {
        this.string3 = string3;
    }

}
