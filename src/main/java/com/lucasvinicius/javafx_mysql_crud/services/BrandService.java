package com.lucasvinicius.javafx_mysql_crud.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.lucasvinicius.javafx_mysql_crud.dao.BrandDAO;
import com.lucasvinicius.javafx_mysql_crud.dao.factory.DAOFactory;
import com.lucasvinicius.javafx_mysql_crud.gui.util.Alerts;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;
import com.lucasvinicius.javafx_mysql_crud.util.JPAUtil;

import javafx.scene.control.Alert.AlertType;

public class BrandService {

	public Brand save(Brand brand) {

		if (brand != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			BrandDAO dao = DAOFactory.createBrandDAO(persistenceContext);

			try {
				persistenceContext.getTransaction().begin();
				brand = dao.save(brand);
				persistenceContext.getTransaction().commit();
			} catch (RuntimeException e) {
				persistenceContext.getTransaction().rollback();
				Alerts.showAlert("Unexpected Error", "Error saving brand", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return brand;
	}

	public boolean removeById(Long id) {

		boolean successfully = false;

		if (id != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			BrandDAO dao = DAOFactory.createBrandDAO(persistenceContext);

			try {
				persistenceContext.getTransaction().begin();
				successfully = dao.remove(findById(id));
				persistenceContext.getTransaction().commit();
			} catch (RuntimeException e) {
				persistenceContext.getTransaction().rollback();
				Alerts.showAlert("Unexpected Error", "Error removing brand", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return successfully;
	}

	public Brand findById(Long id) {

		Brand brand = null;

		if (id != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			BrandDAO dao = DAOFactory.createBrandDAO(persistenceContext);

			try {
				brand = dao.findById(id);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}

		return brand;
	}

	public Brand findByName(String name) {

		Brand brand = null;
		
		if (name != null) {
			EntityManager persistenceContext = JPAUtil.createEntityManager();
			BrandDAO dao = DAOFactory.createBrandDAO(persistenceContext);
			
			try {
				brand = dao.findByName(name);
			} catch (RuntimeException e) {
				Alerts.showAlert("Unexpected Error", "Error fetching data", e.getMessage(), AlertType.ERROR);
			} finally {
				persistenceContext.close();
			}
		}
		
		return brand;
	}

	public List<Brand> findAll() {
		
		List<Brand> brands = new ArrayList<>();
		
		EntityManager persistenceContext = JPAUtil.createEntityManager();
		BrandDAO dao = DAOFactory.createBrandDAO(persistenceContext);
		try {
			brands = dao.findAll();
		} catch (RuntimeException e) {
			Alerts.showAlert("Unexpected Error", "Error fetching data", e.getMessage(), AlertType.ERROR);
		} finally {
			persistenceContext.close();
		}

		return brands;
	}

}