package com.datateam.bd;

public class Usuario {

    private Integer id;
    private String correo;
    private String contrasena;
    private String rol;
    private String nombre;
    private String apellidos;

    public Usuario(Integer id, String correo, String contrasena, String rol, String nombre, String apellidos) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    
    

    public String getUsuario() {
        return correo;
    }

    public void setUsuario(String usuario) {
        this.correo = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean autentificar(String usuario, String contra) {
        if (usuario.equals(getUsuario()) && contrasena.equals(getContrasena())) {
            return true;
        }

        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", correo=" + correo + ", contrasena=" + contrasena + ", rol=" + rol + ", nombre=" + nombre + ", apellidos=" + apellidos + '}';
    }

    
}
