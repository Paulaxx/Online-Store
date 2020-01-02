package de.vogella.mysql.first;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

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
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	Label lblStatus, lblStatus2;
	
	@FXML
	TextField txtUserName, txtEmail, txtPass1, txtPass2, txt1Name, txt2Name, txtAdd1, txtAdd2, txtShop;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	ListView<String> LVProducts;
	
	static ObservableList<String> productsList = FXCollections.observableArrayList();
	static ObservableList<String> Cart = FXCollections.observableArrayList();
	
	public void show(ActionEvent event) throws SQLException {
		
		MysqlCon con = new MysqlCon();
		con.showProducts();
		LVProducts.setItems(productsList);
		txtShop.setVisible(true);
	}
	
	public void login(ActionEvent event) throws IOException, SQLException {
		
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
	
	public void createAcc2(ActionEvent event) throws SQLException {
		
		MysqlCon con = new MysqlCon();
		if(con.newClient(txtEmail.getText(), txtPass1.getText(), txtPass2.getText(), txt1Name.getText(), txt2Name.getText(), txtAdd1.getText(), txtAdd2.getText())==true)
			lblStatus2.setText("Account has been created");
		else
			lblStatus2.setText("Cannot create acount, different passwords");
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
	
	public void showCart(ActionEvent event) throws SQLException {
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
		String selected = LVProducts.getSelectionModel().getSelectedItem(), id;
		int size=selected.length(), idP, amount = 0;
		id=getProductId(selected, size);
		idP=Integer.parseInt(id);	
		
		TextInputDialog dialog = new TextInputDialog("amount");
		dialog.setContentText("Please enter amount:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			amount=Integer.parseInt(result.get());
		}
		MysqlCon con = new MysqlCon();
		con.addToCart(idP, amount);
		
	}

}
