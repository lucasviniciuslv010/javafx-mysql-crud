package com.lucasvinicius.javafx_mysql_crud;

import java.io.IOException;

import com.lucasvinicius.javafx_mysql_crud.util.JPAUtil;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws IOException {  
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