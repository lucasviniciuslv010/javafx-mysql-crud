package com.lucasvinicius.javafx_mysql_crud.dao;

import java.util.List;

import com.lucasvinicius.javafx_mysql_crud.model.User;

public interface UserDAO {
	
	User save(User user);
	boolean remove(User user);
	User findById(Long id);
	User findByEmail(String email);
	User findByContractNumber(String contractNumber);
	List<User> findByName(String name);
	List<User> findAll();
	
}
