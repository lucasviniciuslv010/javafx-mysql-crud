package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.exceptions.ValidationException;
import com.lucasvinicius.javafx_mysql_crud.gui.listeners.DataChangeListener;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Constraints;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;
import com.lucasvinicius.javafx_mysql_crud.model.Model;
import com.lucasvinicius.javafx_mysql_crud.services.BrandService;
import com.lucasvinicius.javafx_mysql_crud.services.ModelService;
import com.lucasvinicius.javafx_mysql_crud.util.ModelUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModelFormController implements Initializable, DialogForm {

	private Scene myScene;
	
	private Model myModel;

	private ModelService service;

	private BrandService brandService;

	private List<DataChangeListener> listeners = new ArrayList<>();

	@FXML
	private TextField txtId, txtName, txtPrice, txtYear;
	@FXML
	private ComboBox<String> comboBoxStatus, comboBoxBodywork;
	@FXML
	private ComboBox<Brand> comboBoxBrand;
	@FXML
	private Label labelErrorName, labelErrorPrice, labelErrorStatus, labelErrorBodywork, labelErrorBrand,
			labelErrorYear, labelErrorCar;
	@FXML
	private Button btSave, btCancel;

	private ObservableList<String> obsListString;

	private ObservableList<Brand> obsListBrand;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeContraints();
		statusList();
		bodyworkList();
	}

	public void onBtSaveAction(ActionEvent event) {
		try {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			} else if (brandService == null) {
				throw new IllegalStateException("BrandService was null");
			}
			Model obj = getFormData();
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

	public void setMyModel(Model myModel) {
		this.myModel = myModel;
	}

	public void setServices(ModelService service, BrandService brandService) {
		this.service = service;
		this.brandService = brandService;
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
		labelErrorName.setText(errors.containsKey("name") ? errors.get("name") : "");
		labelErrorPrice.setText(errors.containsKey("price") ? errors.get("price") : "");
		labelErrorYear.setText(errors.containsKey("year") ? errors.get("year") : "");
		labelErrorStatus.setText(errors.containsKey("status") ? errors.get("status") : "");
		labelErrorBodywork.setText(errors.containsKey("bodywork") ? errors.get("bodywork") : "");
		labelErrorBrand.setText(errors.containsKey("brand") ? errors.get("brand") : "");
		labelErrorCar.setText(errors.containsKey("car") ? errors.get("car") : "");
	}

	/* Providing 2 types of status for the car */
	public void statusList() {
		String[] arrayStatus = { "New", "Used" };
		obsListString = FXCollections.observableArrayList(arrayStatus);
		comboBoxStatus.setItems(obsListString);
	}

	/* Provide 8 types of car category */
	public void bodyworkList() {
		List<String> list = new ArrayList<String>();
		list.add("Hatchback");
		list.add("Crossover");
		list.add("Cupê");
		list.add("Sport");
		list.add("Jeep");
		list.add("Pickup Truck");
		list.add("Sedan");
		list.add("SUV");
		obsListString = FXCollections.observableArrayList(list);
		comboBoxBodywork.setItems(obsListString);
	}

	public void updateComboBoxBrand() {
		if (brandService == null) {
			throw new IllegalArgumentException("Brand Service was null");
		}
		obsListBrand = FXCollections.observableArrayList(brandService.findAll());
		comboBoxBrand.setItems(obsListBrand);
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		listeners.add(listener);
	}

	public void notifyDataChangeListener() {
		for (DataChangeListener listener : listeners) {
			listener.onDataChanged();
		}
	}

	public Model getFormData() throws ValidationException {
		ValidationException exception = new ValidationException("Validation error");

		Model model = new Model();

		model.setId(ModelUtil.tryParseLong(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals(""))
			exception.addError("name", "The field cannot be empty");
		model.setName(txtName.getText());

		if (txtPrice.getText() == null || txtPrice.getText().trim().equals(""))
			exception.addError("price", "The field cannot be empty");
		model.setPrice(ModelUtil.tryParseDouble(txtPrice.getText()));

		if (txtYear.getText() == null || txtYear.getText().trim().equals(""))
			exception.addError("year", "Enter the year of the car");
		model.setYear(txtYear.getText());

		if (comboBoxStatus.getValue() == null)
			exception.addError("status", "Select car status");
		model.setStatus(comboBoxStatus.getSelectionModel().getSelectedItem());

		if (comboBoxBodywork.getValue() == null)
			exception.addError("bodywork", "Select car bodywork");
		model.setBodywork(comboBoxBodywork.getSelectionModel().getSelectedItem());

		if (comboBoxBrand.getValue() == null)
			exception.addError("brand", "Select car brand");
		model.setBrand(comboBoxBrand.getSelectionModel().getSelectedItem());

		if (checkDuplicity(txtName.getText(), model))
			exception.addError("car", "Car already registered");

		if (exception.getErrors().size() > 0)
			throw exception;

		return model;
	}

	public void updateFormData() {
		if (myModel == null) {
			throw new IllegalStateException("Entity was null");
		}
		Locale.setDefault(Locale.US);

		txtId.setText(String.valueOf(myModel.getId()));
		txtId.setOpacity(0.5);
		txtName.setText(myModel.getName());
		txtPrice.setText(String.format("%.2f", myModel.getPrice()));
		txtYear.setText(myModel.getYear());
		if (myModel.getStatus() != null)
			comboBoxStatus.setValue(myModel.getStatus());
		if (myModel.getBodywork() != null)
			comboBoxBodywork.setValue(myModel.getBodywork());
		if (myModel.getBrand() != null)
			comboBoxBrand.setValue(myModel.getBrand());
	}

	public boolean checkDuplicity(String carName, Model model) {
		List<Model> result = service.findByName(carName);
		if (result.size() > 0) {
			for (Model m : result) {
				if (m.getStatus().equals(model.getStatus()) && m.getYear().equals(model.getYear())
						&& m.getBodywork().equals(model.getBodywork())
						&& !m.getId().equals(ModelUtil.tryParseLong(txtId.getText()))) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public void initializeContraints() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldDouble(txtPrice);
	}
	
}
