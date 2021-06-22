package com.lucasvinicius.javafx_mysql_crud.dao;

import java.util.List;

import com.lucasvinicius.javafx_mysql_crud.model.Brand;

public interface BrandDAO {

	Brand save(Brand brand);
	boolean remove(Brand brand);
	Brand findById(Long id);
	Brand findByName(String name);
	List<Brand> findAll();
	
}
