package de.vogella.mysql.first;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Test extends Application{
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/LogIn.fxml"));
		AnchorPane anchorPane=loader.load();
		Controller controller = loader.getController();
		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String args[]){  
		launch(args);
	}
	

}
