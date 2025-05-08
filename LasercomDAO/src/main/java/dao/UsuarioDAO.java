/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import dominio.Usuario;
import jakarta.persistence.*;

/**
 *
 * @author FER
 */
public class UsuarioDAO {
    
    /**
     * Metodo para buscar un usuario por su nombre de usuario.
     * @param nombreUsuario Nombre de usuario por el cual se buscara.
     * @return Usuario encontrado, null en caso de error.
     */
    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        EntityManager em = Conexion.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    /**
     * Metodo para buscar un usuario por su email.
     * @param email 
     * @return 
     */
    public Usuario buscarPorEmail(String email) {
        EntityManager em = Conexion.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    /**
     * Metodo para guardar un nuevo usuario en la base de datos.
     * @param usuario Usuario a guardar en la base de datos.
     */
    public void guardar(Usuario usuario) {
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(usuario);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
