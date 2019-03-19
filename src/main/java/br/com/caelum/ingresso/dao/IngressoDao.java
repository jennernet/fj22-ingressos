package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Ingresso;

/**
 * Created by nando on 03/03/17.
 */
@Repository
public class IngressoDao {

    @PersistenceContext
    private EntityManager manager;

    public List<Ingresso> listar(){
    	return manager.createQuery("select i from Ingresso i").getResultList();
    }
    
    public boolean isDisponivel(Ingresso ingresso) {
    	String jpql = "select i from Ingresso i where i.lugar = :lugar and i.sessao = :sessao";
    	List<Ingresso> lista = manager.createQuery(jpql, Ingresso.class).setParameter("lugar", ingresso.getLugar()).setParameter("sessao", ingresso.getSessao()).getResultList();
    	return lista.isEmpty();
    }

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
}
