package de.vogella.mysql.first;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller{
	
	public static int basket=0, shop=0;
	
	@FXML
	Label lblStatus, lblStatus2, txtOwner, txtAdmin;
	
	@FXML
	TextField txtUserName, txtEmail, txtPass1, txtPass2, txt1Name, txt2Name, txtAdd1, txtAdd2, txtShop;
	
	@FXML
	PasswordField txtPassword, txtOwnerPass, txtAdminPass;
	
	@FXML
	ListView<String> LVProducts;
	
	@FXML
	ListView<String> LVOwner;
	
	@FXML
	ListView<String> LVAdmin;
	
	static ObservableList<String> productsList = FXCollections.observableArrayList();
	static ObservableList<String> Cart = FXCollections.observableArrayList();

	static String userName, password;
	
	Stage primaryStageLog;
	
	public void show(ActionEvent event) throws SQLException {
		
		shop=1;
		basket=0;
		MysqlCon con = new MysqlCon();
		con.showProducts();
		LVProducts.setItems(productsList);
		txtShop.setVisible(true);
	}
	
	public void show2(ActionEvent event) throws SQLException {
		
		shop=1;
		basket=0;
		MysqlCon con = new MysqlCon();
		con.showProducts();
		LVOwner.setItems(productsList);
	}
	
	
	public void login(ActionEvent event) throws IOException, SQLException {
		
		userName =  txtUserName.getText();
		password =  txtPassword.getText();

		System.out.println("Creating mysqlcon");
		MysqlCon con = new MysqlCon();
		if(con.checkPassword(userName, password)==true) {
			lblStatus.setText("Login Success");
			
			final Node source = (Node) event.getSource();
		    final Stage stage = (Stage) source.getScene().getWindow();
		    stage.close();
		    
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/fxml/Window.fxml"));
			AnchorPane anchorPane=loader.load();
			Controller controller = loader.getController();
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		else
			lblStatus.setText("Login Failed");
	}
	
	public void createAcc(ActionEvent event) throws IOException {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Register.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void createAcc2(ActionEvent event) throws SQLException {

		try{
			MysqlCon con = new MysqlCon();
			if(con.newClient(txtEmail.getText(), txtPass1.getText(), txtPass2.getText(), txt1Name.getText(), txt2Name.getText(), txtAdd1.getText(), txtAdd2.getText()) == true)
				lblStatus2.setText("Account has been created");
			else
				lblStatus2.setText("Passwords must match");
		}
		catch(SQLException e){
			lblStatus2.setText("Incorrect values or this user already exists!");
		}
	}
	
	public void goToStore(ActionEvent event) throws IOException {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Window.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void showCart(ActionEvent event) throws SQLException {
		
		shop=0;
		basket=1;
		MysqlCon con = new MysqlCon();
		con.showCart();
		LVProducts.setItems(Cart);
		txtShop.setVisible(false);
	}
	
	public String getProductId(String s, int size) {
		
		int i=s.indexOf("\t"),j;
		String a="";
		for(j=0;j<i;j++) {
			a=a+s.charAt(j);
		}
		return a;
	}
	
	public void selected(MouseEvent event) throws SQLException {
		String selected = LVProducts.getSelectionModel().getSelectedItem();
		System.out.println(selected);
		if(selected == null)
			return;
		int size = selected.length(), idP, amount = 0;
		
		if(shop==1) {
			String id = getProductId(selected, size);
			idP = Integer.parseInt(id);
			
			TextInputDialog dialog = new TextInputDialog("amount");
			dialog.setContentText("Please enter amount:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				amount=Integer.parseInt(result.get());
			}
			MysqlCon con = new MysqlCon();
			con.addToCart(idP, amount);
		}
		else if(basket==1) {
			MysqlCon con = new MysqlCon();
			con.removeFromCart(selected, size);
		}
		
	}
	
	public void ClientModule(ActionEvent event) throws IOException {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/LogIn.fxml"));
		AnchorPane anchorPane = loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
	}
	
	public void LogOut(ActionEvent event) throws IOException {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/LogIn.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
		
	}
	
	public void OwnerModule(ActionEvent event) throws IOException {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/OwnerLogIn.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
	}
	
	public void AdminModule(ActionEvent event) throws IOException {
	
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/AdminLogIn.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
	}
	
	public void OwnerLogIn(ActionEvent event) throws IOException {
		
		String pass=txtOwnerPass.getText();
		MysqlCon con = new MysqlCon();
		if(con.checkOwnerPass(pass)==true) {
			
			txtOwner.setText("Login Success");
			
			final Node source = (Node) event.getSource();
		    final Stage stage = (Stage) source.getScene().getWindow();
		    stage.close();
		    
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/fxml/OwnerWindow.fxml"));
			AnchorPane anchorPane=loader.load();
			Controller controller = loader.getController();
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else
			txtOwner.setText("Login Failed");
	}
	
	public void AdminLogIn(ActionEvent event) throws IOException {
		
		String pass=txtAdminPass.getText();
		MysqlCon con = new MysqlCon();
		if(con.checkAdminPass(pass)==true) {
			
			txtAdmin.setText("Login Success");
			
			final Node source = (Node) event.getSource();
		    final Stage stage = (Stage) source.getScene().getWindow();
		    stage.close();
		    
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/fxml/AdminWindow.fxml"));
			AnchorPane anchorPane=loader.load();
			Controller controller = loader.getController();
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else
			txtOwner.setText("Login Failed");
	}

}
