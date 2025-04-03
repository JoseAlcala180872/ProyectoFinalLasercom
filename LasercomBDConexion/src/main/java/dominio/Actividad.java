/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa una actividad dentro del sistema
 *
 * @author Yeisi
 */
@Entity
@Table(name = "actividades")
public class Actividad {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActividad;

    @Column(name = "titulo", nullable = false, length = 100)
    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_termino", nullable = false)
    @FutureOrPresent(message = "La fecha estimada debe ser hoy o en el futuro")
    private LocalDate fechaTermino; // Fecha estimada

    @Column(name = "fecha_real_termino")
    @PastOrPresent(message = "La fecha real debe ser hoy o en el pasado")
    private LocalDate fechaRealTermino; // Fecha real de termino (Se registrara al cambiar esta a terminado)

    @Enumerated(EnumType.STRING)
    private EstadoActividad estado = EstadoActividad.PENDIENTE;

    @Column(name = "monto", columnDefinition = "DECIMAL(10,2)")
    @PositiveOrZero(message = "El monto debe ser positivo")
    private BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    /**
     * Camba el estado de la actividad a TERMINADO y registra la fecha actual
     * como fehca real de termino
     */
    public void marcarComoTerminada() {
        if (this.estado != EstadoActividad.TERMINADO) {
            this.estado = EstadoActividad.TERMINADO;
            this.fechaRealTermino = LocalDate.now(); // Fecha actual del sistema
        }
    }
    
    public boolean isEditable() {
        return estado != EstadoActividad.TERMINADO;
    }
    
    public void avanzarEstado() {
        this.estado = EstadoActividad.values()[this.estado.ordinal() + 1];
    }

    // Constructor por defecto
    public Actividad() {
    }

    // Constructor con ID
    public Actividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    // Constructor con todos los atributos de la clase
    public Actividad(Long idActividad, String titulo, String descripcion, LocalDate fechaTermino, LocalDate fechaRealTermino, BigDecimal monto, Cliente cliente) {
        this.idActividad = idActividad;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaTermino = fechaTermino;
        this.fechaRealTermino = fechaRealTermino;
        this.monto = monto;
        this.cliente = cliente;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public LocalDate getFechaRealTermino() {
        return fechaRealTermino;
    }

    public void setFechaRealTermino(LocalDate fechaRealTermino) {
        this.fechaRealTermino = fechaRealTermino;
    }

    public EstadoActividad getEstado() {
        return estado;
    }

    public void setEstado(EstadoActividad estado) {
        this.estado = estado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        if (estado != EstadoActividad.TERMINADO && monto != null) {
            throw new IllegalStateException("El monto solo puede asignarse cuando la actividad esta"
                    + " terminada");
        }
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActividad);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Actividad actividad = (Actividad) o;
        return Objects.equals(idActividad, actividad.idActividad);
    }

}
