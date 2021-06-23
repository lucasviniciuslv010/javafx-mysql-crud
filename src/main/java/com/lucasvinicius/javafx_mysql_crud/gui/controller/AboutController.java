package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AboutController implements Initializable {

	@FXML
	private TextArea textArea;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		textAreaPrefHeight();
		textAreaPrefWidth();
		textArea.setEditable(false);
	}
	
	public void textAreaPrefHeight() {
		Stage mainStage = (Stage) App.getMainScene().getWindow();
		textArea.prefHeightProperty().bind(mainStage.heightProperty());
	}
	
	public void textAreaPrefWidth() {
		Stage mainStage = (Stage) App.getMainScene().getWindow();
		textArea.prefWidthProperty().bind(mainStage.widthProperty());
	}
	
}
