package com.lucasvinicius.javafx_mysql_crud.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.lucasvinicius.javafx_mysql_crud.dao.BrandDAO;
import com.lucasvinicius.javafx_mysql_crud.model.Brand;

public class BrandDAOJPA extends AbstractDAOJPA<Brand, Long> implements BrandDAO {

	public BrandDAOJPA(EntityManager em) {
		super(em);
	}

	@Override
	public Brand findByName(String name) {
		String jpql = "SELECT b FROM Brand b WHERE b.name like :name";
		Query query = getPersistenceContext().createQuery(jpql, Brand.class);
		query.setParameter("name", name.concat("%"));
		return (Brand) query.getSingleResult();
	}

}