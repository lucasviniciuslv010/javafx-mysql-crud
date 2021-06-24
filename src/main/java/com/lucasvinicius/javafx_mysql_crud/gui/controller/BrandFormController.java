package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.exceptions.ValidationException;
import com.lucasvinicius.javafx_mysql_crud.gui.listeners.DataChangeListener;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Constraints;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;
import com.lucasvinicius.javafx_mysql_crud.services.BrandService;
import com.lucasvinicius.javafx_mysql_crud.util.ModelUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BrandFormController implements Initializable, DialogForm {

	private Scene myScene;

	private Brand myEntity;

	private BrandService service;

	private List<DataChangeListener> listeners = new ArrayList<>();

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelErrorName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeContraints();
	}

	public void onBtSaveAction(ActionEvent event) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			Brand obj = getFormData();
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

	public void setService(BrandService service) {
		this.service = service;
	}

	public void setMyEntity(Brand myEntity) {
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

	public void subscribeDataChangeListener(DataChangeListener listener) {
		listeners.add(listener);
	}

	/* Notifies listeners that the data being shown to the user has been updated */
	public void notifyDataChangeListener() {
		for (DataChangeListener listener : listeners) {
			listener.onDataChanged();
		}
	}

	/*
	 * Returning an object with the data that was filled in the form Throws
	 * ValidationException if any data is invalid
	 */
	public Brand getFormData() throws ValidationException {
		ValidationException exception = new ValidationException("Validation error");

		Brand obj = new Brand();

		obj.setId(ModelUtil.tryParseLong(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "The field cannot be empty");
		} else if (checkDuplicity(txtName.getText())) {
			exception.addError("name", "This brand already exists");
		} 
		
		obj.setName(txtName.getText());
		
		if (exception.getErrors().size() > 0)
			throw exception;

		return obj;
	}

	/* Fill in the form with my entity data */
	public void updateFormData() {
		if (myEntity == null) {
			throw new IllegalStateException("My entity was null");
		}
		txtId.setText(String.valueOf(myEntity.getId()));
		txtName.setText(myEntity.getName());
	}

	/*
	 * Checks if there is already a car brand with that name in the database to
	 * avoid duplication
	 */
	public boolean checkDuplicity(String name) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		Brand result = service.findByName(name);
		if (result != null && !(result.getId().equals(myEntity.getId()))) {
			return true;
		}
		return false;
	}

	/*
	 * Sets an error message to an invalid field Error messages are in the
	 * getFormData method
	 */
	public void setErrorMessage(Map<String, String> errors) {
		labelErrorName.setText(errors.containsKey("name") ? errors.get("name") : "");
	}

	public void initializeContraints() {
		Constraints.setTextFieldInteger(txtId);
	}

}
