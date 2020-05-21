<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Java Server Pages demo login</title>
    </head>
    <body>
        <%@ page import="java.sql.*"%>
        <%@ page import="javax.sql.*"%>
        <%

            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@141.165.201.10:1521:oracle12c", "student010", "Fransgel14");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM login");
            StringBuilder sb = new StringBuilder();
            if (rs.next()) {
                rs.previous();

                while (rs.next()) {
                    sb.append(rs.getString(1));
                    sb.append(" ");
                    sb.append(rs.getString(2));
                    sb.append(" ");
                    sb.append(rs.getString(3));
                    sb.append(" ");
                    sb.append(rs.getString(4));
                    sb.append("<br />");
                }
                

                
                out.println(sb.toString());
            } else {
                out.println("Invalid, try again");
            }
        %>
    </body>
</html>