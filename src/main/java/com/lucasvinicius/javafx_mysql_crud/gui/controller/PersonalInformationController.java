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

	private User user;

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
		GUILoader.createDialogForm("Enter employee data", user, "RegistrationForm.fxml", parentStage,
				(RegistrationFormController controller) -> {
					controller.setService(new UserService());
					controller.setUser(user);
					controller.subscribeDataChangeListener(this);
					controller.updateFormData();
					GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//badge.png", 
							240.0, 240.0, 430.0, 0.0, 0.0, 170.0);
				});
	}

	public void onBtPasswordAction(ActionEvent event) {
		Stage parentStage = GUILoader.currentStage(event);
		GUILoader.createDialogForm("Enter a new password", user, "PasswordForm.fxml",
				parentStage, (PasswordFormController controller) -> {
					controller.setService(new UserService());
					controller.setUser(user);
					GUILoader.addImage(controller.getMyScene(), "src//main//resources//images//padlock.png", 
							140.0, 160.0, 475.0, 10.0, 15.0, 50.0);
				});
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void updateLabels() {
		labelId.setText(String.valueOf(user.getId()));
		labelFullName.setText(user.getFullName());
		labelAge.setText(String.valueOf(getAge(user.getDateOfBirth())));
		labelEmail.setText(user.getEmail());
		labelPhone.setText(ModelUtil.phoneFormat(user.getPhone()));
		labelContractNumber.setText(ModelUtil.contractNumberFormat(user.getContractNumber()));
		labelDateOfBirth.setText(sdf.format(user.getDateOfBirth()));
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
