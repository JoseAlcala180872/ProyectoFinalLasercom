/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author FER
 */
public class BOException extends RuntimeException {
    
    /**
     * Constructor con mensaje
     * @param message 
     */
    public BOException(String message) {
        super("[BO] " + message);
    }
    
    /**
     * Constructor con mensaje y causa
     * @param message
     * @param cause 
     */
    public BOException(String message, Throwable cause) {
        super("[BO] " + message, cause);
    }
}
