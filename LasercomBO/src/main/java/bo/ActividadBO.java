/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.ActividadDAO;
import dominio.Actividad;
import dominio.Cliente;
import dominio.EstadoActividad;
import excepciones.BOException;
import excepciones.DAOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author FER
 */
public class ActividadBO {

    private final ActividadDAO actividadDAO;

    public ActividadBO(ActividadDAO actividadDAO) {
        this.actividadDAO = Objects.requireNonNull(actividadDAO, "ActividadDAO no puede ser nulo");
    }

    /**
     * Registra una nueva actividad con validaciones.
     *
     * @param actividad Actividad a registrar.
     * @return Actividad registrada (con ID generado)
     * @throws BOException Si la actividad es invalida o hay un error en la capa
     * DAO.
     */
    public Actividad registrarActividad(Actividad actividad) throws BOException {
        if (actividad == null || !actividad.isValid()) {
            throw new BOException("Errores: " + String.join(", ", actividad.getMensajesError()));
        }

        try {
            return actividadDAO.guardar(actividad);
        } catch (DAOException e) {
            throw new BOException("Error al registrar actividad", e);
        }
    }

    /**
     * Cambia el estado de una actividad (Pendiente -> En progreso ->
     * Terminado).
     *
     * @param idActividad ID de la actividad.
     * @param nuevoEstado Nuevo estado a asignar.
     * @throws BOException Si la actividad no existe o hay un error en la capa
     * DAO.
     */
    public void cambiarEstadoActividad(Long idActividad, EstadoActividad nuevoEstado) throws BOException {
        try {
            actividadDAO.moverActividad(idActividad, nuevoEstado);
        } catch (DAOException e) {
            throw new BOException("Error al cambiar estado de la actividad", e);
        }
    }

    /**
     * Actualiza los datos editables de una actividad (titulo, descripcion,
     * fecha).
     *
     * @param actividad Actividad con datos actualizados.
     * @throws BOException Si la actividad esta terminada o hay un error en la
     * capa DAO.
     */
    public void actualizarActividad(Actividad actividad) throws BOException {
        try {
            actividadDAO.actualizarInformacion(actividad);
        } catch (DAOException e) {
            throw new BOException("Error al actualizar actividad", e);
        }
    }

    /**
     * Registra el monto generado por una actividad terminada.
     *
     * @param idActividad ID de la actividad.
     * @param monto Monto en pesos mexicanos.
     * @throws BOException Si el monto es invalido o la actividad no esta
     * terminada.
     */
    public void registrarMontoActividad(Long idActividad, BigDecimal monto) throws BOException {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BOException("El monto debe ser mayor que cero");
        }

        try {
            actividadDAO.registrarMontoTerminado(idActividad, monto);
        } catch (DAOException e) {
            throw new BOException("Error al registrar monto de la actividad", e);
        }
    }

    /**
     * Obtiene todas las actividades para el tablero Kanban.
     *
     * @return Lista de actividades ordenadas por estado y fecha.
     * @throws BOException Si hay un error en la capa DAO.
     */
    public List<Actividad> obtenerTableroKanban() throws BOException {
        try {
            return actividadDAO.obtenerTableroCompleto();
        } catch (DAOException e) {
            throw new BOException("Error al cargar el tablero Kanban", e);
        }
    }

    public List<Actividad> obtenerActividadesEntreFechas(LocalDate fInicial, LocalDate fFinal, Cliente clienteFiltro) throws BOException {
        try {
            // Validar fechas
            if (fInicial == null || fFinal == null) {
                throw new BOException("Debe especificar ambas fechas");
            }
            if (fInicial.isAfter(fFinal)) {
                throw new BOException("La fecha inicial no puede ser posterior a la final");
            }

            return actividadDAO.obtenerActividadesEntreFechas(fInicial, fFinal, clienteFiltro);
        } catch (DAOException e) {
            System.err.println("Error en BO: " + e.getMessage());
            throw new BOException("[BO] Error al buscar Actividades por fecha: " + e.getMessage(), e);
        }
    }
}
