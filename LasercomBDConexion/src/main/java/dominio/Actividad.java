/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Yeisi
 */
@Entity  
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idActividad;
    private Cliente cliente;
    private int idTrabajador;
    private String descripcion;
    private Date fechaCreada;
    private Date fechaRealizar;
    private Enum estado;
    private double monto;
    private boolean estaTerminado;

    public Actividad() {
    }

    public Actividad(int idActividad, Cliente cliente, int idTrabajador, String descripcion, Date fechaCreada, Date fechaRealizar, Enum estado, double monto, boolean estaTerminado) {
        this.idActividad = idActividad;
        this.cliente = cliente;
        this.idTrabajador = idTrabajador;
        this.descripcion = descripcion;
        this.fechaCreada = fechaCreada;
        this.fechaRealizar = fechaRealizar;
        this.estado = estado;
        this.monto = monto;
        this.estaTerminado = estaTerminado;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreada() {
        return fechaCreada;
    }

    public void setFechaCreada(Date fechaCreada) {
        this.fechaCreada = fechaCreada;
    }

    public Date getFechaRealizar() {
        return fechaRealizar;
    }

    public void setFechaRealizar(Date fechaRealizar) {
        this.fechaRealizar = fechaRealizar;
    }

    public Enum getEstado() {
        return estado;
    }

    public void setEstado(Enum estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isEstaTerminado() {
        return estaTerminado;
    }

    public void setEstaTerminado(boolean estaTerminado) {
        this.estaTerminado = estaTerminado;
    }

    @Override
    public String toString() {
        return "Actividad{" + "idActividad=" + idActividad + ", cliente=" + cliente + ", idTrabajador=" + idTrabajador + ", descripcion=" + descripcion + ", fechaCreada=" + fechaCreada + ", fechaRealizar=" + fechaRealizar + ", estado=" + estado + ", monto=" + monto + ", estaTerminado=" + estaTerminado + '}';
    }
    
    
    
}
