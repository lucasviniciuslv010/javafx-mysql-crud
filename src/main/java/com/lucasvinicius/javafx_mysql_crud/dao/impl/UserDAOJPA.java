package com.lucasvinicius.javafx_mysql_crud.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.lucasvinicius.javafx_mysql_crud.dao.UserDAO;
import com.lucasvinicius.javafx_mysql_crud.model.User;

public class UserDAOJPA extends AbstractDAOJPA<User, Long> implements UserDAO {

	public UserDAOJPA(EntityManager em) {
		super(em);
	}
	
	@Override
	public User findByEmail(String email) {
		String jpql = "SELECT u FROM User u WHERE u.email like :email";
		Query query = getPersistenceContext().createQuery(jpql, User.class);
		query.setParameter("email", email.concat("%"));
		return (User) query.getSingleResult();
	}

	@Override
	public User findByContractNumber(String contractNumber) {
		String jpql = "SELECT u FROM User u WHERE u.contractNumber like :contractNumber";
		Query query = getPersistenceContext().createQuery(jpql, User.class);
		query.setParameter("contractNumber", contractNumber.concat("%"));
		return (User) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByName(String name) {
		String jpql = "SELECT u FROM User u WHERE u.name like :name";
		Query query = getPersistenceContext().createQuery(jpql, User.class);
		query.setParameter("name", name.concat("%"));
		return (List<User>) query.getResultList();
	}
	
}
