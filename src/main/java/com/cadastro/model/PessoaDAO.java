package com.cadastro.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PessoaDAO {

    private static PessoaDAO instance;
    private EntityManagerFactory emf;

    private PessoaDAO() {
        try {
            emf = Persistence.createEntityManagerFactory("CadastroPU");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw e; // Propaga o erro para tratamento superior
        }
    }

    public static PessoaDAO getInstance() {
        if (instance == null) {
            instance = new PessoaDAO();
        }
        return instance;
    }

    public void salvar(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (pessoa.getId() == null) {
                em.persist(pessoa); // Insert
            } else {
                em.merge(pessoa); // Update
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Pessoa pessoa = em.find(Pessoa.class, id);
            if (pessoa != null) {
                em.remove(pessoa);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Pessoa buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pessoa> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Pessoa p");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void fechar() {
        emf.close();
    }
}