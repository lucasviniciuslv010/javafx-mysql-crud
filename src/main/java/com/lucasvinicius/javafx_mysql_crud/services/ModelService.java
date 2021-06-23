package com.lucasvinicius.javafx_mysql_crud.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.lucasvinicius.javafx_mysql_crud.dao.ModelDAO;
import com.lucasvinicius.javafx_mysql_crud.dao.factory.DAOFactory;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.model.Model;
import com.lucasvinicius.javafx_mysql_crud.util.JPAUtil;

import javafx.scene.control.Alert.AlertType;

public class ModelService {

	public Model save(Model model) {

		if (model != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			ModelDAO dao = DAOFactory.createModelDAO(persistenceContext);

			try {
				persistenceContext.getTransaction().begin();
				model = dao.save(model);
				persistenceContext.getTransaction().commit();
			} catch (RuntimeException e) {
				persistenceContext.getTransaction().rollback();
				Alerts.showAlert("Unexpected Error", "Error saving model", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return model;
	}

	public boolean removeById(Long id) {

		boolean successfully = false;

		if (id != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			ModelDAO dao = DAOFactory.createModelDAO(persistenceContext);

			try {
				persistenceContext.getTransaction().begin();
				successfully = dao.remove(findById(id));
				persistenceContext.getTransaction().commit();
			} catch (RuntimeException e) {
				persistenceContext.getTransaction().rollback();
				Alerts.showAlert("Unexpected Error", "Error removing model", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return successfully;
	}

	public Model findById(Long id) {

		Model model = null;

		if (id != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			ModelDAO dao = DAOFactory.createModelDAO(persistenceContext);

			try {
				model = dao.findById(id);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return model;
	}

	public List<Model> findByName(String name) {
		
		List<Model> models = new ArrayList<>();
		
		if (name != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			ModelDAO dao = DAOFactory.createModelDAO(persistenceContext);
			
			try {
				models = dao.findByName(name);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}
		return models;
	}

	public List<Model> findAll() {
		
		List<Model> models = new ArrayList<>();
		
		EntityManager persistenceContext = JPAUtil.createEntityManager();
		ModelDAO dao = DAOFactory.createModelDAO(persistenceContext);
		try {
			models = dao.findAll();
		} catch (RuntimeException e) {
			Alerts.showAlert("Unexpected Error", "Error fetching data", e.getMessage(), AlertType.ERROR);
		} finally {
			persistenceContext.close();
		}

		return models;
	}

}