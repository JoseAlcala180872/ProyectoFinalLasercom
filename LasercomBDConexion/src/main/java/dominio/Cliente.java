/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

/**
 *  Entidad que representa un cliente en el sistema
 * @author Yeisi
 */
@Entity
@Table(name = "clientes") // Personaliza el nombre de la tabla en la BD
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(name = "nombre", length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "correo", unique = true, nullable = false)
    @Email(message = "El correo debe ser valido")
    private String correo;

    @Column(name = "telefono", length = 20)
    @Pattern(regexp = "\\d{10}", message = "El telefono debe tener 10 digitos")
    private String telefono;

    @Column(name = "direccion", length = 200)
    private String direccion;

    // Constructor por defecto
    public Cliente() {
    }

    // Constructor con ID
    public Cliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    // Constructor con todos los atributos de la clase
    public Cliente(Long idCliente, String nombre, String correo, String telefono, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cliente cliente = (Cliente) o;

        return Objects.equals(idCliente, cliente.idCliente);

    }

}
