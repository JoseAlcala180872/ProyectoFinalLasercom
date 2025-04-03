/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package lasercom.lasercombo;

import bo.ActividadBO;
import bo.ClienteBO;
import dao.ActividadDAO;
import dao.ClienteDAO;
import dominio.Actividad;
import dominio.Cliente;
import dominio.EstadoActividad;
import excepciones.BOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author FER
 */
public class LasercomBO {

    public static void main(String[] args) {
        // Pruebas (Tambien funciona padrinos)
        /*
        // 1. Inicialización
        ClienteDAO clienteDAO = new ClienteDAO();
        ActividadDAO actividadDAO = new ActividadDAO();

        ClienteBO clienteBO = new ClienteBO(clienteDAO);
        ActividadBO actividadBO = new ActividadBO(actividadDAO);

        // 2. Prueba ClienteBO
        System.out.println("=== PRUEBAS CLIENTE ===");
        Cliente clienteValido = new Cliente("Juan Pérez", "juan@test.com", "5512345678", "Calle 123");
        Cliente clienteInvalido = new Cliente(null, "correo@invalido", "123", null);

        try {
            // Registrar cliente válido
            clienteBO.registrarCliente(clienteValido);
            System.out.println("Cliente registrado: " + clienteValido.getNombre());

            // Registrar cliente inválido (debe fallar)
            clienteBO.registrarCliente(clienteInvalido);
        } catch (BOException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
        }

        // 3. Prueba ActividadBO
        System.out.println("\n=== PRUEBAS ACTIVIDAD ===");
        Actividad actividadValida = new Actividad(
                clienteValido,
                "Diseño UI",
                "Diseñar interfaz de usuario",
                LocalDate.now().plusDays(7) // Fecha futura
        );

        try {
            // Registrar actividad válida
            Actividad actividadRegistrada = actividadBO.registrarActividad(actividadValida);
            System.out.println("Actividad registrada: " + actividadRegistrada.getTitulo());

            // Cambiar estado a "En progreso"
            actividadBO.cambiarEstadoActividad(actividadRegistrada.getIdActividad(), EstadoActividad.EN_PROGRESO);
            System.out.println("Estado cambiado a: EN_PROGRESO");

            // Registrar monto en actividad no terminada (debe fallar)
            actividadBO.registrarMontoActividad(actividadRegistrada.getIdActividad(), BigDecimal.valueOf(1000));
        } catch (BOException e) {
            System.err.println("Error en actividad: " + e.getMessage());
        }

        // 4. Prueba Tablero Kanban
        try {
            System.out.println("\n=== TABLERO KANBAN ===");
            actividadBO.obtenerTableroKanban().forEach(a
                    -> System.out.println(
                            "Actividad: " + a.getTitulo()
                            + " | Estado: " + a.getEstado()
                            + " | Cliente: " + a.getCliente().getNombre()
                    )
            );
        } catch (BOException e) {
            System.err.println("Error al cargar tablero: " + e.getMessage());
        }
        */
    }
}
