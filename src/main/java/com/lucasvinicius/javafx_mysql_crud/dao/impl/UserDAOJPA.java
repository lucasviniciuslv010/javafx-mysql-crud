package com.lucasvinicius.javafx_mysql_crud.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.lucasvinicius.javafx_mysql_crud.dao.UserDAO;
import com.lucasvinicius.javafx_mysql_crud.model.User;

public class UserDAOJPA extends AbstractDAOJPA<User, Long> implements UserDAO {

	public UserDAOJPA(EntityManager em) {
		super(em);
	}
	
	@Override
	public User findByEmail(String email) {
		try {
			String sql = "SELECT * FROM tb_user WHERE email = '" + email + "'";
			Query query = getPersistenceContext().createNativeQuery(sql, User.class);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByContractNumber(String contractNumber) {
		try {
			String sql = "SELECT * FROM tb_user WHERE contract_number = '" + contractNumber + "'";
			Query query = getPersistenceContext().createNativeQuery(sql, User.class);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByName(String name) {
		String sql = "SELECT * FROM tb_user WHERE name = '" + name + "'";
		Query query = getPersistenceContext().createNativeQuery(sql, User.class);
		return (List<User>) query.getResultList();
	}
	
}