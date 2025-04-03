/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package lasercom.lasercomdao;

import dao.ActividadDAO;
import dao.ClienteDAO;
import dominio.Actividad;
import dominio.Cliente;
import dominio.EstadoActividad;
import excepciones.DAOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author FER
 */
public class LasercomDAO {

    public static void main(String[] args) {
        // Pruebas (Si funciona) 
        /*
        ClienteDAO clienteDAO = new ClienteDAO();
        ActividadDAO actividadDAO = new ActividadDAO();
        
        try {
            // 2. Probar ClienteDAO
            System.out.println("=== PRUEBA DE CLIENTE ===");
            Cliente cliente = new Cliente();
            cliente.setNombre("Ejemplo S.A.");
            cliente.setTelefono("5551234567");
            cliente.setCorreo("ejemplo@correo.com");
            
            clienteDAO.registrarCliente(cliente);
            System.out.println("Cliente registrado con ID: " + cliente.getIdCliente());
            
            // 3. Probar ActividadDAO
            System.out.println("\n=== PRUEBA DE ACTIVIDAD ===");
            Actividad actividad = new Actividad();
            actividad.setTitulo("Implementar DAOs");
            actividad.setDescripcion("Terminar las clases DAO para el proyecto");
            actividad.setFechaTermino(LocalDate.now().plusDays(7));
            actividad.setCliente(cliente); // Asignar el cliente creado
            
            actividadDAO.guardar(actividad);
            System.out.println("Actividad registrada con ID: " + actividad.getIdActividad());
            
            // 4. Simular movimiento en el Kanban
            actividadDAO.moverActividad(actividad.getIdActividad(), EstadoActividad.EN_PROGRESO);
            System.out.println("Actividad movida a EN_PROGRESO");
            
            // 5. Registrar monto al terminar
            actividadDAO.moverActividad(actividad.getIdActividad(), EstadoActividad.TERMINADO);
            actividadDAO.registrarMontoTerminado(actividad.getIdActividad(), new BigDecimal("1500.50"));
            System.out.println("Actividad terminada con monto registrado");
            
            // 6. Listar resultados
            System.out.println("\n=== RESULTADOS FINALES ===");
            System.out.println("Clientes registrados: " + clienteDAO.listarClientes().size());
            System.out.println("Actividades en tablero: " + actividadDAO.obtenerTableroCompleto().size());
        } catch (DAOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actividadDAO.cerrar();
        }
        */
    }
}
