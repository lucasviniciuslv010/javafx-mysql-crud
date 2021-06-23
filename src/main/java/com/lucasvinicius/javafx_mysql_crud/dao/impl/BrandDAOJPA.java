package com.lucasvinicius.javafx_mysql_crud.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.lucasvinicius.javafx_mysql_crud.dao.BrandDAO;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;

public class BrandDAOJPA extends AbstractDAOJPA<Brand, Long> implements BrandDAO {

	public BrandDAOJPA(EntityManager em) {
		super(em);
	}

	@Override
	public Brand findByName(String name) {
		try {
			String sql = "SELECT * FROM tb_brand WHERE name = '" + name + "'";
			Query query = getPersistenceContext().createNativeQuery(sql, Brand.class);
			return (Brand) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}