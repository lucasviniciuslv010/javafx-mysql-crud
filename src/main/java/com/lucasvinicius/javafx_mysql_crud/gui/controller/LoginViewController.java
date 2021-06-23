package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Constraints;
import com.lucasvinicius.javafx_mysql_crud.model.User;
import com.lucasvinicius.javafx_mysql_crud.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController implements Initializable {

	private UserService service;

	@FXML
	private MenuItem menuItemLogin;
	@FXML
	private MenuItem menuItemAbout;
	@FXML
	private TextField txtEmailAddress;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label labelErrorEmail;
	@FXML
	private Label labelErrorPassword;
	@FXML
	private Button btSignIn;
	@FXML
	private Button btSignUp;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeConstraints();
	}

	public void onMenuItemLoginAction() {
		GUILoader.loadView("LoginView.fxml", (LoginViewController controller) -> {
			controller.setService(new UserService());
			GUILoader.addBackgroundImage("src//main//resources//images//background.png");
		});
	}

	public void onMenuItemAboutAction() {
		GUILoader.loadView("About.fxml", (AboutController controller) -> {
			GUILoader.addBackgroundImage("src//main//resources//images//background.png");
		});
	}

	public void onBtSignInAction() {
		if (validateEmailAndPassword(txtEmailAddress.getText(), passwordField.getText()))
			GUILoader.loadView("HomeView.fxml", (HomeViewController controller) -> {
				controller.setUser(service.findByEmail(txtEmailAddress.getText()));
				GUILoader.addBackgroundImage("src//main//resources//images//cars.png");
			});
	}

	public void onBtSignUpAction(ActionEvent event) {
		User obj = new User();
		Stage parentStage = GUILoader.currentStage(event);
		GUILoader.createDialogForm("Enter employee data", obj, "RegistrationForm.fxml", parentStage,
				(RegistrationFormController controller) -> {
					controller.setService(new UserService());
					controller.setUser(obj);
					controller.updateFormData();
					GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//badge.png", 
							240.0, 240.0, 430.0, 0.0, 0.0, 170.0);
				});
	}

	public boolean validateEmailAndPassword(String email, String password) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		User user = service.findByEmail(email);

		if (user == null) {
			labelErrorPassword.setText("");
			labelErrorEmail.setText("Email address invalid");
			return false;
		}
		if (!(user.getPassword().equals(password))) {
			labelErrorEmail.setText("");
			labelErrorPassword.setText("Incorrect password");
			return false;
		}

		return true;
	}

	public void initializeConstraints() {
		Constraints.setTextFieldMaxLength(txtEmailAddress, 120);
	}

	public void setService(UserService service) {
		this.service = service;
	}
	
}
