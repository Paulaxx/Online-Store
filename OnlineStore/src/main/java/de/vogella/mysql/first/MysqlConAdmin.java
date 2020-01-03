package de.vogella.mysql.first;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConAdmin {

	Connection con;
	
	public MysqlConAdmin() {
		try{
			System.out.println("Connecting to mysql database...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/store","admin","admin");
			System.out.println("Done...");
		}
		catch(Exception e){
			System.out.println("Error connecting to the database!!!");
			System.out.println(e);
		}
	}
	
	
    public static void main(String args[]){  
    	
    }
}
