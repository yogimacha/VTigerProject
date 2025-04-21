package com.GenericUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

/**
 * @author : M.Yogesh
 */
public class DataBase_Utility {
	Connection con;
		public Connection getDataBaseConnection(String url,String username,String password) {
			Driver dobj=null;
			try {
				DriverManager.registerDriver(dobj);
			} catch (SQLException e) {
				
			}
			
			try {
				con=DriverManager.getConnection(url,username,password);
			} catch (Exception e) {
				System.out.println("Database connection has not done");
			}
			
			return con;
			
		}
		
		public Connection getDataBaseConnection() {
			Driver dobj=null;
			try {
				DriverManager.registerDriver(dobj);
				con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/mydatabase","root","12345678");
			}
			 catch (Exception e) {
				System.out.println("Database connection has not done");
			}
			
			return con;
			
		}
		
		
		
		
		
		public void closeDataBaseConnection() {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println("Database connection hasn't closed");
			}
		}
		
		public ResultSet executeSelectQuery(String query) {
			ResultSet res=null;
			try {
				Statement statement=con.createStatement();
				res = statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
			
		}
		
		public int executeNonSelectQuery(String query) {
			int result=0;
			Statement stat;
			try {
				stat = con.createStatement();
				result=stat.executeUpdate(query);
			} catch (Exception e) {
				System.out.println("NonSelect query not executed");
			}
			return result;
		}	
		
}
