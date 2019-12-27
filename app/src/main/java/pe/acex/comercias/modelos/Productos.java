package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Productos {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String titulo;

    @SerializedName("brand")
    private String marca;

    @SerializedName("condition")
    private String condicion;

    @SerializedName("stock_quantity")
    private int cantidad;

    @SerializedName("description")
    private String especificacion;

    @SerializedName("rating")
    private int calificacion;

    @SerializedName("min_order_quantity")
    private int minimaCantidad;

    @SerializedName("manufacturer_id")
    private int idProveedor;

    @SerializedName("free_shipping")
    private boolean envioGratis;

    @SerializedName("has_offer")
    private boolean tieneOferta;

    @SerializedName("product")
    private Product productoFinal;

    @SerializedName("images")
    private ArrayList<Imagen> imagenes;

    public Productos(int id, String titulo, String marca, String condicion, int cantidad, String especificacion, int calificacion, int minimaCantidad, int idProveedor, boolean envioGratis, boolean tieneOferta, Product productoFinal, ArrayList<Imagen> imagenes) {
        this.id = id;
        this.titulo = titulo;
        this.marca = marca;
        this.condicion = condicion;
        this.cantidad = cantidad;
        this.especificacion = especificacion;
        this.calificacion = calificacion;
        this.minimaCantidad = minimaCantidad;
        this.idProveedor = idProveedor;
        this.envioGratis = envioGratis;
        this.tieneOferta = tieneOferta;
        this.productoFinal = productoFinal;
        this.imagenes = imagenes;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMarca() {
        return marca;
    }

    public String getCondicion() {
        return condicion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public int getMinimaCantidad() {
        return minimaCantidad;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public boolean isEnvioGratis() {
        return envioGratis;
    }

    public boolean isTieneOferta() {
        return tieneOferta;
    }

    public Product getProductoFinal() {
        return productoFinal;
    }

    public ArrayList<Imagen> getImagenes() {
        return imagenes;
    }

    public class Imagen {
        private String path;

        public Imagen(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
