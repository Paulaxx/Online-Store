package de.vogella.mysql.first;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConOwner {
	
	Connection con;
	
	public MysqlConOwner() {
		try{
			System.out.println("Connecting to mysql database...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/store","owner","1234");
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
