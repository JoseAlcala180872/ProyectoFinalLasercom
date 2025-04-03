/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import dominio.Actividad;
import excepciones.PersistenciaExcepciones;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Yeisi
 */
public class ActividadDAO implements interfaces.IActividadDAO{
    EntityManagerFactory emf;

    public ActividadDAO() {
        this.emf = Conexion.getConexion();
    }
    
    @Override
    public Actividad insertar(Actividad actividadInsertar) throws PersistenciaExcepciones {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(actividadInsertar);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return actividadInsertar;
    }
    
    @Override
    public Actividad actualizar(Actividad actividadActualizar)throws PersistenciaExcepciones{
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(actividadActualizar);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return actividadActualizar;
    }
    
    @Override
    public Actividad eliminar(Actividad actividadEliminar)throws PersistenciaExcepciones{
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            if (!em.contains(actividadEliminar)) {
                // Si no está administrada, busca la entidad en el contexto de persistencia
                actividadEliminar = em.merge(actividadEliminar);
            }
            em.remove(actividadEliminar);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return actividadEliminar;
    }
    
    @Override
    public Actividad buscar(int idActividad)throws PersistenciaExcepciones{
        EntityManager em = emf.createEntityManager();
        Actividad actividadBuscar = null;
        try{
            em.getTransaction().begin();
            actividadBuscar = em.find(Actividad.class, idActividad);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return actividadBuscar;
    }
    
    
}
