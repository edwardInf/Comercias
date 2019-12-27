package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("description")
    private String descripcion;


    public Product(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
