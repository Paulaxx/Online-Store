package de.vogella.mysql.first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    
    public boolean checkAdminPass(String pass) {
    	if(pass.contentEquals("admin")) {
    		return true;
    	}
    	return false;
    }
    
    
	public void showCl() throws SQLException {
		
		String orders="";
		Controller.Clients.removeAll(Controller.Clients);
		String sql="select * from users";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(sql);  
		while(rs.next()) {
			orders=rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(4)+"\t\t"+rs.getString(5)+"\t\t"+rs.getString(6)+"\t\t"+rs.getString(7);
			Controller.Clients.add(orders);
			orders="";
		}
	}
	
	public void deleteCl(int id) throws SQLException {
		
		Statement stmt=con.createStatement(); 
		String sql="delete from users where id=";
		sql=sql+id;
    	stmt.executeUpdate(sql);
    	System.out.println("done");
	}


	public void newClient(String email, String pass, String pass2, String firstName, String lastName, String add1, String add2) throws SQLException {

		if(pass.contentEquals(pass2)) {
    		Statement stmt=con.createStatement(); 
        	String sql="insert into users(email, password, firstName, lastName, addressLine1, addressLine2) values ('"+email+"', '"+pass+"', '"+firstName+"', '"+lastName+"', '"+add1+"', '"+add2+"')";
        	stmt.executeUpdate(sql);
        	System.out.println("done");
    	}
		
	}
}
