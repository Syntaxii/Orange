/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Brett Exley
 */
@Named(value = "validateForm")
@RequestScoped
public class validateForm {
    private String string1;
    private String string2;

    public void getResponse() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@141.165.201.10:1521:oracle12c", "student010", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery(getString1());
            StringBuilder sb = new StringBuilder();
            if (rs.next()) {
                rs.previous();

                while (rs.next()) {
                    sb.append(rs.getString(1));
                    sb.append(" ");
                    sb.append(rs.getString(2));
                    sb.append(" ");
                    sb.append(rs.getString(3));
                    sb.append("<br />");
                }

                setString2(sb.toString());
            } else {
                setString2("try again");
            }
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