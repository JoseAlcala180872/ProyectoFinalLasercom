/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package dominio;

/**
 *  Representa los posibles estados de una actividad en el sistema.
 * @author FER
 */
public enum EstadoActividad {
    /** Actividad creada pero no iniciada. */
    PENDIENTE,
    
    /** Actividad en desarrollo */
    EN_PROGRESO,
    
    /** Actividad finalizada. */
    TERMINADO
}
