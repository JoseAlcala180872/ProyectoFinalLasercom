/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author TADEO
 */
public class PersistenciaExcepciones extends Exception{

    public PersistenciaExcepciones() {
    }

    public PersistenciaExcepciones(String message) {
        super(message);
    }

    public PersistenciaExcepciones(String message, Throwable cause) {
        super(message, cause);
    }

}