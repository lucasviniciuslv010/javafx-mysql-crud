package com.lucasvinicius.javafx_mysql_crud.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.lucasvinicius.javafx_mysql_crud.dao.ModelDAO;
import com.lucasvinicius.javafx_mysql_crud.model.Model;

public class ModelDAOJPA extends AbstractDAOJPA<Model, Long> implements ModelDAO {

	public ModelDAOJPA(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Model> findByName(String name) {
		String sql = "SELECT * FROM tb_model WHERE name = '" + name + "'";
		Query query = getPersistenceContext().createNativeQuery(sql, Model.class);
		return (List<Model>) query.getResultList();
	}

}