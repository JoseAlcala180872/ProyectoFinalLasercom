/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author FER
 */
public class DAOException extends RuntimeException {
    
    /**
     * Constructor con mensaje.
     * @param message 
     */
    public DAOException(String message) {
        super("[DAO] " + message);
    }
    
    /**
     * Constructor con mensaje y causa.
     * @param message
     * @param cause 
     */
    public DAOException(String message, Throwable cause) {
        super("[DAO} " + message, cause);
    }
}
