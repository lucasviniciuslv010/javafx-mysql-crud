package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.exceptions.ValidationException;
import com.lucasvinicius.javafx_mysql_crud.gui.listeners.DataChangeListener;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Constraints;
import com.lucasvinicius.javafx_mysql_crud.gui.util.ViewUtil;
import com.lucasvinicius.javafx_mysql_crud.model.User;
import com.lucasvinicius.javafx_mysql_crud.services.UserService;
import com.lucasvinicius.javafx_mysql_crud.util.ModelUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationFormController implements Initializable, DialogForm {
	
	private Scene myScene;
	
	private User myEntity;
	
	private UserService service;
	
	private List<DataChangeListener> listeners = new ArrayList<DataChangeListener>();
	
	@FXML
	private TextField txtId, txtFullName, txtEmail, txtPhone, txtContractNumber;
	@FXML
	private DatePicker dpDateOfBirth;
	@FXML
	private PasswordField passwordField, passwordFieldConfirm;
	@FXML
	private Label labelErrorFullName, labelErrorEmail, labelErrorDateOfBirth, 
				labelErrorPhone, labelErrorPassword, labelErrorConfirmPassword,
				labelErrorContractNumber;
	@FXML
	private Button btSave, btCancel;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializingConstraints();
		ViewUtil.formatDatePicker(dpDateOfBirth, "dd/MM/yyyy");
	}
	
	public void onBtSaveAction(ActionEvent event) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			User obj = getFormData();
			updateMyEntity(obj);
			service.save(obj);
			notifyDataChangeListener();
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
	
	public void setMyEntity(User myEntity) {
		this.myEntity = myEntity;
	}
	
	@Override
	public void setMyScene(Scene myScene) {
		this.myScene = myScene;
	}
	
	@Override
	public Scene getMyScene() {
		return this.myScene;
	}
	
	public void setErrorMessage(Map<String, String> errors) {
		labelErrorFullName.setText(errors.containsKey("fullName") ? errors.get("fullName") : "");
		labelErrorEmail.setText(errors.containsKey("email") ? errors.get("email") : "");
		labelErrorDateOfBirth.setText(errors.containsKey("dateOfBirth") ? errors.get("dateOfBirth") : "");
		labelErrorPhone.setText(errors.containsKey("phone") ? errors.get("phone") : "");
		labelErrorPassword.setText(errors.containsKey("password") ? errors.get("password") : "");
		labelErrorConfirmPassword.setText(errors.containsKey("confirmPassword") ? errors.get("confirmPassword") : "");
		labelErrorContractNumber.setText(errors.containsKey("contractNumber") ? errors.get("contractNumber") : "");
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		listeners.add(listener);
	}
	
	public void notifyDataChangeListener() {
		for (DataChangeListener listener : listeners) {
			listener.onDataChanged();
		}
	}
	
	public void initializingConstraints() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldString(txtFullName);
		Constraints.setTextFieldMaxLength(txtFullName, 120);
		Constraints.setTextFieldMaxLength(txtEmail, 120);
		Constraints.setTextFieldInteger(txtPhone);
		Constraints.setTextFieldMaxLength(txtPhone, 11);
		Constraints.setTextFieldInteger(txtContractNumber);
		Constraints.setTextFieldMaxLength(txtContractNumber, 7);
	}
	
	public User getFormData() throws ValidationException {
		ValidationException exception = new ValidationException("Validation error");
		
		User obj = new User();
		
		obj.setId(ModelUtil.tryParseLong(txtId.getText()));
		
		if (txtFullName.getText() == null || txtFullName.getText().trim().equals("")) {
			exception.addError("fullName", "The field cannot be empty");
		} else {
			obj.setFullName(ViewUtil.toTitledCase(txtFullName.getText()));
		}
		
		if (txtContractNumber.getText() == null || txtContractNumber.getText().trim().equals("")) {
			exception.addError("contractNumber", "The field cannot be empty");
		} 
		else if (txtContractNumber.getText().length() < 7) {
			exception.addError("contractNumber", "Very short contract number");
		} 
		else if (!(checkContractNumber(txtContractNumber.getText(), ModelUtil.tryParseLong(txtId.getText())))) {
			exception.addError("contractNumber", "Contract already registered");
		} else {
			obj.setContractNumber(txtContractNumber.getText());
		}
		
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "The field cannot be empty");
		} 
		else if (!(checkEmail(txtEmail.getText(), ModelUtil.tryParseLong(txtId.getText())))) {
			exception.addError("email", "Email already registered");
		}
		else {
			obj.setEmail(txtEmail.getText().toLowerCase());
		}
	
		if (dpDateOfBirth.getValue() == null) {
			exception.addError("dateOfBirth", "Invalid date of birth");
		} else {
			Instant instant = Instant.from(dpDateOfBirth.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDateOfBirth(Date.from(instant));
		}
		
		if (txtPhone.getText() == null || txtPhone.getText().trim().equals("") || txtPhone.getText().length() < 11) {
			exception.addError("phone", "Invalid phone number");
		}
		obj.setPhone(txtPhone.getText());
		
		if (passwordField.getText() == null || passwordField.getText().trim().equals("")) {
			exception.addError("password", "The field cannot be empty");
		} else if (passwordField.getText().length() < 8) {
			exception.addError("password", "Password too short");
		} else if (!(passwordField.getText().equals(passwordFieldConfirm.getText()))) {
			exception.addError("confirmPassword", "Password do not match");
		}
		obj.setPassword(passwordField.getText());
		
		if (exception.getErrors().size() > 0)
			throw exception;
			
		return obj;
	}
	
	public void updateFormData() {
		if (myEntity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(myEntity.getId()));
		txtFullName.setText(myEntity.getFullName());
		txtContractNumber.setText(myEntity.getContractNumber());
		txtEmail.setText(myEntity.getEmail());
		if (myEntity.getDateOfBirth() != null) {
			dpDateOfBirth.setValue(LocalDate.ofInstant(myEntity.getDateOfBirth().toInstant(), ZoneId.systemDefault()));
		}
		txtPhone.setText(myEntity.getPhone());
		passwordField.setText(myEntity.getPassword());
		passwordFieldConfirm.setText(myEntity.getPassword());
		
		if (txtId.getText() != null && !(txtId.getText().trim().equals(""))) {
			txtContractNumber.setEditable(false);
			txtContractNumber.setOpacity(0.5);
			passwordField.setEditable(false);
			passwordField.setOpacity(0.5);
			passwordFieldConfirm.setEditable(false);
			passwordFieldConfirm.setOpacity(0.5);
		}
	}
	
	public boolean checkEmail(String email, Long id) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		User result = service.findByEmail(email);
		
		if (result == null) 
			return true;
		
		if (result.getId().equals(id)) 
			return true;
		
		return false;
	}
	
	public boolean checkContractNumber(String contractNumber, Long id) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		User result = service.findByContractNumber(contractNumber);
		
		if (result == null) 
			return true;
		
		if (result.getId().equals(id)) 
			return true;
		
		return false;
	}
	
	public void updateMyEntity(User obj) {
		myEntity.setFullName(obj.getFullName());
		myEntity.setEmail(obj.getEmail());
		myEntity.setDateOfBirth(obj.getDateOfBirth());
		myEntity.setPhone(obj.getPhone());
	}
	
}
