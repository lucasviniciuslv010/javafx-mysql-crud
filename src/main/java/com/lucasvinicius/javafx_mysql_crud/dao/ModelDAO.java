package com.lucasvinicius.javafx_mysql_crud.dao;

import java.util.List;

import com.lucasvinicius.javafx_mysql_crud.model.Model;

public interface ModelDAO {

	Model save(Model model);
	boolean remove(Model model);
	Model findById(Long id);
	List<Model> findByName(String name);
	List<Model> findAll();
	
}
