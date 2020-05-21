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
@Named(value = "registerBean")
@RequestScoped
public class RegisterBean {
      //create Strings to manipulate items from webpage
    private String String1;
    private String String2;
    private String UserID;
    private String legalFirstName;
    private String legalLastName;
    private String isPerformer;
    private String userName;
    private String password;
    private String stageFirstName;
    private String stageLastName;
    private String email;
    private String phoneNum;
    private String location;
    private String desiredPay;
    private String education;
    private String bio;
    private String restrictions;
    private String age;
    private String gender;
    private String isAuditory;
    private String isVisual;
    private String skinColor;
    private String hairColor;
    private String eyeColor;
    private String skinTone;
    private String bodyType;
    private String height;
    private String weight;
    private String voicePart;
    private String instrument;
    private String date;
    private String METAINFID;
    private String String3;
    private boolean isAuditoryb;
    private boolean isVisualb;
    /**
     * Creates a new instance of RegisterBean
     */
    public RegisterBean() {
    }
    public void getResponse(){
        int count = 0;
        //make sure legal last name is populated
        if(getLegalLastName() != null){
        try {
            //database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            //find all UserID's from user
            setString1("SELECT UserID from Users");
            ResultSet rs = st.executeQuery(getString1());
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
            //make sure UserID is the maximum number available
            count++;
            setUserID(Integer.toString(count));
           //create date with current date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            setDate(dtf.format(localDate));
            //insert into the database the values given
            setString1("INSERT INTO Users VALUES (\'" + getUserID() + "\',\'" + getLegalFirstName() + "\',\'" 
                    + getLegalLastName() +  "\',\'" + getIsPerformer() +  "\',\'" 
                    + getUserName() + "\',\'"+ getDate() + "\');");
            st.executeUpdate(getString1());
            setString1(getDate());
            
            //make sure metainfid doesnt match userid
            setMETAINFID(Integer.toString(count + 3));
            setString3(getMETAINFID());
            setString2(getUserID());
            //insert into metainfid values
            setString1("INSERT INTO METAINFID VALUES(\'" + getMETAINFID() + "\','" + getUserID() +  "\');");
            st.executeUpdate(getString1());
            //insert into metainfo values
            setString1("INSERT INTO METAINFO VALUES(\'" + getPassword() + "\','" + getMETAINFID() +  "\');");
            st.executeUpdate(getString1());
            //check and see if isperformer exists, if yes then fill in values
            if(getIsPerformer().equals("yes")){
            setString1("INSERT INTO PERFORMER VALUES(\'" + getStageFirstName() 
                    + "\','" + getStageLastName() + "\','" + getEmail() + "\','" + getPhoneNum() 
                    + "\','" + getLocation() + "\','" + getDesiredPay() + "\','" + getEducation() 
                    + "\','" + getBio() + "\','" + getUserID() +  "\');");
            st.executeUpdate(getString1());
            }
            //check and see if isauditory and isvisual exists, if yes then fill in values properly
            if(getIsAuditory().equals("yes")){
                if(getIsVisual().equals("yes")){
                setString1("INSERT INTO TRAITS VALUES(\'" + getUserID() 
                    + "\','" + getAge() + "\','" + getGender() + "\','YES\','YES\','" + getSkinColor() 
                    + "\','" + getHairColor() + "\','" + getEyeColor() + "\');");
                }
                if(getIsVisual().equals("no")){
                setString1("INSERT INTO TRAITS VALUES(\'" + getUserID() 
                    + "\','" + getAge() + "\','" + getGender() + "\','YES\','NO\','" + getSkinColor() 
                    + "\','" + getHairColor() + "\','" + getEyeColor() + "\');");
                }
                setString2("isaud yes");
            }else{
                if(getIsVisual().equals("yes")){
                setString1("INSERT INTO TRAITS VALUES(\'" + getUserID() 
                    + "\','" + getAge() + "\','" + getGender() + "\','NO\','YES\','" + getSkinColor() 
                    + "\','" + getHairColor() + "\','" + getEyeColor() + "\');");
                }
                if(getIsVisual().equals("no")){
                setString1("INSERT INTO TRAITS VALUES(\'" + getUserID()  
                    + "\','" + getAge() + "\','" + getGender() + "\','NO\','NO\','" + getSkinColor() 
                    + "\','" + getHairColor() + "\','" + getEyeColor() + "\');");
                }
                setString2("isaud no");
            }
           
            
            //update for each, both isauditory and isvisual 
            st.executeUpdate(getString1());
            
            
          //check and see if isvisual is yes, if so, then insert into visual values appropriate
            if(getIsVisual().equals("yes")){
                setString1("INSERT INTO VISUAL VALUES(\'" + getUserID()
                + "\','" + getSkinTone() + "\','" + getBodyType()
                + "\','" + getHeight() + "\','" + getWeight() + "\');");
                st.executeUpdate(getString1());
            }
            //check and see if isvisual is yes, if so, then insert into visual values appropriate
            if(getIsAuditory().equals("yes")){
                setString1("INSERT INTO AUDITORY VALUES(\'" + getUserID() 
                + "\','" + getVoicePart() + "\','" + getInstrument() + "\');");
                st.executeUpdate(getString1());
            }
            //insert into restrictions values  from webpage
            setString1("INSERT INTO RESTRICTIONS VALUES(\'" + getRestrictions() + "\','" + getUserID() + "\');");
            st.executeUpdate(getString1());
            
        } catch (Exception ex) {
            ex.printStackTrace();
              
        }
        
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
     * @return the legalFirstName
     */
    public String getLegalFirstName() {
        return legalFirstName;
    }

    /**
     * @param legalFirstName the legalFirstName to set
     */
    public void setLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName;
    }

    /**
     * @return the legalLastName
     */
    public String getLegalLastName() {
        return legalLastName;
    }

    /**
     * @param legalLastName the legalLastName to set
     */
    public void setLegalLastName(String legalLastName) {
        this.legalLastName = legalLastName;
    }

    /**
     * @return the isPerformer
     */
    public String getIsPerformer() {
        return isPerformer;
    }

    /**
     * @param isPerformer the isPerformer to set
     */
    public void setIsPerformer(String isPerformer) {
        this.isPerformer = isPerformer;
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
     * @return the desiredPay
     */
    public String getDesiredPay() {
        return desiredPay;
    }

    /**
     * @param desiredPay the desiredPay to set
     */
    public void setDesiredPay(String desiredPay) {
        this.desiredPay = desiredPay;
    }

    /**
     * @return the education
     */
    public String getEducation() {
        return education;
    }

    /**
     * @param education the education to set
     */
    public void setEducation(String education) {
        this.education = education;
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
     * @return the restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * @param restrictions the restrictions to set
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the isAuditory
     */
    public String getIsAuditory() {
        return isAuditory;
    }

    /**
     * @param isAuditory the isAuditory to set
     */
    public void setIsAuditory(String isAuditory) {
        this.isAuditory = isAuditory;
    }

    /**
     * @return the isVisual
     */
    public String getIsVisual() {
        return isVisual;
    }

    /**
     * @param isVisual the isVisual to set
     */
    public void setIsVisual(String isVisual) {
        this.isVisual = isVisual;
    }

    /**
     * @return the skinColor
     */
    public String getSkinColor() {
        return skinColor;
    }

    /**
     * @param skinColor the skinColor to set
     */
    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    /**
     * @return the hairColor
     */
    public String getHairColor() {
        return hairColor;
    }

    /**
     * @param hairColor the hairColor to set
     */
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * @return the eyeColor
     */
    public String getEyeColor() {
        return eyeColor;
    }

    /**
     * @param eyeColor the eyeColor to set
     */
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * @return the skinTone
     */
    public String getSkinTone() {
        return skinTone;
    }

    /**
     * @param skinTone the skinTone to set
     */
    public void setSkinTone(String skinTone) {
        this.skinTone = skinTone;
    }

    /**
     * @return the bodyType
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     * @param bodyType the bodyType to set
     */
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @return the voicePart
     */
    public String getVoicePart() {
        return voicePart;
    }

    /**
     * @param voicePart the voicePart to set
     */
    public void setVoicePart(String voicePart) {
        this.voicePart = voicePart;
    }

    /**
     * @return the instrument
     */
    public String getInstrument() {
        return instrument;
    }

    /**
     * @param instrument the instrument to set
     */
    public void setInstrument(String instrument) {
        this.instrument = instrument;
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
     * @return the METAINFID
     */
    public String getMETAINFID() {
        return METAINFID;
    }

    /**
     * @param METAINFID the METAINFID to set
     */
    public void setMETAINFID(String METAINFID) {
        this.METAINFID = METAINFID;
    }

    /**
     * @return the String3
     */
    public String getString3() {
        return String3;
    }

    /**
     * @param String3 the String3 to set
     */
    public void setString3(String String3) {
        this.String3 = String3;
    }
}
