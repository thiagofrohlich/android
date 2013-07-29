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
		entityManager.getTransaction().begin();
		entityManager.persist(house);
		entityManager.getTransaction().commit();
		
	}
	
	public List<House> getAll() {
		return getEntityManager().createQuery("select h from House h").getResultList();
	}
	
	public House getOne(Long id) {
		return (House) getEntityManager().createQuery("select h from House h where h.id = :id").setParameter("id", id).getSingleResult();
	}
	
	public List<House> filtrar(String tipo, Integer qtdQuartos, Double valor) {
		StringBuilder hql = new StringBuilder("select h from House h ");
		if(tipo != null || qtdQuartos != null || valor != null) {
			hql.append("where ");
		}
		
		return null;
	}
	
	public void vender(Long id) {
		House casa = getOne(id);
		casa.setVendido(true);
		update(casa);
	}
	
	public void excluir(Long id) {
		entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		House casa = getOne(id);
		entityManager.remove(casa);
		entityManager.getTransaction().commit();
	}
	
	public void update(House house) {
		entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(house);
		entityManager.getTransaction().commit();
	}
}
