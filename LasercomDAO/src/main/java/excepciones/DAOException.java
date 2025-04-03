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
    public DAOException(String message) {
        super("[DAO]" + message);
    }
    
    public DAOException(String message, Throwable cause) {
        super("[DAO}" + message, cause);
    }
}
