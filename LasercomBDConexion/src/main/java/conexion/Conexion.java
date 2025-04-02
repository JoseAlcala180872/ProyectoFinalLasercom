/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Yeisi
 */
public class Conexion {
    private final String DIRECCION_PERSISTENCE = "LasercomPU";
    private static EntityManagerFactory entityManagerFactory;
    private static Conexion conexion;
    
    private Conexion(){
        entityManagerFactory = Persistence.createEntityManagerFactory(DIRECCION_PERSISTENCE);
    }
    
    public static EntityManagerFactory getConexion(){
        
        if(conexion == null){
            conexion = new Conexion();
        }
        return entityManagerFactory;
    }
    
}
