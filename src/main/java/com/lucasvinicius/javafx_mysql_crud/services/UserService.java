package com.lucasvinicius.javafx_mysql_crud.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.lucasvinicius.javafx_mysql_crud.dao.UserDAO;
import com.lucasvinicius.javafx_mysql_crud.dao.factory.DAOFactory;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.model.User;
import com.lucasvinicius.javafx_mysql_crud.util.JPAUtil;

import javafx.scene.control.Alert.AlertType;

public class UserService {

	public User save(User user) {

		if (user != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			UserDAO dao = DAOFactory.createUserDAO(persistenceContext);

			try {
				persistenceContext.getTransaction().begin();
				user = dao.save(user);
				persistenceContext.getTransaction().commit();
			} catch (RuntimeException e) {
				persistenceContext.getTransaction().rollback();
				Alerts.showAlert("Unexpected Error", "Error saving user", 
						e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return user;
	}

	public boolean removeById(Long id) {

		boolean successfully = false;

		if (id != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			UserDAO dao = DAOFactory.createUserDAO(persistenceContext);

			try {
				persistenceContext.getTransaction().begin();
				successfully = dao.remove(findById(id));
				persistenceContext.getTransaction().commit();
			} catch (RuntimeException e) {
				persistenceContext.getTransaction().rollback();
				Alerts.showAlert("Unexpected Error", "Error removing user", 
						e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return successfully;
	}

	public User findById(Long id) {

		User user = null;

		if (id != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			UserDAO dao = DAOFactory.createUserDAO(persistenceContext);

			try {
				user = dao.findById(id);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", 
						e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return user;
	}

	public List<User> findByName(String name) {
		
		List<User> users = new ArrayList<>();
		
		if (name != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			UserDAO dao = DAOFactory.createUserDAO(persistenceContext);
			
			try {
				users = dao.findByName(name);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", 
						e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}
		
		return users;
	}

	public User findByEmail(String email) {

		User user = null;
		
		if (email != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			UserDAO dao = DAOFactory.createUserDAO(persistenceContext);
			
			try {
				user = dao.findByEmail(email);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", 
						e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}
		
		return user;
	}

	public User findByContractNumber(String contractNumber) {

		User user = null;
		
		if (contractNumber != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			UserDAO dao = DAOFactory.createUserDAO(persistenceContext);
			
			try {
				user = dao.findByContractNumber(contractNumber);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", 
						e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}
		
		return user;
	}

	public List<User> findAll() {
		
		List<User> users = new ArrayList<>();
		
		EntityManager persistenceContext = JPAUtil.createEntityManager();
		UserDAO dao = DAOFactory.createUserDAO(persistenceContext);
		try {
			users = dao.findAll();
		} catch (RuntimeException e) {
			Alerts.showAlert("Unexpected Error", "Error fetching data", 
					e.getMessage(), AlertType.ERROR);
		} finally {
			persistenceContext.close();
		}

		return users;
	}

}