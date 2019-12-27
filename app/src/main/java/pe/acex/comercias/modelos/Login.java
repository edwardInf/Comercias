package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("email")
    private String usuario;

    @SerializedName("password")
    private String contrasena;


    public Login(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
