/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Cliente;
import excepciones.PersistenciaExcepciones;

/**
 *
 * @author Yeisi
 */
public interface IClienteDAO {
    public Cliente insertar(Cliente clienteInsertar) throws PersistenciaExcepciones;
    public Cliente actualizar(Cliente clienteActualizar)throws PersistenciaExcepciones;
    public Cliente eliminar(Cliente clienteEliminar)throws PersistenciaExcepciones;
    public Cliente buscar(int idCliente)throws PersistenciaExcepciones;
    
}
