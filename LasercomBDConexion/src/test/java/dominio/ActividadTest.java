/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Yeisi
 */
public class ActividadTest {
     private Actividad actividad;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Cliente Test", "test@example.com", "1234567890", "Dirección Test");
        actividad = new Actividad(cliente, "Título Test", "Descripción Test", LocalDate.now().plusDays(5));
    }

    @Test
    void testConstructores() {
        // Test constructor vacío
        Actividad vacia = new Actividad();
        assertNull(vacia.getIdActividad());
        assertNull(vacia.getTitulo());
        
        // Test constructor con ID
        Actividad conId = new Actividad(1L);
        assertEquals(1L, conId.getIdActividad());
        
        // Test constructor completo
        LocalDate fechaTermino = LocalDate.now().plusDays(10);
        Actividad completa = new Actividad(1L, "Título", "Descripción", fechaTermino, 
                                         LocalDate.now(), BigDecimal.valueOf(100.50), cliente);
        assertEquals(1L, completa.getIdActividad());
        assertEquals("Título", completa.getTitulo());
        assertEquals(BigDecimal.valueOf(100.50), completa.getMonto());
    }

    @Test
    void testGettersYSetters() {
        actividad.setIdActividad(1L);
        assertEquals(1L, actividad.getIdActividad());
        
        actividad.setTitulo("Nuevo Título");
        assertEquals("Nuevo Título", actividad.getTitulo());
        
        actividad.setDescripcion("Nueva Descripción");
        assertEquals("Nueva Descripción", actividad.getDescripcion());
        
        LocalDate nuevaFecha = LocalDate.now().plusDays(10);
        actividad.setFechaTermino(nuevaFecha);
        assertEquals(nuevaFecha, actividad.getFechaTermino());
        
        actividad.setEstado(EstadoActividad.EN_PROGRESO);
        assertEquals(EstadoActividad.EN_PROGRESO, actividad.getEstado());
    }

    @Test
    void testMarcarComoTerminada() {
        actividad.marcarComoTerminada();
        assertEquals(EstadoActividad.TERMINADO, actividad.getEstado());
        assertEquals(LocalDate.now(), actividad.getFechaRealTermino());
        
        // Verificar que no cambie si ya está terminado
        LocalDate fechaOriginal = actividad.getFechaRealTermino();
        actividad.marcarComoTerminada();
        assertEquals(fechaOriginal, actividad.getFechaRealTermino());
    }

    @Test
    void testIsEditable() {
        assertTrue(actividad.isEditable());
        
        actividad.setEstado(EstadoActividad.TERMINADO);
        assertFalse(actividad.isEditable());
    }

    @Test
    void testAvanzarEstado() {
        actividad.setEstado(EstadoActividad.PENDIENTE);
        actividad.avanzarEstado();
        assertEquals(EstadoActividad.EN_PROGRESO, actividad.getEstado());
        
        actividad.avanzarEstado();
        assertEquals(EstadoActividad.TERMINADO, actividad.getEstado());
        
        // Verificar que no avance más allá de TERMINADO
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> actividad.avanzarEstado());
    }

    @Test
    void testSetMonto() {
        // No debería permitir monto si no está terminado
        assertThrows(IllegalStateException.class, () -> 
            actividad.setMonto(BigDecimal.valueOf(100))
        );
        
        actividad.setEstado(EstadoActividad.TERMINADO);
        actividad.setMonto(BigDecimal.valueOf(150.75));
        assertEquals(BigDecimal.valueOf(150.75), actividad.getMonto());
    }

    @Test
    void testIsRegistroValido() {
        assertTrue(actividad.isRegistroValido());
        
        Actividad invalida1 = new Actividad();
        assertFalse(invalida1.isRegistroValido());
        
        Actividad invalida2 = new Actividad(cliente, "", "Descripción", LocalDate.now());
        assertFalse(invalida2.isRegistroValido());
    }

    @Test
    void testValidaciones() {
        Actividad valida = new Actividad(cliente, "Título válido", "Descripción", LocalDate.now().plusDays(1));
        assertTrue(valida.isValid());
        assertEquals(0, valida.getMensajesError().size());
        
        Actividad sinTitulo = new Actividad(cliente, "", "Descripción", LocalDate.now());
        assertFalse(sinTitulo.isValid());
        assertTrue(sinTitulo.getMensajesError().contains("El titulo es obligatorio"));
        
        Actividad sinCliente = new Actividad(null, "Título", "Descripción", LocalDate.now());
        assertFalse(sinCliente.isValid());
        assertTrue(sinCliente.getMensajesError().contains("El cliente asociado es requerido"));
    }

    @Test
    void testEqualsYHashCode() {
        Actividad actividad1 = new Actividad(1L);
        Actividad actividad2 = new Actividad(1L);
        Actividad actividad3 = new Actividad(2L);
        
        assertEquals(actividad1, actividad2);
        assertNotEquals(actividad1, actividad3);
        assertEquals(actividad1.hashCode(), actividad2.hashCode());
    }

//    @Test
//    void testValidacionFechas() {
//        // Fecha término debe ser presente o futuro
//        assertDoesNotThrow(() -> 
//            actividad.setFechaTermino(LocalDate.now().plusDays(1))
//        );
//        
//        assertThrows(jakarta.validation.ValidationException.class, () -> 
//            actividad.setFechaTermino(LocalDate.now().minusDays(1))
//        );
//        
//        // Fecha real término debe ser pasado o presente
//        actividad.marcarComoTerminada();
//        assertEquals(LocalDate.now(), actividad.getFechaRealTermino());
//        
//        assertThrows(jakarta.validation.ValidationException.class, () -> 
//            actividad.setFechaRealTermino(LocalDate.now().plusDays(1))
//        );
//    }
    
}
