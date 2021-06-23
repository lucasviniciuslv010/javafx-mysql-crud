package com.lucasvinicius.javafx_mysql_crud.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;

import com.lucasvinicius.javafx_mysql_crud.App;
import com.lucasvinicius.javafx_mysql_crud.gui.controller.DialogForm;
import com.lucasvinicius.javafx_mysql_crud.gui.controller.HomeViewController;
import com.lucasvinicius.javafx_mysql_crud.gui.controller.LoginViewController;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.model.AbstractEntity;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUILoader {

	public static synchronized <T> void loadView(String fxml, Consumer<T> controllerDependencyInjector) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
			Parent parent = loader.load();

			VBox newVBox = null;
			VBox mainVBox = (VBox) ((ScrollPane) App.getMainScene().getRoot()).getContent();

			if (parent instanceof ScrollPane) {
				newVBox = (VBox) ((ScrollPane) parent).getContent();
			} else {
				newVBox = (VBox) parent;
			}

			((ScrollPane) App.getMainScene().getRoot()).setContent(newVBox);

			/* Check if it will be necessary to use the previous menu bar */
			if (!(loader.getController() instanceof LoginViewController)
					&& !(loader.getController() instanceof HomeViewController)) {

				MenuBar mainMenuBar = (MenuBar) mainVBox.getChildren().get(0);
				((VBox) ((ScrollPane) App.getMainScene().getRoot()).getContent()).getChildren().add(0, mainMenuBar);
			}

			T controller = loader.getController();
			controllerDependencyInjector.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	public static <T> void createDialogForm(String title, AbstractEntity obj, String fxml, Stage parentStage,
			Consumer<T> controllerDependencyInjector) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
			AnchorPane anchorPane = loader.load();

			Stage newStage = new Stage();
			Scene newScene = new Scene(anchorPane);

			((DialogForm) loader.getController()).setMyScene(newScene);

			T controller = loader.getController();
			controllerDependencyInjector.accept(controller);

			newStage.setTitle(title);
			newStage.setScene(newScene);
			newStage.initOwner(parentStage);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.setResizable(false);
			newStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading form", e.getMessage(), AlertType.ERROR);
		}
	}

	public static void addBackgroundImage(String imagePath) {
		try {
			FileInputStream stream = new FileInputStream(imagePath);

			Image image = new Image(stream);
			ImageView imageView = new ImageView(image);

			VBox mainVBox = (VBox) ((ScrollPane) App.getMainScene().getRoot()).getContent();
			AnchorPane anchorPane = (AnchorPane) mainVBox.getChildren().get(1);

			imageView.fitHeightProperty().bind(anchorPane.heightProperty());
			imageView.fitWidthProperty().bind(anchorPane.widthProperty());
			imageView.setDisable(true);

			anchorPane.getChildren().add(0, imageView);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void addImage(Scene scene, String imagePath, Double width, Double height, Double leftAnchor,
			Double topAnchor, Double rightAnchor, Double bottomAnchor) {
		try {
			FileInputStream stream = new FileInputStream(imagePath);

			Image image = new Image(stream);
			ImageView imageView = new ImageView(image);

			imageView.setBlendMode(BlendMode.MULTIPLY);
			imageView.setFitWidth(width);
			imageView.setFitHeight(height);
			imageView.setDisable(true);

			AnchorPane mainAnchorPane = (AnchorPane) scene.getRoot();
			mainAnchorPane.getChildren().add(imageView);

			AnchorPane.setLeftAnchor(imageView, leftAnchor);
			AnchorPane.setTopAnchor(imageView, topAnchor);
			AnchorPane.setRightAnchor(imageView, rightAnchor);
			AnchorPane.setBottomAnchor(imageView, bottomAnchor);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static Stage primaryStage() {
		return (Stage) App.getMainScene().getWindow();
	}

}
