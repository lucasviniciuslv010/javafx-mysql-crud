package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.listeners.DataChangeListener;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;
import com.lucasvinicius.javafx_mysql_crud.services.BrandService;

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

public class BrandListController implements Initializable, DataChangeListener {

	private BrandService service;

	@FXML
	private Button btNew;
	@FXML
	private TableView<Brand> tableViewBrand;
	@FXML
	private TableColumn<Brand, Long> tableColumnId;
	@FXML
	private TableColumn<Brand, String> tableColumnName;
	@FXML
	private TableColumn<Brand, Brand> tableColumnEdit;
	@FXML
	private TableColumn<Brand, Brand> tableColumnRemove;

	private ObservableList<Brand> obsList;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableViewPrefHeight();
		initializeTableColumnCell();
	}

	public void onBtNewAction(ActionEvent event) {
		Brand obj = new Brand();
		Stage parentStage = GUILoader.currentStage(event);
		GUILoader.createDialogForm("Enter car brand data", obj, "BrandForm.fxml", parentStage,
				(BrandFormController controller) -> {
					controller.setMyEntity(obj);
					controller.setService(new BrandService());
					controller.subscribeDataChangeListener(this);
					controller.updateFormData();
					GUILoader.addImage(controller.getMyScene(), 
							"src//main//resources//images//car.png", 
							200.0, 200.0, 425.0, 0.0, 0.0, 0.0);
				});
	}

	public void setService(BrandService service) {
		this.service = service;
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		obsList = FXCollections.observableArrayList(service.findAll());
		tableViewBrand.setItems(obsList);
		initEditButtons(this);
		initRemoveButtons();
	}

	public void tableViewPrefHeight() {
		Stage primaryStage = GUILoader.primaryStage();
		tableViewBrand.prefHeightProperty().bind(primaryStage.heightProperty());
	}

	public void initializeTableColumnCell() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	/* add an update button on each table row */
	public void initEditButtons(DataChangeListener listener) {
		tableColumnEdit.setCellValueFactory(param -> 
			new ReadOnlyObjectWrapper<Brand>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Brand, Brand>() {

			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Brand obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				getCursor();
				button.setCursor(Cursor.HAND);
				button.setOnAction(
						event -> GUILoader.createDialogForm("Enter brand data", 
									obj, "BrandForm.fxml",
								GUILoader.currentStage(event), 
								(BrandFormController controller) -> {
									controller.setMyEntity(obj);
									controller.setService(new BrandService());
									controller.subscribeDataChangeListener(listener);
									controller.updateFormData();
									GUILoader.addImage(controller.getMyScene(), 
											"src//main//resources//images//car.png", 
											200.0, 200.0, 425.0, 0.0, 0.0, 0.0);
								}));
			}
		});
	}

	/* add an remove button on each table row */
	public void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> 
			new ReadOnlyObjectWrapper<Brand>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Brand, Brand>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Brand obj, boolean empty) {
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

	public void removeEntity(Brand obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", 
				"Are you sure to delete?");
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			service.removeById(obj.getId());
			updateTableView();
		}
	}
	
}
