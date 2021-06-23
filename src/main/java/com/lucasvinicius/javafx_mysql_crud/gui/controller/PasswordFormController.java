package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.exceptions.ValidationException;
import com.lucasvinicius.javafx_mysql_crud.model.User;
import com.lucasvinicius.javafx_mysql_crud.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class PasswordFormController implements Initializable, DialogForm {
	
	private Scene myScene;
	
	private User user;
	
	private UserService service;
	
	@FXML
	private PasswordField currentPassword, newPassword, newPasswordConfirm;
	@FXML
	private Label labelErrorCurrentPassword, labelErrorNewPassword, labelErrorNewPasswordConfirm;
	@FXML
	private Button btSave, btCancel;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	public void onBtSaveAction(ActionEvent event) {
		try {
			User obj = getFormData();
			updateUser(obj);
			service.save(user);
			GUILoader.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessage(e.getErrors());
		}
	}
	
	public void onBtCancelAction(ActionEvent event) {
		GUILoader.currentStage(event).close();
	}
	
	public void setService(UserService service) {
		this.service = service;
	}
	
	public void setErrorMessage(Map<String, String> errors) {
		labelErrorCurrentPassword.setText(errors.containsKey("currentPassword") 
				? errors.get("currentPassword") : "");
		labelErrorNewPassword.setText(errors.containsKey("newPassword") 
				? errors.get("newPassword") : "");
		labelErrorNewPasswordConfirm.setText(errors.containsKey("newPasswordConfirm") 
				? errors.get("newPasswordConfirm") : "");
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public void setMyScene(Scene myScene) {
		this.myScene = myScene;
	}
	
	@Override
	public Scene getMyScene() {
		return this.myScene;
	}
	
	public User getFormData() throws ValidationException {
		ValidationException exception = new ValidationException("Validation Error");
		
		User obj = new User();
		
		if (!(currentPassword.getText().equals(user.getPassword()))) {
			exception.addError("currentPassword", "Incorrect current password");
		} else if (newPassword.getText() == null || newPassword.getText().trim().equals("")) {
			exception.addError("newPassword", "The field cannot be empty");
		} else if (newPassword.getText().length() < 8) { 
			exception.addError("newPassword", "Password too short");
		} else if (!newPassword.getText().equals(newPasswordConfirm.getText())) {
			exception.addError("newPasswordConfirm", "Passwords do not match");
		} 
		
		obj.setPassword(newPassword.getText());
		
		if (exception.getErrors().size() > 0)
			throw exception;
		
		return obj;
	}
	
	public void updateUser(User obj) {
		user.setPassword(obj.getPassword());
	}
	
}
