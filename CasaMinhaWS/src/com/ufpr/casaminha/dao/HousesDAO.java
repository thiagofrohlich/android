package com.ufpr.casaminha.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ufpr.casaminha.model.House;

public class HousesDAO {
	private EntityManagerFactory factory = null;
	private EntityManager entityManager = null;
	
	private EntityManager getEntityManager() {
	   
	    try {
	      //Obtém o factory a partir da unidade de persistência.
	      factory = Persistence.createEntityManagerFactory("CasaMinhaWS");
	      //Cria um entity manager.
	      entityManager = factory.createEntityManager();
	      //Fecha o factory para liberar os recursos utilizado.
	    }catch (Exception e){
	    	e.printStackTrace();
	    }   
	    finally {
//	      factory.close();
	    }
	    return entityManager;
	  }
	
	public List<House> buscaVendidos(){
		return getEntityManager().createQuery("select h from House h where h.vendido = true").getResultList();
	}
	
	public void save(House house){
		System.out.println("hue");
		entityManager = getEntityManager();
		entityManager.persist(house);
		
	}
	
	public List<House> getAll() {
		return getEntityManager().createQuery("select h from House h").getResultList();
	}
}
