package pe.acex.comercias.api.interfaz;

import pe.acex.comercias.api.data.ProductosServ;
import pe.acex.comercias.api.data.RecientesServ;
import pe.acex.comercias.api.data.TendenciasServ;
import pe.acex.comercias.modelos.Productos;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoInterface {
    @GET("/api/trending")
    Call<TendenciasServ> getTendencias();

    @GET("/api/recent")
    Call<RecientesServ> getRecientes();

    @GET("/api/product/{id}")
    Call<ProductosServ> getProductos(@Path("id") int id);

    @DELETE("/api/product/{id}")
    Call<ProductosServ> delteRecientes(@Path("id") int id);
}
