package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.listeners.DataChangeListener;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.gui.util.ViewUtil;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;
import com.lucasvinicius.javafx_mysql_crud.model.Model;
import com.lucasvinicius.javafx_mysql_crud.services.BrandService;
import com.lucasvinicius.javafx_mysql_crud.services.ModelService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModelListController implements Initializable, DataChangeListener {

	private ModelService service;

	@FXML
	private Button btNew;
	@FXML
	private TableView<Model> tableViewModel;
	@FXML
	private TableColumn<Model, Long> tableColumnId;
	@FXML
	private TableColumn<Model, String> tableColumnName;
	@FXML
	private TableColumn<Model, Double> tableColumnPrice;
	@FXML
	private TableColumn<Model, String> tableColumnYear;
	@FXML
	private TableColumn<Model, Integer> tableColumnStatus;
	@FXML
	private TableColumn<Model, Double> tableColumnBodywork;
	@FXML
	private TableColumn<Model, Brand> tableColumnBrand;
	@FXML
	private TableColumn<Model, Model> tableColumnEdit;
	@FXML
	private TableColumn<Model, Model> tableColumnRemove;

	private ObservableList<Model> obsList;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeTableColumnCell();
		tableViewPrefHeight();
		tableViewPrefWidth();
	}

	public void onBtNewAction(ActionEvent event) {
		Model obj = new Model();
		Stage parentStage = GUILoader.currentStage(event);
		GUILoader.createDialogForm("Enter car model data", obj, "ModelForm.fxml", parentStage,
				(ModelFormController controller) -> {
					controller.setMyEntity(obj);
					controller.setServices(new ModelService(), new BrandService());
					controller.updateComboBoxBrand();
					controller.updateFormData();
					controller.subscribeDataChangeListener(this);
					GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//car.png", 
							220.0, 215.0, 415.0, 120.0, 10.0, 60.0);
				});
	}

	public void setService(ModelService service) {
		this.service = service;
	}

	public void updateTableView() {
		obsList = FXCollections.observableArrayList(service.findAll());
		tableViewModel.setItems(obsList);
		initEditButtons(this);
		initRemoveButtons();
	}

	public void initializeTableColumnCell() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableColumnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		ViewUtil.formatTableColumnDouble(tableColumnPrice, 2);
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnBodywork.setCellValueFactory(new PropertyValueFactory<>("bodywork"));
		tableColumnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
	}

	public void tableViewPrefHeight() {
		Stage primaryStage = GUILoader.primaryStage();
		tableViewModel.prefHeightProperty().bind(primaryStage.heightProperty());
	}

	public void tableViewPrefWidth() {
		Stage primaryStage = GUILoader.primaryStage();
		tableViewModel.prefWidthProperty().bind(primaryStage.widthProperty());
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	/* add an update button on each table row */
	public void initEditButtons(DataChangeListener listener) {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Model>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Model, Model>() {

			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Model obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				getCursor();
				button.setCursor(Cursor.HAND);
				button.setOnAction(event -> GUILoader.createDialogForm("Enter car model data", obj,
						"ModelForm.fxml", GUILoader.currentStage(event),
						(ModelFormController controller) -> {
							controller.setMyEntity(obj);
							controller.setServices(new ModelService(), new BrandService());
							controller.updateComboBoxBrand();
							controller.updateFormData();
							controller.subscribeDataChangeListener(listener);
							GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//car.png", 
									220.0, 215.0, 415.0, 120.0, 10.0, 60.0);
						}));
			}
		});
	}

	/* add an remove button on each table row */
	public void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Model>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Model, Model>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Model obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				getCursor();
				button.setCursor(Cursor.HAND);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	public void removeEntity(Model obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			service.removeById(obj.getId());
			updateTableView();
		}
	}
	
}
