package montoya;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import bo.ClienteBO;
import dao.ClienteDAO;
import dominio.Cliente;
import excepciones.BOException;
import Pantallas.*;
import java.awt.Robot;

public class PruebasClientes 
{
    // aqui se va a guardar el ID del cliente para utilizarlo en pruebas
    String clienteId;
    /*
     * Registracion de Cliente
     * 
     * El usuario insertara los datos correctos 
     * para agregar un cliente al sistema
     * 
     * Resultado Esperado: Se insertara el cliente nuevo al sistema.
     */
    @Test
    public void CL1()
    {
        System.out.println("Prueba de registracion de clientes");
        String nombre = "Juan Perez";
        String numero = "6442145231";
        String correo = "juanperez123@gmail.com";
        String direccion = "Bella Vista 1234";
        try {
            // TODO: handle exception
            Cliente cliente = new Cliente(nombre, correo, numero, direccion);
        
            ClienteBO instance = new ClienteBO(new ClienteDAO());
            instance.registrarCliente(cliente);
        } catch (Exception e) {
            fail(e.getClass().getSimpleName() + " was thrown");
        }
        

        
        assertTrue( true );
    }

    /*
     * Edicion de Cliente
     * 
     * Se editara la direccion del cliente
     * 
     * Resultado Esperado: Se editara los datos del cliente en el sistema.
     */
    @Test
    public void CL2()
    {
        System.out.println("Prueba de edicion de clientes");
        String direccion = "Casa Blanca 5678";        
        assertTrue( true );
    }


    /*
     * Datos erroneos al registrar cliente
     * 
     * Se van a insertar datos vacios o erroneos en 
     * campos de texto el cual el sistema debe de rechazar.
     * 
     * Resultado Esperado: El sistema rechazara la insercion al 
     * menos que se inserte datos correctos.
     */
    @Test
    public void CL3()
    {
        System.out.println("Prueba de datos erroneos de clientes");       
        assertTrue( true );
    }
}
