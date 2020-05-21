/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.inject.Named;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Derek
 */
@ApplicationScoped
@Named(value = "logInBean")

public class LogInBean implements Serializable {
      //create Strings to manipulate items from webpage
    private String UserID;
    private String Password;
    private String legalFName;
    private String newlegalFname;
    private String legalLName;
    private String newLegalLName;
    private String String1;
    private String String2;
    private String userName;
    private String newUserName;
    private String stageFirstName;
    private String newStageFirstName;
    private String stageLastName;
    private String newStageLastName;
    private String email;
    private String newEmail;
    private String phoneNum;
    private String newPhoneNum;
    private String location;
    private String newLocation;
    private String bio;
    private String newBio;
    private String dayFree;
    private String hourFree;
    private String newDayFree;
    private String newHourFree;
    private String string3;

    public void getResponse() {
        //check to see if username and password are null or not 
        if ((getUserName() != null) && (getPassword() != null)) {
            try {
                //connect to database
                Class.forName("oracle.jdbc.driver.OracleDriver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                //string 1 is the query to the database
                setString1("Select legalFirstName, legalLastName from USERS JOIN METAINFID ON USERS.UserID = METAINFID.UserID "
                        + "JOIN METAINFO ON METAINFID.metainfID = METAINFO.metainfID "
                        + "where USERS.userName = \'" + getUserName() + "\' and METAINFO.password = \'" + getPassword() + "\'");
                //string 2 is used a debugging string
                setString2(getString1());
                //result set is used to house query answers
                ResultSet rs = st.executeQuery(getString1());
                //if resultset holds items, i.e. query actually returned values continues forward
                if (rs.next()) {
                    rs.previous();
                    //set legal name from the resultset
                    while (rs.next()) {
                        setLegalFName(rs.getString(1));
                        setLegalLName(rs.getString(2));

                    }
                    //query based on the UserID from above
                    setString1("Select USERS.UserID from USERS JOIN METAINFID ON USERS.UserID = METAINFID.UserID "
                            + "JOIN METAINFO ON METAINFID.metainfID = METAINFO.metainfID "
                            + "where USERS.userName = \'" + getUserName() + "\' and METAINFO.password = \'" + getPassword() + "\'");
                    setString2(getString1());
                    rs = st.executeQuery(getString1());

                    if (rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            setUserID(rs.getString(1));
                        }
                    }
                    //query based on UserID from above
                    setString1("SELECT stageFirstName, stageLastName, email, phoneNUM, location, bio "
                            + "FROM Performer WHERE UserID = \'" + getUserID() + "\';");
                    setString2(getString1());
                    rs = st.executeQuery(getString1());
                    if (rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            setStageFirstName(rs.getString(1));
                            setStageLastName(rs.getString(2));
                            setEmail(rs.getString(3));
                            setPhoneNum(rs.getString(4));
                            setLocation(rs.getString(5));
                            setBio(rs.getString(6));
                        }
                    }
                    //query based on UserID from above
                    setString1("SELECT dayFree, hourFree "
                            + "FROM SCHEDULE "
                            + "WHERE UserID = \'" + getUserID() + "\';");
                    st.executeQuery(getString1());
                    rs = st.executeQuery(getString1());
                    if (rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            setDayFree(rs.getString(1));
                            setHourFree(rs.getString(1));
                        }
                    }
                    //query based on UserID from above 
                    setString1("SELECT HISTORY.role, EVENT.location, EVENT.title "
                            + "FROM EVENT JOIN HISTORY ON EVENT.EID = HISTORY.EID "
                            + "WHERE HISTORY.UserID = \'" + getUserID() + "\';");
                    rs = st.executeQuery(getString1());
                    StringBuilder sb = new StringBuilder();
                    if (rs.next()) {
                        rs.previous();
                        while (rs.next()) {
                            sb.append("Role:");
                            sb.append(rs.getString(1));
                            sb.append(" ");
                            sb.append("Event Location:");
                            sb.append(rs.getString(2));
                            sb.append(" ");
                            sb.append("Event Title:");
                            sb.append(rs.getString(3));

                            sb.append("\n");
                        }
                    }
                    setString3(sb.toString());
                    //redirect to portfolio page
                    goToPortfolio();
                } else {
                    setString2("wrong username or password, please try again");
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        } else {
            setString2("");
        }
    }

