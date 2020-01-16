package de.vogella.mysql.first;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller{
	
	public static int basket=0, shop=0, orders=0;
	public static int products=0, order=0;
	
	@FXML
	Label lblStatus, lblStatus2, txtOwner, txtAdmin;
	
	@FXML
	TextField txtUserName, txtEmail, txtPass1, txtPass2, txt1Name, txt2Name, txtAdd1, txtAdd2, txtShop, name, price, available, description;
	
	@FXML
	TextField txtEmail2, txtPass12, txtPass22, txt1Name2, txt2Name2, txtAdd12, txtAdd22, txtId;
	
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
	static ArrayList<Integer> productsIdList = new ArrayList<>();
	static ObservableList<String> Cart = FXCollections.observableArrayList();
	static ArrayList<Integer> CartIds = new ArrayList<>();
	static ObservableList<String> Orders = FXCollections.observableArrayList();
	static ObservableList<String> AllOrders = FXCollections.observableArrayList();
	static ObservableList<String> Clients = FXCollections.observableArrayList();
	static ObservableList<String> Details = FXCollections.observableArrayList();

//	final ObservableList<StockItem> data = FXCollections.observableArrayList(
//			new StockItem(0, "namee", "0 groszy kurnaaa", "no")
//	);

	static String userName, password;
	
	Stage primaryStageLog;
	
	public void show(ActionEvent event) {
		
		shop=1;
		basket=0;
		orders=0;
		MysqlCon con = new MysqlCon();
		try {
			con.showProducts();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		LVProducts.setItems(productsList);
		txtShop.setVisible(true);
	}
	
	public void show2(ActionEvent event) {
		
		products=1;
		order=0;
		MysqlCon con = new MysqlCon();
		try {
			con.showProducts();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		txt.setVisible(true);
		LVOwner.setItems(productsList);
	}
	
	
	public void login(ActionEvent event){
		
		userName =  txtUserName.getText();
		password =  txtPassword.getText();

		System.out.println("Creating mysqlcon");
		MysqlCon con = new MysqlCon();
		try {
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void createAcc(ActionEvent event) {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Register.fxml"));
		AnchorPane anchorPane= null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void createAcc2(ActionEvent event) {

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
	
	public void goToStore(ActionEvent event) {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Window.fxml"));
		AnchorPane anchorPane= null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void showCart(ActionEvent event) {
		shop=0;
		basket=1;
		orders=0;
		MysqlCon con = new MysqlCon();
		try {
			con.showCart();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		LVProducts.setItems(Cart);
		txtShop.setVisible(false);
	}
	
	public void selected(MouseEvent event) {
		String selected = LVProducts.getSelectionModel().getSelectedItem();
		System.out.println(selected);
		if(selected == null) {
			return;
		}
		int selectedId = LVProducts.getSelectionModel().getSelectedIndex();
		System.out.println(selectedId);


		if(shop==1) {
			int id = productsIdList.get(selectedId - 1);

			TextInputDialog dialog = new TextInputDialog("amount");
			dialog.setContentText("Please enter amount:");
			Optional<String> result = dialog.showAndWait();

			if(!result.isPresent())
				return;

			int amount;
			try {
				amount = Integer.parseInt(result.get());
			}
			catch(Exception ex){
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Incorrect amount");
				alert.showAndWait();
				return;
			}

			MysqlCon con = new MysqlCon();
			try {
				con.addToCart(id, amount);

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

			int id = Controller.CartIds.get(selectedId - 1);
			System.out.println("id is = " +  id);

			try {
				con.removeFromCart(id);

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
		else if(orders==1) {
			orders=0;
			MysqlCon con = new MysqlCon();
			int id=selectedId+1;
			try {
				con.orderDetails(id);
			} catch (SQLException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Sql exception");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			LVProducts.setItems(Details);
		}
		
	}
	
	
	public void ClientModule(ActionEvent event) {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/LogIn.fxml"));
		AnchorPane anchorPane = null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
	}
	
	public void LogOut(ActionEvent event) {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/LogIn.fxml"));
		AnchorPane anchorPane= null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
		
	}
	
	public void OwnerModule(ActionEvent event) {
		
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/OwnerLogIn.fxml"));
		AnchorPane anchorPane= null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
	}
	
	public void AdminModule(ActionEvent event) {
	
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	    
		primaryStageLog = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/AdminLogIn.fxml"));
		AnchorPane anchorPane= null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStageLog.setScene(scene);
		primaryStageLog.show();
	}
	
	public void OwnerLogIn(ActionEvent event) {
		
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
			AnchorPane anchorPane= null;
			try {
				anchorPane = loader.load();
			} catch (IOException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Io exception");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			Controller controller = loader.getController();
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else
			txtOwner.setText("Login Failed");
	}
	
	public void AdminLogIn(ActionEvent event) {
		
		String pass=txtAdminPass.getText();
		MysqlConAdmin con = new MysqlConAdmin();
		if(con.checkAdminPass(pass)==true) {
			
			txtAdmin.setText("Login Success");
			
			final Node source = (Node) event.getSource();
		    final Stage stage = (Stage) source.getScene().getWindow();
		    stage.close();
		    
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/fxml/AdminWindow.fxml"));
			AnchorPane anchorPane= null;
			try {
				anchorPane = loader.load();
			} catch (IOException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Io exception");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			Controller controller = loader.getController();
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else
			txtAdmin.setText("Login Failed");
	}
	
	public void showOrders(ActionEvent event) {
		
		shop=0;
		basket=0;
		orders=1;
		MysqlCon con = new MysqlCon();
		try {
			con.showOrder();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
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
	
	public void addProduct(ActionEvent event) {
	    
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/NewProductDetails.fxml"));
		AnchorPane anchorPane= null;
		try {
			anchorPane = loader.load();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Io exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void addNewProduct(ActionEvent event) {
		
		MysqlConOwner con = new MysqlConOwner();
		try {
			con.addProduct(name.getText(), price.getText(), available.getText(), description.getText());
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void deleteProduct(MouseEvent event) {
		
		MysqlConOwner con = new MysqlConOwner();
		Integer selectedIndex = LVOwner.getSelectionModel().getSelectedIndex();
		if(selectedIndex == null)
			return;
		int id = productsIdList.get(selectedIndex - 1);
		
		if(products==1) {
			
			try {
				con.deleteP(id);
			} catch (SQLException e) {
				System.out.println("ERRRRRROR: " + e.getMessage());

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Error deleting item");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		else if(order==1) {

			try {
				con.orderDetails(id);
			} catch (SQLException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Sql exception");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			LVOwner.setItems(Details);
		}

	}
	
	public void showAllOrders(ActionEvent event){
		
		products=0;
		order=1;
		MysqlConOwner con = new MysqlConOwner();
		try {
			con.showOrders();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Error showing orders");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		txt.setVisible(false);
		LVOwner.setItems(AllOrders);
	}
	
	public void showClients(ActionEvent event){
		
		MysqlConAdmin con = new MysqlConAdmin();
		try {
			con.showCl();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Error showing clients");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
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
	
	public void deleteClient(MouseEvent event) {
		
		MysqlConAdmin con = new MysqlConAdmin();
		String selected = LVAdmin.getSelectionModel().getSelectedItem();
		if(selected == null)
			return;
		int id=getClId(selected);
		try {
			con.deleteCl(id);
		} catch (SQLException e) {
			System.out.println(e.getMessage());

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Error seleting users");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
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
	
	public void changeStatus(ActionEvent event) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Status.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void waiting(ActionEvent event) {
		
		String id=txtId.getText();
		int idd=Integer.parseInt(id);
		MysqlConOwner con = new MysqlConOwner();
		try {
			con.changeStatus(idd, "Waiting");
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}
	
	public void shipping(ActionEvent event) {
		
		String id=txtId.getText();
		int idd=Integer.parseInt(id);
		MysqlConOwner con = new MysqlConOwner();
		try {
			con.changeStatus(idd, "Shipping");
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void completed(ActionEvent event) {
		
		String id=txtId.getText();
		int idd;

		try {
			idd=Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return;
		}

		MysqlConOwner con = new MysqlConOwner();
		try {
			con.changeStatus(idd, "Completed");
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void canceled(ActionEvent event){
		
		String id=txtId.getText();
		int idd=Integer.parseInt(id);
		MysqlConOwner con = new MysqlConOwner();
		try {
			con.changeStatus(idd, "Canceled");
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Sql exception");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void doBackup(ActionEvent actionEvent) {
		try {

			Process process = Runtime.getRuntime().exec("mysqldump --databases store -R --triggers --no-create-info  --flush-privileges --user=admin --password=admin");

			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(primaryStageLog);

			if(!selectedFile.exists()){
				selectedFile.createNewFile();
			}

			try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {

				int read;
				byte[] bytes = new byte[1024];

				while ((read = process.getInputStream().read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			}

			System.out.println("Executed");
		} catch (IOException e) {
			System.out.println("Ewwor " + e.getMessage());
		}
	}

	public void doRestore(ActionEvent actionEvent) {

		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(primaryStageLog);
		if(!selectedFile.exists())
			return;

		ProcessBuilder pb = new ProcessBuilder("mysql", "--user=admin", "--password=admin");
		pb.redirectInput(selectedFile);
		try {
			Process process = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Success?");

	}
}
