package de.vogella.mysql.first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public boolean checkOwnerPass(String pass) {
    	//sprawdzanie czy dobre haslo
    	return true;
    }
    
	public void addProduct(String name, String price, String available, String description) throws SQLException {
		
		Statement stmt=con.createStatement(); 
    	String sql="insert into stock(name, price, available,description) values ('"+name+"', '"+price+"', '"+available+"', '"+description+"')";
    	stmt.executeUpdate(sql);
    	System.out.println("done");
		
	}
	
	public void deleteP(int id) throws SQLException {
		
		Statement stmt=con.createStatement(); 
		String sql="delete from stock where id=";
		sql=sql+id;
    	stmt.executeUpdate(sql);
    	System.out.println("done");
	}
	
	public void showOrders() throws SQLException {
		
		String orders="";
		Controller.AllOrders.removeAll(Controller.AllOrders);
		String sql="select * from orders";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(sql);  
		while(rs.next()) {
			orders=rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getDate(4);
			Controller.AllOrders.add(orders);
			orders="";
		}
	}
	
	public void modifyAm(int id, int amount) throws SQLException {
		
		Statement stmt=con.createStatement();
		String sql="update stock set available=";
		sql=sql+amount;
		sql=sql+" where id=";
		sql=sql+id;
		stmt.executeUpdate(sql); 
	}


}
