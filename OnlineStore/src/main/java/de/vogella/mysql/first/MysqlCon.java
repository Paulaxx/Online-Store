package de.vogella.mysql.first;
import java.sql.*;

class MysqlCon{  
	
	Connection con;
	
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
    
    
    public boolean checkPassword(String user, String pass) {
    	//true jesli user pasuje do hasla i mozna zalogowac
    	//false jesli nie ma takiego uzytkownika/hasla/cos jest nie tak z kontem
    	return true;
    }
    
    
    //do dopisania zwracanie konkretnego bledu(jakie dane sa niepoprawne)
    public boolean newClient(String email, String pass, String pass2, String firstName, String lastName, String add1, String add2) {
    	//tworzenie konta i od razu logowanie na to konto
    	//jesli dane sa ok to true
    	//jesli cos jest nie tak z danymi albo nie mozna utworzyc konta to false
    	return true;
    }
    
    
}  