package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class Tiendas {

    @SerializedName("name")
    private String nombre;

    @SerializedName("slug")
    private String slug;

    @SerializedName("image")
    private String imagen;

    @SerializedName("rating")
    private int rating;

    public Tiendas(String nombre, String slug, String imagen, int rating) {
        this.nombre = nombre;
        this.slug = slug;
        this.imagen = imagen;
        this.rating = rating;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSlug() {
        return slug;
    }

    public String getImagen() {
        return imagen;
    }

    public int getRating() {
        return rating;
    }
}
