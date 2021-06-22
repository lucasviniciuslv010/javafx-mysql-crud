package com.lucasvinicius.javafx_mysql_crud.dao.factory;

import javax.persistence.EntityManager;

import com.lucasvinicius.javafx_mysql_crud.dao.impl.BrandDAOJPA;
import com.lucasvinicius.javafx_mysql_crud.dao.impl.ModelDAOJPA;
import com.lucasvinicius.javafx_mysql_crud.dao.impl.UserDAOJPA;

public class DAOFactory {

	public static UserDAOJPA createUserDAO(EntityManager em) {
		return new UserDAOJPA(em);
	}
	
	public static BrandDAOJPA createBrandDAO(EntityManager em) {
		return new BrandDAOJPA(em);
	}
	
	public static ModelDAOJPA createModelDAO(EntityManager em) {
		return new ModelDAOJPA(em);
	}
	
}
