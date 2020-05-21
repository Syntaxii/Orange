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
@Named(value = "addEventBean")
@RequestScoped
public class addEventBean {
  //create Strings to manipulate items from webpage
    private String String1;
    private String String2;
    private String EID;
    private String Location;
    private String Title;
    private String Role;
    private String StartDate;
    private String EndDate;

    public void addEvent() {
        int count = 0;
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            //get EID from event, to make sure there are only 1 Event with 1 EID
            setString1("SELECT EID from EVENT;");
            setString2(getString1());
            ResultSet rs = st.executeQuery(getString1());
            //Stringbuilder to make a string from the resultset above
            StringBuilder sb = new StringBuilder();
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
            //makes sure that EID is higher number than any that exist
            setEID(Integer.toString(count));
            //insert into event table all values as needed
            setString1("INSERT INTO EVENT VALUES (\'" + getEID() + "\',\'"
                        + getLocation() + "\',\'"
                        + getTitle() + "\',\'" + getRole() + "\',\'" + getStartDate() + "\',\'" 
                        + getEndDate() + "\');");
            setString2(getString1());
            st.executeUpdate(getString1());
            setString2("worked");
            
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
     * @return the EID
     */
    public String getEID() {
        return EID;
    }

    /**
     * @param EID the EID to set
     */
    public void setEID(String EID) {
        this.EID = EID;
    }

    /**
     * @return the Location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * @param Location the Location to set
     */
    public void setLocation(String Location) {
        this.Location = Location;
    }

    /**
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return the Role
     */
    public String getRole() {
        return Role;
    }

    /**
     * @param Role the Role to set
     */
    public void setRole(String Role) {
        this.Role = Role;
    }

    /**
     * @return the StartDate
     */
    public String getStartDate() {
        return StartDate;
    }

    /**
     * @param StartDate the StartDate to set
     */
    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    /**
     * @return the EndDate
     */
    public String getEndDate() {
        return EndDate;
    }

    /**
     * @param EndDate the EndDate to set
     */
    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }
}
