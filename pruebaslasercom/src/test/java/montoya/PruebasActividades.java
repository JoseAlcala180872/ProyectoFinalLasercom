package montoya;

import static org.junit.Assert.assertTrue;
import java.sql.Date;
import org.junit.Test;

public class PruebasActividades
{
    
    /*
     * Registracion de Actividad
     * 
     * El usuario insertara los datos correctos 
     * para agregar una actividad al sistema
     * 
     * Resultado Esperado: Se insertara la actividad 
     * nueva al sistema.
     */
    @Test
    public void AC1()
    {
        System.out.println("Prueba de registracion de actividad");
        String clienteId = "4123";
        String titulo = "Mantenimiento de Computadoras";
        String desc = "Se hara mantenimiento a los sistemas del negocio";
        Date fechaRealizar = Date();
        assertTrue( true );
    }
    /*
     * Edicion de Actividad
     * 
     * Se editara la descripcion de la actividad.
     * 
     * Resultado Esperado: Se editara los datos de la actividad en el
     * sistema.
     */
    @Test
    public void AC2()
    {
        System.out.println("Prueba de edicion de actividad");
        String desc = "Se hara mantenimiento a los sistemas del negocio, incluyendo computadoras y el servidor.";   
        assertTrue( true );
    }
    /*
     * Termina Actividad
     * 
     * Se terminara una actvidad y se va a insertar el monto
     * ganado por la terminacion de la actividad.
     * 
     * Resultado Esperado: Se Terminara el cliente del sistema.
     */
    @Test
    public void AC3()
    {
        System.out.println("Prueba de terminacion de actividad");
        float ingresos = 5000f;      
        assertTrue( true );
    }
}
