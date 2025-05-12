/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.ClienteDAO;
import dominio.Cliente;
import excepciones.BOException;
import excepciones.DAOException;
import java.util.List;

/**
 *
 * @author FER
 */
public class ClienteBO {

    private final ClienteDAO clienteDAO;

    public ClienteBO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * Registra un cliente con validaciones de negocio.
     * @param cliente Cliente a registrar
     * @throws BOException Si el cliente es invalido, ya existe o hay un error en la capa DAO.
     */
    public void registrarCliente(Cliente cliente) throws BOException {
        if (cliente == null) {
            throw new BOException("El cliente no puede ser nulo");
        }
        
        List<String> errores = cliente.getMensajesError();
        if (!errores.isEmpty()) {
            throw new BOException("Errores de validacion: " + String.join(", ", errores));
        }

        try {
            clienteDAO.registrarCliente(cliente);
        } catch (IllegalStateException e) {
            throw new BOException("El cliente ya existe: " + e.getMessage());
        } catch (DAOException e) {
            throw new BOException("Error al registrar cliente en la base de datos", e);
        }
    }
    
    /**
     * Actualiza un cliente existente
     * @param cliente Cliente con datos actualizados
     * @throws BOException Si el cliente es invalido o hay un error en la capa DAO.
     */
    public void actualizarCliente(Cliente cliente) throws BOException {
        if (cliente == null || cliente.getIdCliente() == null) {
            throw new BOException("El cliente o su ID son nulos");
        }

        List<String> errores = cliente.getMensajesError();
        if (!errores.isEmpty()) {
            throw new BOException("Errores de validacion: " + String.join(", ", errores));
        }

        try {
            clienteDAO.actualizarCliente(cliente);
        } catch (DAOException e) {
            throw new BOException("Error al actualizar cliente", e);
        }
    }
    
    /**
     * Obtiene todos los clientes registrados.
     * @return Lista de clientes
     * @throws BOException Si hay un error en la capa DAO.
     */
    public List<Cliente> listarTodosLosClientes() throws BOException {
        try {
            return clienteDAO.listarClientes();
        } catch (DAOException e) {
            throw new BOException("Error al listar clientes", e);
        }
    }
    
    /**
     * Busca clienter por nombre (Busqueda parcial).
     * @param nombre Nombre o fragmento a buscar.
     * @return Lista de clientes coincidentes.
     * @throws BOException Si el nombre esta vacio o hay un error en la capa DAO.
     */
    public List<Cliente> buscarClientesPorNombre(String nombre) throws BOException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BOException("El nombre de busqueda no puede estar vacio");
        }
        
        try {
            return clienteDAO.buscarPorNombre(nombre);
        } catch (DAOException e) {
            throw new BOException("Error al buscar clientes por nombre", e);
        }
    }
}
