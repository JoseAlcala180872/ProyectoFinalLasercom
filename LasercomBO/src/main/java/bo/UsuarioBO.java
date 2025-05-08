/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.UsuarioDAO;
import dominio.Usuario;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author FER
 */
public class UsuarioBO {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    /**
     * Metodo con validaciones para registrar un usuario en la base de datos..
     * @param nombreUsuario Nombre del usuario a registrar.
     * @param contrasena Contrasena hasheada del usuario a registrar.
     * @param email Email del usuario a registrar.
     * @return True para registrar un usuario, False en caso de errores.
     */
    public boolean registrar(String nombreUsuario, String contrasena, String email) {
        // Validar existencia previa
        if (usuarioDAO.buscarPorNombreUsuario(nombreUsuario) != null || usuarioDAO.buscarPorEmail(email) != null) {
            System.out.println("Usuario o correo ya existen");
            return false;
        }
        
        // Validar email
        if (!esEmailValido(email)) {
            System.out.println("Correo electronico invalido.");
            return false;
        }
        
        // Hashear contraseña
        String hashed = BCrypt.withDefaults().hashToString(12, contrasena.toCharArray());
        
        // Crear y guardar usuario
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(hashed);
        usuario.setEmail(email);
        usuarioDAO.guardar(usuario);
        System.out.println("Registro exitoso");
        return true;
    }
    
    /**
     * Metodo con validaciones para iniciar sesion.
     * @param nombreUsuario Nombre de usuario para iniciar sesion.
     * @param contrasena Contrasena del usuario para iniciar sesion.
     * @return True para iniciar sesion, false en caso de errores.
     */
    public boolean login(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarioDAO.buscarPorNombreUsuario(nombreUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return false;
        }
        
        BCrypt.Result result = BCrypt.verifyer().verify(contrasena.toCharArray(), usuario.getContrasena());
        if (result.verified) {
            System.out.println("Inicio de sesion exitoso");
            return true;
        } else {
            System.out.println("Contraseña incorrecta");
            return false;
        }
    }
    
    /**
     * Expresion regular para validar el email.
     * @param email Email a validar.
     * @return Email validado.
     */
    public boolean esEmailValido(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,6}$");
    }    
}