    public void goToPortfolio() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "Portfolio.xhtml");
    }

    public void goToPortfolioUpdate() {
        try {
            //connect to database
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //query based on UserID from login page
            setString1("SELECT LegalFirstName, LegalLastName, StageFirstName, StageLastName, "
                    + "email, phoneNUM, location, bio "
                    + "FROM Users JOIN Performer ON Users.UserID = Performer.UserID "
                    + "WHERE Users.UserID = \'" + getUserID() + "\';");
            setString2(getString1());
            setString2("did execute");
            ResultSet rs = st.executeQuery(getString1());
            setString2("resultset should be full");
            if (rs.next()) {
                rs.previous();
                setString2("has next");
                while (rs.next()) {
                    setLegalFName(rs.getString(1));
                    setLegalLName(rs.getString(2));
                    setStageFirstName(rs.getString(3));
                    setStageLastName(rs.getString(4));
                    setEmail(rs.getString(5));
                    setPhoneNum(rs.getString(6));
                    setLocation(rs.getString(7));
                    setBio(rs.getString(8));
                }
            }
            //query based on UserID from above
            setString1("SELECT dayFree, hourFree "
                    + "FROM SCHEDULE "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeQuery(getString1());
            rs = st.executeQuery(getString1());
            if (rs.next()) {
                rs.previous();
                while (rs.next()) {
                    setDayFree(rs.getString(1));
                    setHourFree(rs.getString(1));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        //redirect ot portfolio
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "UpdatePortfolio.xhtml");
    }

    public void changelegalF() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change legal first name 
            setString1("UPDATE Users SET LegalFirstName = \'" + getNewlegalFname() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changelegalL() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change legal last name
            setString1("UPDATE Users SET LegalLastName = \'" + getNewLegalLName() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeStageF() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change stage first name
            setString1("UPDATE PERFORMER SET StageFirstName = \'" + getNewStageFirstName() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeStageL() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change stage last name 
            setString1("UPDATE PERFORMER SET StageLastName = \'" + getNewStageLastName() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeEmail() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change email
            setString1("UPDATE PERFORMER SET email = \'" + getNewEmail() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changePhoneNum() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change phonenum
            setString1("UPDATE PERFORMER SET PhoneNUM = \'" + getNewPhoneNum() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeLocation() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change location
            setString1("UPDATE PERFORMER SET Location = \'" + getNewLocation() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeBio() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change bio
            setString1("UPDATE PERFORMER SET Bio = \'" + getNewBio() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeDayFree() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change newday
            setString1("UPDATE SCHEDULE SET dayFree = \'" + getNewDayFree() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void changeHourFree() {
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change hourfree
            setString1("UPDATE SCHEDULE SET hourFree = \'" + getNewHourFree() + "\' "
                    + "WHERE UserID = \'" + getUserID() + "\';");
            st.executeUpdate(getString1());
            goToPortfolioUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();

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
        return String2;
    }

    /**
     * @param string2 the string2 to set
     */
    public void setString2(String string2) {
        this.String2 = string2;
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
     * @return the legalFName
     */
    public String getLegalFName() {
        return legalFName;
    }

    /**
     * @param legalFName the legalFName to set
     */
    public void setLegalFName(String legalFName) {
        this.legalFName = legalFName;
    }

    /**
     * @return the legalLName
     */
    public String getLegalLName() {
        return legalLName;
    }

    /**
     * @param legalLName the legalLName to set
     */
    public void setLegalLName(String legalLName) {
        this.legalLName = legalLName;
    }

    

    /**
     * @return the stageFirstName
     */
    public String getStageFirstName() {
        return stageFirstName;
    }

    /**
     * @param stageFirstName the stageFirstName to set
     */
    public void setStageFirstName(String stageFirstName) {
        this.stageFirstName = stageFirstName;
    }

    /**
     * @return the stageLastName
     */
    public String getStageLastName() {
        return stageLastName;
    }

    /**
     * @param stageLastName the stageLastName to set
     */
    public void setStageLastName(String stageLastName) {
        this.stageLastName = stageLastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio the bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return the newlegalFname
     */
    public String getNewlegalFname() {
        return newlegalFname;
    }

    /**
     * @param newlegalFname the newlegalFname to set
     */
    public void setNewlegalFname(String newlegalFname) {
        this.newlegalFname = newlegalFname;
    }

    /**
     * @return the newLegalLName
     */
    public String getNewLegalLName() {
        return newLegalLName;
    }

    /**
     * @param newLegalLName the newLegalLName to set
     */
    public void setNewLegalLName(String newLegalLName) {
        this.newLegalLName = newLegalLName;
    }

    /**
     * @return the newUserName
     */
    public String getNewUserName() {
        return newUserName;
    }

    /**
     * @param newUserName the newUserName to set
     */
    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    /**
     * @return the newStageFirstName
     */
    public String getNewStageFirstName() {
        return newStageFirstName;
    }

    /**
     * @param newStageFirstName the newStageFirstName to set
     */
    public void setNewStageFirstName(String newStageFirstName) {
        this.newStageFirstName = newStageFirstName;
    }

    /**
     * @return the newStageLastName
     */
    public String getNewStageLastName() {
        return newStageLastName;
    }

    /**
     * @param newStageLastName the newStageLastName to set
     */
    public void setNewStageLastName(String newStageLastName) {
        this.newStageLastName = newStageLastName;
    }

    /**
     * @return the newEmail
     */
    public String getNewEmail() {
        return newEmail;
    }

    /**
     * @param newEmail the newEmail to set
     */
    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    /**
     * @return the newPhoneNum
     */
    public String getNewPhoneNum() {
        return newPhoneNum;
    }

    /**
     * @param newPhoneNum the newPhoneNum to set
     */
    public void setNewPhoneNum(String newPhoneNum) {
        this.newPhoneNum = newPhoneNum;
    }

    /**
     * @return the newLocation
     */
    public String getNewLocation() {
        return newLocation;
    }

    /**
     * @param newLocation the newLocation to set
     */
    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    /**
     * @return the newBio
     */
    public String getNewBio() {
        return newBio;
    }

    /**
     * @param newBio the newBio to set
     */
    public void setNewBio(String newBio) {
        this.newBio = newBio;
    }

    /**
     * @return the dayFree
     */
    public String getDayFree() {
        return dayFree;
    }

    /**
     * @param dayFree the dayFree to set
     */
    public void setDayFree(String dayFree) {
        this.dayFree = dayFree;
    }

    /**
     * @return the hourFree
     */
    public String getHourFree() {
        return hourFree;
    }

    /**
     * @param hourFree the hourFree to set
     */
    public void setHourFree(String hourFree) {
        this.hourFree = hourFree;
    }

    /**
     * @return the newDayFree
     */
    public String getNewDayFree() {
        return newDayFree;
    }

    /**
     * @param newDayFree the newDayFree to set
     */
    public void setNewDayFree(String newDayFree) {
        this.newDayFree = newDayFree;
    }

    /**
     * @return the newHourFree
     */
    public String getNewHourFree() {
        return newHourFree;
    }

    /**
     * @param newHourFree the newHourFree to set
     */
    public void setNewHourFree(String newHourFree) {
        this.newHourFree = newHourFree;
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
