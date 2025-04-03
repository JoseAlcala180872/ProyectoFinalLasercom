/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import dominio.Cliente;
import excepciones.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author FER
 */
public class ClienteDAO {

    /**
     * Registro del cliente con validacion de duplicados
     *
     * @param cliente Cliente a registrar en la base de datos
     * @throws IllegalStateException Excepcion en caso de que haya un cliente
     * duplicado
     */
    public void registrarCliente(Cliente cliente) throws DAOException, IllegalStateException {
        // Validacion preeliminar antes de abrir la transaccion
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre es requerido");
        }

        EntityManager em = Conexion.getEntityManager();
        try {
            if (existeCliente(cliente.getNombre(), cliente.getTelefono())) {
                throw new IllegalStateException("El cliente ya existe en la base de datos");
            }
            
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (DAOException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new DAOException("Error al registrar al cliente en la base de datos", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Metodo para actualizar datos del cliente.
     *
     * @param cliente Cliente que se actualizara en la base de datos.
     */
    public void actualizarCliente(Cliente cliente) throws DAOException {
        EntityManager em = Conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (DAOException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new DAOException("Error al actualizar cliente", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Metodo para hacer la busqueda de todos los clientes en la base de datos.
     *
     * @return
     */
    public List<Cliente> listarClientes() throws DAOException {
        EntityManager em = Conexion.getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c", Cliente.class);
            return query.getResultList();
        } catch (DAOException e) {
            throw new DAOException("Error al listar clientes", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Metodo para buscar clienter por nombre.
     *
     * @param nombre Nombre utilizado en el metodo de busqueda para filtrar.
     * @return Lista con todos los clientes coincidentes.
     */
    public List<Cliente> buscarPorNombre(String nombre) throws DAOException {
        EntityManager em = Conexion.getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre", Cliente.class);
            query.setParameter("nombre", "%" + nombre + "%");
            return query.getResultList();
        } catch (DAOException e) {
            throw new DAOException("Error al buscar por nombre", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Metodo auxiliar para validacion de clientes duplicados
     *
     * @param nombre Nombre del cliente
     * @param telefono Telefono del cliente
     * @return En caso de encontrar, regresa el cliente encontrado con el mismo
     * nombre y telefono.
     */
    private boolean existeCliente(String nombre, String telefono) throws DAOException {
        EntityManager em = Conexion.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c WHERE c.nombre = :nombre AND c.telefono ="
                    + ":telefono", Long.class);
            query.setParameter("nombre", nombre);
            query.setParameter("telefono", telefono);
            return query.getSingleResult() > 0;
        } catch (DAOException e) {
            throw new DAOException("Error al verificar cliente existente", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
