package de.vogella.mysql.first;

import java.sql.*;

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
		
//		Statement stmt=con.createStatement();
		final String SQL_UPDATE = "update stock set available = ? where id = ?";
		PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
		preparedStatement.setInt(1, amount);
		preparedStatement.setInt(2, id);

		System.out.println(preparedStatement.execute());
	}
	
	public void orderDetails(int id) throws SQLException {
		
		Controller.Details.removeAll(Controller.Details);
		
		CallableStatement stmt=con.prepareCall(
				"select * from view_order_items where orderId= ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		String product="";
		//String product="name"+"\t\t"+"amount"+"\t\t"+"price";
		//Controller.Cart.add(product);
		while(rs.next()) {
			product = rs.getString(1)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getString(5);
			Controller.Details.add(product);
		}
		
	}


}
