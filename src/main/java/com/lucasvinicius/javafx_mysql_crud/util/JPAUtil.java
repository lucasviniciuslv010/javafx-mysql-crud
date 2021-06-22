package com.lucasvinicius.javafx_mysql_crud.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static final String PERSISTENCE_UNIT_NAME = "appJavaFXUnit";
	private static EntityManagerFactory emf;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager createEntityManager() {
		if (!emf.isOpen()) {
			throw new RuntimeException("EntityManagerFactory is closed.");
		}
		return emf.createEntityManager();
	}
	
	public static void closeEntityManagerFactory() {
		if (emf.isOpen()) {
			emf.close();
		}
	}
	
}
