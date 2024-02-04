package com.covideinfo.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcCollection {
	
      public Connection getJdbcConnection() throws ClassNotFoundException {
    	  String connectionURL = "jdbc:postgresql://localhost:5432/SIMS";
  	    Connection con = null;
  		try {
//  			Class.forName("com.mysql.jdbc.Driver");
  			Class.forName("org.postgresql.Driver");
  			con = DriverManager.getConnection(connectionURL, "root", "password");
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  		return con;
      }

}
