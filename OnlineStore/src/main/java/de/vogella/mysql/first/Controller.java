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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller{
	
	public static int basket=0, shop=0;
	
	@FXML
	Label lblStatus, lblStatus2, txtOwner, txtAdmin;
	
	@FXML
	TextField txtUserName, txtEmail, txtPass1, txtPass2, txt1Name, txt2Name, txtAdd1, txtAdd2, txtShop, name, price, available, description;
	
	@FXML
	TextField txtEmail2, txtPass12, txtPass22, txt1Name2, txt2Name2, txtAdd12, txtAdd22;
	
	@FXML
	PasswordField txtPassword, txtOwnerPass, txtAdminPass;
	
	@FXML
	ListView<String> LVProducts;
	
	@FXML
	ListView<String> LVOwner;
	
	@FXML
	ListView<String> LVAdmin;
	
	@FXML
	Text txt;
	
	static ObservableList<String> productsList = FXCollections.observableArrayList();
	static ObservableList<String> Cart = FXCollections.observableArrayList();
	static ObservableList<String> Orders = FXCollections.observableArrayList();
	static ObservableList<String> AllOrders = FXCollections.observableArrayList();
	static ObservableList<String> Clients = FXCollections.observableArrayList();

//	final ObservableList<StockItem> data = FXCollections.observableArrayList(
//			new StockItem(0, "namee", "0 groszy kurnaaa", "no")
//	);


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
		txt.setVisible(true);
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
	
	public void selected(MouseEvent event) {
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
				try {
					amount = Integer.parseInt(result.get());
				}
				catch(Exception ex){
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Incorrect amount");
					alert.showAndWait();
					return;
				}
			}

			MysqlCon con = new MysqlCon();
			try {
				con.addToCart(idP, amount);

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Item was added to your cart");
				alert.showAndWait();
			} catch (SQLException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Item can't be added to cart!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		else if(basket==1) {
			MysqlCon con = new MysqlCon();
			try {
				con.removeFromCart(selected, size);

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Item was removed from your cart");
				alert.showAndWait();
			} catch (SQLException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Item can't be deleted from cart!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
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
		MysqlConOwner con = new MysqlConOwner();
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
	
	public void showOrders(ActionEvent event) throws SQLException {
		
		MysqlCon con = new MysqlCon();
		con.showOrder();
		LVProducts.setItems(Orders);
		
	}
	
	public void submitOrder(ActionEvent event) {
		MysqlCon con = new MysqlCon();
		try {
			con.submitOrder();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Your order was placed");
			alert.showAndWait();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Placing your order failed");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void addProduct(ActionEvent event) throws IOException{
	    
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/NewProductDetails.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void addNewProduct(ActionEvent event) throws SQLException {
		
		MysqlConOwner con = new MysqlConOwner();
		con.addProduct(name.getText(), price.getText(), available.getText(), description.getText());
	}
	
	public int getProductId(String s) {
		
		int id,p,j;
		p=s.indexOf("\t");
		String a="";
		for(j=0;j<p;j++) {
			a=a+s.charAt(j);
		}
		id=Integer.parseInt(a);
		return id;
		
	}
	
	public void deleteProduct(MouseEvent event) throws SQLException {
		
		MysqlConOwner con = new MysqlConOwner();
		String selected = LVOwner.getSelectionModel().getSelectedItem();
		if(selected == null)
			return;
		int id=getProductId(selected);
		con.deleteP(id);
	}
	
	public void showAllOrders(ActionEvent event) throws SQLException {
		
		MysqlConOwner con = new MysqlConOwner();
		con.showOrders();
		txt.setVisible(false);
		LVOwner.setItems(AllOrders);
	}
	
	public void showClients(ActionEvent event) throws SQLException {
		
		MysqlConAdmin con = new MysqlConAdmin();
		con.showCl();
		LVAdmin.setItems(Clients);
	}
	
	public int getClId(String s) {
		
		int id,p,j;
		p=s.indexOf("\t");
		String a="";
		for(j=0;j<p;j++) {
			a=a+s.charAt(j);
		}
		id=Integer.parseInt(a);
		return id;
	}
	
	public void deleteClient(MouseEvent event) throws SQLException {
		
		MysqlConAdmin con = new MysqlConAdmin();
		String selected = LVAdmin.getSelectionModel().getSelectedItem();
		if(selected == null)
			return;
		int id=getClId(selected);
		con.deleteCl(id);
	}
	
	public void addClient(ActionEvent event) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/AddClient.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void AdminAddCl(ActionEvent event) {
		
		try{
			MysqlConAdmin con = new MysqlConAdmin();
			con.newClient(txtEmail2.getText(), txtPass12.getText(), txtPass22.getText(), 
					txt1Name2.getText(), txt2Name2.getText(), txtAdd12.getText(), txtAdd22.getText());
		}
		catch(SQLException e){
			
		}
	}
	
	public void modifyAm(ActionEvent event) {
		
		TextInputDialog dialog = new TextInputDialog("ID");
		dialog.setContentText("Enter id of product, you want to modify:");
		Optional<String> result = dialog.showAndWait();

		if(!result.isPresent())
			return;

		int id = 0;
		try {
			id = Integer.parseInt(result.get());
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Incorrect ID");
			alert.showAndWait();
			return;
		}

		System.out.println(id);
		
		TextInputDialog dialog2 = new TextInputDialog("1");
		dialog2.setContentText("Enter new amount:");
		Optional<String> result2 = dialog2.showAndWait();

		if(!result2.isPresent())
			return;

		int amount = 0;
		try {
			amount = Integer.parseInt(result2.get());
		} catch (NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Incorrect amount");
			alert.showAndWait();
			return;
		}
		System.out.println(amount);
		
		MysqlConOwner con = new MysqlConOwner();
		try {
			con.modifyAm(id, amount);
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Error setting new amount");
			alert.setContentText(e.getMessage());
			System.out.println(e.getMessage());
			alert.showAndWait();

			return;
		}

	}

}
