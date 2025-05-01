/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Yeisi
 */
public class ClienteTest {
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    @DisplayName("Test constructor vacío")
    void testConstructorVacio() {
        assertNotNull(cliente);
        assertNull(cliente.getIdCliente());
        assertNull(cliente.getNombre());
        assertNull(cliente.getCorreo());
        assertNull(cliente.getTelefono());
        assertNull(cliente.getDireccion());
    }

    @Test
    @DisplayName("Test constructor con ID")
    void testConstructorConId() {
        Cliente clienteConId = new Cliente(1L);
        assertEquals(1L, clienteConId.getIdCliente());
        assertNull(clienteConId.getNombre());
    }

    @Test
    @DisplayName("Test constructor con atributos básicos")
    void testConstructorConAtributos() {
        Cliente clienteCompleto = new Cliente("Juan Perez", "juan@example.com", "1234567890", "Calle 123");
        
        assertEquals("Juan Perez", clienteCompleto.getNombre());
        assertEquals("juan@example.com", clienteCompleto.getCorreo());
        assertEquals("1234567890", clienteCompleto.getTelefono());
        assertEquals("Calle 123", clienteCompleto.getDireccion());
    }

    @Test
    @DisplayName("Test constructor con todos los atributos")
    void testConstructorCompleto() {
        Cliente clienteCompleto = new Cliente(1L, "Juan Perez", "juan@example.com", "1234567890", "Calle 123");
        
        assertEquals(1L, clienteCompleto.getIdCliente());
        assertEquals("Juan Perez", clienteCompleto.getNombre());
        assertEquals("juan@example.com", clienteCompleto.getCorreo());
        assertEquals("1234567890", clienteCompleto.getTelefono());
        assertEquals("Calle 123", clienteCompleto.getDireccion());
    }

    @Test
    @DisplayName("Test setters y getters")
    void testSettersAndGetters() {
        cliente.setIdCliente(1L);
        cliente.setNombre("Maria Garcia");
        cliente.setCorreo("maria@example.com");
        cliente.setTelefono("0987654321");
        cliente.setDireccion("Avenida 456");

        assertEquals(1L, cliente.getIdCliente());
        assertEquals("Maria Garcia", cliente.getNombre());
        assertEquals("maria@example.com", cliente.getCorreo());
        assertEquals("0987654321", cliente.getTelefono());
        assertEquals("Avenida 456", cliente.getDireccion());
    }

    @Test
    @DisplayName("Test equals y hashCode")
    void testEqualsAndHashCode() {
        Cliente cliente1 = new Cliente(1L);
        Cliente cliente2 = new Cliente(1L);
        Cliente cliente3 = new Cliente(2L);

        assertEquals(cliente1, cliente2);
        assertNotEquals(cliente1, cliente3);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
        assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }

    @Test
    @DisplayName("Test validación - Cliente válido")
    void testValidacionClienteValido() {
        Cliente clienteValido = new Cliente("Ana Lopez", "ana@example.com", "1234567890", "Calle Principal 123");
        
        assertTrue(clienteValido.isValid());
        assertEquals(0, clienteValido.getMensajesError().size());
    }

    @Test
    @DisplayName("Test validación - Nombre vacío")
    void testValidacionNombreVacio() {
        cliente.setNombre("");
        cliente.setCorreo("test@example.com");
        cliente.setTelefono("1234567890");
        cliente.setDireccion("Calle 123");

        List<String> errores = cliente.getMensajesError();
        
        assertFalse(cliente.isValid());
        assertEquals(1, errores.size());
        assertTrue(errores.contains("El nombre es obligatorio"));
    }

    @Test
    @DisplayName("Test validación - Correo inválido")
    void testValidacionCorreoInvalido() {
        cliente.setNombre("Juan Perez");
        cliente.setCorreo("correo-invalido");
        cliente.setTelefono("1234567890");
        cliente.setDireccion("Calle 123");

        List<String> errores = cliente.getMensajesError();
        
        assertFalse(cliente.isValid());
        assertEquals(1, errores.size());
        assertTrue(errores.contains("El correo electronico no tiene un formato valido"));
    }

    @Test
    @DisplayName("Test validación - Teléfono inválido")
    void testValidacionTelefonoInvalido() {
        cliente.setNombre("Juan Perez");
        cliente.setCorreo("juan@example.com");
        cliente.setTelefono("123"); // Muy corto
        cliente.setDireccion("Calle 123");

        List<String> errores = cliente.getMensajesError();
        
        assertFalse(cliente.isValid());
        assertEquals(1, errores.size());
        assertTrue(errores.contains("El telefono debe tener entre 10 y 15 digitos"));
    }

    @Test
    @DisplayName("Test validación - Dirección vacía")
    void testValidacionDireccionVacia() {
        cliente.setNombre("Juan Perez");
        cliente.setCorreo("juan@example.com");
        cliente.setTelefono("1234567890");
        cliente.setDireccion("");

        List<String> errores = cliente.getMensajesError();
        
        assertFalse(cliente.isValid());
        assertEquals(1, errores.size());
        assertTrue(errores.contains("La direccion es obligatoria"));
    }

    @Test
    @DisplayName("Test validación - Múltiples errores")
    void testValidacionMultiplesErrores() {
        cliente.setNombre("");
        cliente.setCorreo("correo-invalido");
        cliente.setTelefono("123");
        cliente.setDireccion("");

        List<String> errores = cliente.getMensajesError();
        
        assertFalse(cliente.isValid());
        assertEquals(4, errores.size());
        assertTrue(errores.contains("El nombre es obligatorio"));
        assertTrue(errores.contains("El correo electronico no tiene un formato valido"));
        assertTrue(errores.contains("El telefono debe tener entre 10 y 15 digitos"));
        assertTrue(errores.contains("La direccion es obligatoria"));
    }
    
}
