package com.cefet.dolphub.Repositorio;

import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Arquivo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;

public class ArquivoDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ArquivoDAO() {
        //emf = Persistence.createEntityManagerFactory("br.cefetmg_dao_jar_0.1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
    }

    public void create(Arquivo arquivo) {
        em.getTransaction().begin();
        em.persist(arquivo);
        em.getTransaction().commit();
    }

    public Arquivo read(int id) {
        return em.find(Arquivo.class, id);
    }

    public void update(Arquivo arquivo) {
        em.getTransaction().begin();
        em.merge(arquivo);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        em.getTransaction().begin();
        Arquivo arquivo = em.find(Arquivo.class, id);
        if (arquivo != null) {
            em.remove(arquivo);
        }
        em.getTransaction().commit();
    }

    public List<Arquivo> listAll() {
        return em.createQuery("SELECT x FROM Arquivo x", Arquivo.class).getResultList();
    }
    
    public Arquivo selecionar(int id) {
        em.getTransaction().begin();
        Arquivo x = em.find(Arquivo.class, id);
        return x;
    }
    
    public List<Arquivo> pesquisarNome(String nome) {
        var cb = em.getCriteriaBuilder();
        CriteriaQuery<Arquivo> criteria = cb.createQuery(Arquivo.class);
        var root = criteria.from(Arquivo.class);
        criteria.select(root).where(cb.like(root.get("nome"), "%"+nome+"%"));
        List<Arquivo> lista = em.createQuery(criteria).getResultList();
        return lista;
    }
    
    public Arquivo selecionar(String email) {
        String jpql = "SELECT f FROM Arquivo f WHERE f.email = :email";
        TypedQuery<Arquivo> query = em.createQuery(jpql, Arquivo.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
    
}
