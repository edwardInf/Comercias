package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("id")
    private int id;

    @SerializedName("product_id")
    private int idProducto;

    @SerializedName("title")
    private String tituloCarrito;

    @SerializedName("stock_quantity")
    private int cantidadMax;

    @SerializedName("min_order_quantity")
    private int cantidadMin;

    @SerializedName("pivot")
    private Pivot pivot;

    @SerializedName("image")
    private Imagen imagen;

    public Items(int id, int idProducto, String tituloCarrito, int cantidadMax, int cantidadMin, Pivot pivot, Imagen imagen) {
        this.id = id;
        this.idProducto = idProducto;
        this.tituloCarrito = tituloCarrito;
        this.cantidadMax = cantidadMax;
        this.cantidadMin = cantidadMin;
        this.pivot = pivot;
        this.imagen = imagen;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTituloCarrito() {
        return tituloCarrito;
    }

    public void setTituloCarrito(String tituloCarrito) {
        this.tituloCarrito = tituloCarrito;
    }

    public int getCantidadMax() {
        return cantidadMax;
    }

    public void setCantidadMax(int cantidadMax) {
        this.cantidadMax = cantidadMax;
    }

    public int getCantidadMin() {
        return cantidadMin;
    }

    public void setCantidadMin(int cantidadMin) {
        this.cantidadMin = cantidadMin;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public class Pivot {
        @SerializedName("quantity")
        private int cantidad;

        @SerializedName("unit_price")
        private float precioUnit;

        public Pivot(int cantidad, int precioUnit) {
            this.cantidad = cantidad;
            this.precioUnit = precioUnit;
        }

        public int getCantidad() {
            return cantidad;
        }

        public float getPrecioUnit() {
            return precioUnit;
        }
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
