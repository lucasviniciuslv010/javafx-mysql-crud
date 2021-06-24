package com.lucasvinicius.javafx_mysql_crud.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.lucasvinicius.javafx_mysql_crud.gui.GUILoader;
import com.lucasvinicius.javafx_mysql_crud.gui.listeners.DataChangeListener;
import com.lucasvinicius.javafx_mysql_crud.model.User;
import com.lucasvinicius.javafx_mysql_crud.services.UserService;
import com.lucasvinicius.javafx_mysql_crud.util.ModelUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PersonalInformationController implements Initializable, DataChangeListener {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private User loggedInUser;

	@FXML
	private Button btEdit;
	@FXML
	private Button btPassword;
	@FXML
	private Label labelId, labelFullName, labelAge, labelEmail, labelPhone, labelContractNumber, labelDateOfBirth;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public void onBtEditAction(ActionEvent event) {
		Stage parentStage = GUILoader.currentStage(event);
		GUILoader.createDialogForm("Enter employee data", loggedInUser, "RegistrationForm.fxml", parentStage,
				(RegistrationFormController controller) -> {
					controller.setService(new UserService());
					controller.setMyEntity(loggedInUser);
					controller.subscribeDataChangeListener(this);
					controller.updateFormData();
					GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//badge.png", 
							240.0, 240.0, 430.0, 0.0, 0.0, 170.0);
				});
	}

	public void onBtPasswordAction(ActionEvent event) {
		Stage parentStage = GUILoader.currentStage(event);
		GUILoader.createDialogForm("Enter a new password", loggedInUser, "PasswordForm.fxml",
				parentStage, (PasswordFormController controller) -> {
					controller.setService(new UserService());
					controller.setMyUser(loggedInUser);
					GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//padlock.png", 
							140.0, 160.0, 500.0, 10.0, 15.0, 50.0);
				});
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public void updateLabels() {
		labelId.setText(String.valueOf(loggedInUser.getId()));
		labelFullName.setText(loggedInUser.getFullName());
		labelAge.setText(String.valueOf(getAge(loggedInUser.getDateOfBirth())));
		labelEmail.setText(loggedInUser.getEmail());
		labelPhone.setText(ModelUtil.phoneFormat(loggedInUser.getPhone()));
		labelContractNumber.setText(ModelUtil.contractNumberFormat(loggedInUser.getContractNumber()));
		labelDateOfBirth.setText(sdf.format(loggedInUser.getDateOfBirth()));
	}

	public int getAge(Date dateOfBirth) {
		String dateOfBirthStr = sdf.format(dateOfBirth);
		return ModelUtil.calculateAge(dateOfBirthStr, "dd/MM/yyyy");
	}

	@Override
	public void onDataChanged() {
		updateLabels();
	}
	
}
