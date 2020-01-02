package de.vogella.mysql.first;
import java.sql.*;

class MysqlCon{  
	
	Connection con;
	static String logEmail, logPass;
	
	public MysqlCon() {
		try{  
    		Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/store","sqluser","sqluserpw");  
    	}
		catch(Exception e){ System.out.println(e);} 
	}
	
	
    public static void main(String args[]){  
    	
    }
    
    
    public void select() throws SQLException {
    	Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from users");  
		while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
		//con.close();
    }
    
    public void showProducts() throws SQLException {
    	String product="";
    	Controller.productsList.removeAll(Controller.productsList);
    	Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from stock");
		while(rs.next()) {
			product =rs.getInt(1)+"\t\t"+ rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getString(5);
			Controller.productsList.add(product);
			product="";
		}
    }
    
    
    public boolean checkPassword(String user, String pass) throws SQLException {
    	logEmail=user;
    	logPass=pass;
    	CallableStatement stmt=con.prepareCall("{?= call verify_user(?, ?)}");
    	stmt.registerOutParameter(1, Types.INTEGER);
    	stmt.setString(2, user);
    	stmt.setString(3, pass);
    	stmt.execute(); 
    	int output = stmt.getInt(1);
    	
    	if(output==-1)
    		return false;
    	return true;
    }
    
    
    public boolean newClient(String email, String pass, String pass2, String firstName, String lastName, String add1, String add2) throws SQLException {
    	if(pass.contentEquals(pass2)) {
    		Statement stmt=con.createStatement(); 
        	String sql="insert into users(email, password, firstName, lastName, addressLine1, addressLine2) values ('"+email+"', '"+pass+"', '"+firstName+"', '"+lastName+"', '"+add1+"', '"+add2+"')";
        	stmt.executeUpdate(sql);
        	System.out.println("done");
        	logEmail=email;
        	logPass=pass;
        	return true;
    	}
    	return false;
    }
    
    public void showCart() throws SQLException {
    	String product="";
    	Controller.Cart.removeAll(Controller.Cart);
    	Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select stock.name, cart.amount, cart.price "
				+ "from cart inner join stock where cart.itemId=stock.id and "
				+ "userId=(select id from users where email like '"+logEmail+"')");
		product="name"+"\t"+"amount"+"\t"+"price";
		Controller.Cart.add(product);
		while(rs.next()) {
			product =rs.getString(1)+"\t\t"+rs.getInt(2)+"\t\t"+rs.getInt(3);
			Controller.Cart.add(product);
			product="";
		}
    }
    
    public void addToCart(int id, int amount) throws SQLException {
    	CallableStatement stmt=con.prepareCall("{call add_to_cart(?, ?, ?, ?)}");
    	stmt.setString(1, logEmail);
    	stmt.setString(2, logPass);
    	stmt.setInt(3, id);
    	stmt.setInt(4, amount);
    	stmt.execute(); 
    }
    
    
}  