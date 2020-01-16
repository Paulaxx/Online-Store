package de.vogella.mysql.first;
import java.sql.*;

class MysqlCon{  
	
	Connection con;
	static String logEmail, logPass;
	static int userId = -1;
	
	public MysqlCon() {
		try{
			System.out.println("Connecting to mysql database...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/store","client","");
			System.out.println("Done...");
		}
		catch(Exception e){
			System.out.println("Error connecting to the database!!!");
			System.out.println(e);
		}
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
		ResultSet rs=stmt.executeQuery("select * from view_stock");
		while(rs.next()) {
			product =rs.getInt(1)+"\t\t"+ rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getString(4);
			Controller.productsList.add(product);
			product="";
		}
    }
    
    
    public boolean checkPassword(String user, String pass) throws SQLException {
    	logEmail=user;
    	logPass=pass;
    	System.out.println(logEmail);
    	CallableStatement stmt=con.prepareCall("{?= call verify_user(?, ?)}");
    	stmt.registerOutParameter(1, Types.INTEGER);
    	stmt.setString(2, user);
    	stmt.setString(3, pass);
    	stmt.execute(); 
    	userId = stmt.getInt(1);
    	
    	if(userId == -1)
    		return false;
    	else
    		return true;
    }
    
    
    public boolean newClient(String email, String pass, String pass2, String firstName, String lastName, String add1, String add2) throws SQLException {
    	if(pass.contentEquals(pass2)) {
//    		Statement stmt=con.createStatement();
        	final String SQL_INSERT = "insert into users(email, password, firstName, lastName, addressLine1, addressLine2) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, pass);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);
			preparedStatement.setString(5, add1);
			preparedStatement.setString(6, add2);
			preparedStatement.execute();
        	logEmail=email;
        	logPass=pass;
        	return true;
    	}
    	return false;
    }
    
    public void showCart() throws SQLException {
    	String product="";
    	Controller.Cart.removeAll(Controller.Cart);
    	

		CallableStatement stmt=con.prepareCall(
				"select view_stock.name, cart.amount, cart.price "
				+ "from cart inner join view_stock where cart.itemId = view_stock.id and "
				+ "userId = ?");
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();
		product="name"+"\t\t"+"amount"+"\t\t"+"price";
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
    
    
    public boolean checkAdminPass(String pass) {
    	//sprawdzanie czy dobre haslo
    	return true;
    }
    
    public String itemName(String s, int size) {
    	
    	int i=s.indexOf("\t"),j;
		String a="";
		for(j=0;j<i;j++) {
			a=a+s.charAt(j);
		}
		return a;
    }
    
    public void removeFromCart(String s, int size) throws SQLException {
    	
    	int id=1; //id rzeczy
    	String name=itemName(s, size); //nazwa tej rzeczy
    	String sql="select id from view_stock where name like '";
    	sql=sql+name;
    	sql=sql+"'";
    	
    	Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(sql);  
		while(rs.next())  
			id=rs.getInt(1);
    	
    	CallableStatement stmt2=con.prepareCall("{call remove_from_cart(?, ?, ?)}");
    	stmt2.setString(1, logEmail);
    	stmt2.setString(2, logPass);
    	stmt2.setInt(3, id);
    	stmt2.execute(); 
    }

	public void submitOrder() throws SQLException {

		CallableStatement statement = con.prepareCall("{call place_order(?, ?)}");
		statement.setString(1, logEmail);
		statement.setString(2, logPass);
		statement.execute();
	}
	
	public void showOrder() throws SQLException {
		
		String orders="";
		Controller.Orders.removeAll(Controller.Orders);
		String sql="select * from orders where userId = ";
		sql=sql+userId;
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(sql);  
		while(rs.next()) {
			orders=rs.getString(3)+"\t\t"+rs.getDate(4);
			Controller.Orders.add(orders);
			orders="";
		}
	}

    
}  