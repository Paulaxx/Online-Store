package de.vogella.mysql.first;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	Label lblStatus, lblStatus2;
	
	@FXML
	TextField txtUserName, txtEmail, txtPass1, txtPass2, txt1Name, txt2Name, txtAdd1, txtAdd2;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	ListView<String> LVProducts;
	
	ObservableList<String> productsList = FXCollections.observableArrayList("product1", "product2", "product3");
	
	public void show(ActionEvent event) throws SQLException {
		LVProducts.setItems(productsList);
		System.out.println("show"); 
		MysqlCon con = new MysqlCon();
		con.select();
	}
	
	public void login(ActionEvent event) throws IOException {
		
		String userName, password;
		
		userName =  txtUserName.getText();
		password =  txtPassword.getText();
		
		MysqlCon con = new MysqlCon();
		if(con.checkPassword(userName, password)==true) {
			lblStatus.setText("Login Success");
			
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
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Register.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void createAcc2(ActionEvent event) {
		
		MysqlCon con = new MysqlCon();
		if(con.newClient(txtEmail.getText(), txtPass1.getText(), txtPass2.getText(), txt1Name.getText(), txt2Name.getText(), txtAdd1.getText(), txtAdd2.getText())==true)
			lblStatus2.setText("Account has been created");
		else
			lblStatus2.setText("Cannot create acount");
	}
	
	public void goToStore(ActionEvent event) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Window.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
