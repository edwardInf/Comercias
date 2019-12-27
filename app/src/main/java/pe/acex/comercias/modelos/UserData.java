package pe.acex.comercias.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    private int id;

    @SerializedName("name")
    @Expose
    private String nombre;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("dob")
    @Expose
    private String nacimiento;

    @SerializedName("sex")
    @Expose
    private String sexo;

    @SerializedName("description")
    @Expose
    private String descripcion;

    @SerializedName("phone")
    @Expose
    private String telefono;

    public UserData(int id, String nombre, String email, String nacimiento, String sexo, String descripcion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.nacimiento = nacimiento;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
