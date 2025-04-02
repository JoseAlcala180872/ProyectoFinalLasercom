/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio.Actividad;
import excepciones.PersistenciaExcepciones;

/**
 *
 * @author Yeisi
 */
public interface IActividadDAO {
    public Actividad insertar(Actividad actividadInsertar) throws PersistenciaExcepciones;
    public Actividad actualizar(Actividad actividadActualizar)throws PersistenciaExcepciones;
    public Actividad eliminar(Actividad actividadEliminar)throws PersistenciaExcepciones;
    public Actividad buscar(int idActividad)throws PersistenciaExcepciones;
    
}
