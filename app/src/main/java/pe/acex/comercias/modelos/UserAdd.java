package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class UserAdd {

    @SerializedName("name")
    private String nombre;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String contrasena;

    @SerializedName("phone")
    private String telefono;

    public UserAdd(String nombre, String email, String contrasena, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
