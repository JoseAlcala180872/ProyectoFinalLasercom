/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import dominio.Cliente;
import excepciones.PersistenciaExcepciones;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Yeisi
 */
public class ClienteDAO implements interfaces.IClienteDAO{
    EntityManagerFactory emf;

    public ClienteDAO() {
        this.emf = Conexion.getConexion();
    }
    
    @Override
    public Cliente insertar(Cliente clienteInsertar) throws PersistenciaExcepciones {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(clienteInsertar);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return clienteInsertar;
    }
    
    @Override
    public Cliente actualizar(Cliente clienteActualizar)throws PersistenciaExcepciones{
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(clienteActualizar);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return clienteActualizar;
    }
    
    @Override
    public Cliente eliminar(Cliente clienteEliminar)throws PersistenciaExcepciones{
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            if (!em.contains(clienteEliminar)) {
                // Si no está administrada, busca la entidad en el contexto de persistencia
                clienteEliminar = em.merge(clienteEliminar);
            }
            em.remove(clienteEliminar);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return clienteEliminar;
    }
    
    @Override
    public Cliente buscar(int idCliente)throws PersistenciaExcepciones{
        EntityManager em = emf.createEntityManager();
        Cliente clienteBuscar = null;
        try{
            em.getTransaction().begin();
            clienteBuscar = em.find(Cliente.class, idCliente);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return clienteBuscar;
    }
    
}
