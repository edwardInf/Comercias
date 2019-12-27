package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class Direccion {
    @SerializedName("address_type")
    private String tipoDireccion;

    @SerializedName("address_title")
    private String propietario;

    @SerializedName("address_line_1")
    private String direccion1;

    @SerializedName("address_line_2")
    private String direccion2;

    @SerializedName("city")
    private String ciudad;

    @SerializedName("state")
    private String provincia;

    @SerializedName("zip_code")
    private String codPostal;

    @SerializedName("country")
    private String pais;

    @SerializedName("phone")
    private String telefono;

    public Direccion(String tipoDireccion, String propietario, String direccion1, String direccion2, String ciudad, String provincia, String codPostal, String pais, String telefono) {
        this.tipoDireccion = tipoDireccion;
        this.propietario = propietario;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codPostal = codPostal;
        this.pais = pais;
        this.telefono = telefono;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
