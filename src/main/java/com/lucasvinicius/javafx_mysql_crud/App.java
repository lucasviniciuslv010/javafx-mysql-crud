package com.lucasvinicius.javafx_mysql_crud;

import java.io.IOException;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.controller.LoginViewController;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.services.UserService;
import com.lucasvinicius.javafx_mysql_crud.util.JPAUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws IOException { 
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
			ScrollPane scrollPane = loader.load();
		
			LoginViewController controller = ((LoginViewController)loader.getController());
			controller.setService(new UserService());
			
			mainScene = new Scene(scrollPane);
			
			JPAUtil.initEntityManagerFactory();
			GUILoader.addBackgroundImage("src//main//resources//images//background.png");
			
			primaryStage.setTitle("Sample JavaFX Application");
			primaryStage.setScene(mainScene);
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
    }
    
    @Override
    public void stop() {
    	JPAUtil.closeEntityManagerFactory();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static Scene getMainScene() {
    	return mainScene;
    }

} 