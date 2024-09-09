package com.cefet.dolphub.Repositorio;

import java.util.List;
import com.cefet.dolphub.Entidades.Main.Usuario;
import jakarta.persistence.*;

public class UsuarioDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

     public UsuarioDAO() {
    //     emf = Persistence.createEntityManagerFactory("br.cefetmg_dao_jar_0.1.0-SNAPSHOTPU");
       em = emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public Usuario read(int id) {
        return em.find(Usuario.class, id);
    }

    public void update(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
        em.getTransaction().commit();
    }

    public List<Usuario> listAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }
    
    public Usuario selecionar(int id) {
        em.getTransaction().begin();
        Usuario u = em.find(Usuario.class, id);
        return u;
    }

}
