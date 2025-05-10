/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import dominio.Actividad;
import dominio.EstadoActividad;
import excepciones.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * DAO para operaciones de persistencia de actividades
 *
 * @author FER
 */
public class ActividadDAO {

    private final EntityManager em;

    public ActividadDAO() {
        this.em = Conexion.getEntityManager();
    }

    /**
     * Obtiene todas las actividades para cargar el tablero completo. (Front,
     * echenle ganitas, organizar las columnas segun su estado va a ser trabajo
     * de ustedes en las pantallas).
     *
     * @return
     * @throws DAOException
     */
    public List<Actividad> obtenerTableroCompleto() throws DAOException {
        try {
            return em.createQuery(
                    "SELECT a FROM Actividad a ORDER BY a.estado, a.fechaTermino",
                    Actividad.class).getResultList();
        } catch (Exception e) {
            throw new DAOException("Error al cargar tablero Kanban", e);
        }
    }

    /**
     * Guarda una actividad (nueva o existente).
     *
     * @param actividad Actividad a guardar.
     * @return Actividad gestionada (con ID si era nueva).
     * @throws DAOException En caso de que falle el registro de la actividad.
     */
    public Actividad guardar(Actividad actividad) throws DAOException {
        Objects.requireNonNull(actividad, "La actividad no puede ser nula");
        validarActividadParaRegistro(actividad);

        try {
            em.getTransaction().begin();

            if (actividad.getIdActividad() == null) {
                em.persist(actividad);
            } else {
                actividad = em.merge(actividad);
            }

            em.getTransaction().commit();
            return actividad;
        } catch (Exception e) {
            rollbackTransaction();
            throw new DAOException("Error al guardar actividad", e);
        }
    }

    /**
     * Actualizar solo el estado de una actividad (Al mover entre columnas).
     *
     * @param idActividad ID de la actividad que tendra un nuevo estado.
     * @param nuevoEstado Estado al que cambiara la actividad.
     * @throws DAOException En caso de errores al cambiar de estado la
     * actividad.
     */
    public void moverActividad(Long idActividad, EstadoActividad nuevoEstado) throws DAOException {
        try {
            em.getTransaction().begin();
            Actividad actividad = em.find(Actividad.class, idActividad);

            if (actividad == null) {
                throw new DAOException("La actividad no existe");
            }

            actividad.setEstado(nuevoEstado);

            // Si se marca como TERMINADO, registrar la fecha real.
            if (nuevoEstado == EstadoActividad.TERMINADO) {
                actividad.setFechaRealTermino(LocalDate.now());
            }

            em.getTransaction().commit();
        } catch (DAOException e) {
            rollbackTransaction();
            throw e;
        } catch (Exception e) {
            rollbackTransaction();
            throw new DAOException("Error al mover actividad", e);
        }
    }

    /**
     * Actualiza la informacion editable de actividad (Solo para estados
     * pendiente o en progreso).
     *
     * @param actividadActualizada
     * @throws DAOException
     */
    public void actualizarInformacion(Actividad actividadActualizada) throws DAOException {
        try {
            em.getTransaction().begin();
            Actividad actividad = em.find(Actividad.class, actividadActualizada.getIdActividad());

            if (actividad == null) {
                throw new DAOException("La actividad no existe");
            }

            if (actividad.getEstado() == EstadoActividad.TERMINADO) {
                throw new DAOException("No se puede editar una actividad terminada");
            }

            // Solo actualizar campos editables (No cliente ni estado)
            actividad.setTitulo(actividadActualizada.getTitulo());
            actividad.setDescripcion(actividadActualizada.getDescripcion());
            actividad.setFechaTermino(actividadActualizada.getFechaTermino());

            em.getTransaction().commit();
        } catch (DAOException e) {
            rollbackTransaction();
            throw e;
        } catch (Exception e) {
            rollbackTransaction();
            throw new DAOException("Error al actualizar informacion de la actividad", e);
        }
    }

    /**
     * Registra el monto cuando una actividad se marca como terminada.
     *
     * @param idActividad
     * @param monto
     * @throws DAOException
     */
    public void registrarMontoTerminado(Long idActividad, BigDecimal monto) throws DAOException {
        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new DAOException("El monto no puede ser negativo");
        }

        try {
            em.getTransaction().begin();
            Actividad actividad = em.find(Actividad.class, idActividad);

            if (actividad == null) {
                throw new DAOException("La actividad no existe");
            }

            if (actividad.getEstado() != EstadoActividad.TERMINADO) {
                throw new DAOException("Solo se puede registrar monto en actividades terminadas");
            }

            actividad.setMonto(monto);
            em.getTransaction().commit();
        } catch (DAOException e) {
            rollbackTransaction();
            throw e;
        } catch (Exception e) {
            rollbackTransaction();
            throw new DAOException("Error al registrar monto de actividad", e);
        }
    }

    /**
     * Metodo para obtener actividades entre fechas para estadisticas
     * (Documentar mas adelante).
     *
     * @param fInicial
     * @param fFinal
     * @return
     * @throws DAOException
     */
    public List<Actividad> obtenerActividadesEntreFechas(LocalDate fInicial, LocalDate fFinal) throws DAOException {
        try {
            // Debug: Verificar parámetros
            System.out.println("Fechas recibidas - Inicial: " + fInicial + ", Final: " + fFinal);

            if (fInicial == null || fFinal == null) {
                throw new DAOException("Ambas fechas son requeridas");
            }

            // Usando CAST (para MySQL)
            String jpql = "SELECT a FROM Actividad a "
                    + "LEFT JOIN FETCH a.cliente c "
                    + "WHERE CAST(a.fechaRealTermino AS date) BETWEEN :fInicial AND :fFinal "
                    + "ORDER BY a.fechaRealTermino";
            TypedQuery<Actividad> query = em.createQuery(jpql, Actividad.class);
            query.setParameter("fInicial", fInicial);
            query.setParameter("fFinal", fFinal);
            List<Actividad> resultados = query.getResultList();
            System.out.println("Número de actividades encontradas: " + resultados.size()); // Debug
            return resultados;

        } catch (Exception e) {
            System.err.println("Error en DAO al buscar actividades: " + e.getMessage());
            e.printStackTrace();
            throw new DAOException("Error técnico al buscar actividades: " + e.getMessage(), e);
        }
    }

    // Validaciones auxiliares de DAO
    private void validarActividadParaRegistro(Actividad actividad) throws DAOException {
        if (actividad.getCliente() == null || actividad.getCliente().getIdCliente() == null) {
            throw new DAOException("El cliente es requerido para registrar una actividad");
        }
    }

    private void rollbackTransaction() {
        if (em != null && em.isOpen()) {
            em.getTransaction().rollback();
        }
    }

    public void cerrar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
