  package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.model.User;
import com.lucasvinicius.javafx_mysql_crud.services.BrandService;
import com.lucasvinicius.javafx_mysql_crud.services.ModelService;
import com.lucasvinicius.javafx_mysql_crud.services.UserService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

public class HomeViewController implements Initializable {

	private User user;
	
	@FXML
	private MenuItem menuItemHome;
	@FXML
	private MenuItem menuItemModels;
	@FXML
	private MenuItem menuItemBrands;
	@FXML
	private MenuItem menuItemPersonalInformation;
	@FXML
	private MenuItem menuItemDisconnect;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	public void onMenuItemHomeAction() {
		GUILoader.loadView("HomeView.fxml", (HomeViewController controller) -> {
			controller.setUser(user);
			GUILoader.addBackgroundImage("src//main//resources//images//cars.png");
		});
	}
	
	public void onMenuItemModelsAction() {
		GUILoader.loadView("ModelList.fxml", (ModelListController controller) -> {
			controller.setService(new ModelService());
			controller.updateTableView();
			GUILoader.addBackgroundImage("src//main//resources//images//background.png");
		});
	}
	
	public void onMenuItemBrandsAction() {
		GUILoader.loadView("BrandList.fxml", (BrandListController controller) -> {
			controller.setService(new BrandService());
			controller.updateTableView();
			GUILoader.addBackgroundImage("src//main//resources//images//background.png");
		});
	}
	
	public void onMenuItemPersonalInformationAction() {
		GUILoader.loadView("PersonalInformationView.fxml", (PersonalInformationController controller) -> {
			controller.setUser(user);
			controller.updateLabels();
			GUILoader.addBackgroundImage("src//main//resources//images//background.png");
		});
	}
	
	public void onMenuItemDisconnectAction() {
		Optional<ButtonType> result = Alerts.showConfirmation("Disconnect", "Are you sure you want to disconnect?");
		
		if (result.get() == ButtonType.OK) {
			GUILoader.loadView("LoginView.fxml", (LoginViewController controller) -> {
				controller.setService(new UserService());
				GUILoader.addBackgroundImage("src//main//resources//images//background.png");
			});
		}
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
