package com.lucasvinicius.javafx_mysql_crud.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lucasvinicius.javafx_mysql_crud.model.AbstractEntity;

public abstract class AbstractDAOJPA<Entity extends AbstractEntity, PK extends Number> {

	private Class<Entity> persistentClass;
	private EntityManager persistenceContext;

	@SuppressWarnings("unchecked")
	public AbstractDAOJPA(EntityManager em) {
		persistenceContext = em;
		persistentClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public Entity save(Entity entity) {
		if (entity.getId() == null) {
			getPersistenceContext().persist(entity);
		} else {
			entity = getPersistenceContext().merge(entity);
		}
		return entity;
	}

	public boolean remove(Entity entity) {
		getPersistenceContext().remove(getPersistenceContext().contains(entity) 
				? entity : getPersistenceContext().merge(entity));
		return true;
	}

	public Entity findById(PK id) {
		return getPersistenceContext().find(persistentClass, id);
	}

	public List<Entity> findAll() {
		CriteriaBuilder cb = persistenceContext.getCriteriaBuilder();
		CriteriaQuery<Entity> cq = cb.createQuery(persistentClass);
		Root<Entity> rootEntity = cq.from(persistentClass);
		CriteriaQuery<Entity> all = cq.select(rootEntity);
		TypedQuery<Entity> allQuery = persistenceContext.createQuery(all);
		return allQuery.getResultList();
	}

	protected EntityManager getPersistenceContext() {
		return this.persistenceContext;
	}

}