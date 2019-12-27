package pe.acex.comercias.api.interfaz;

import pe.acex.comercias.api.data.CarritoServ;
import pe.acex.comercias.api.data.TiendasServ;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarritoInterface {
    @GET("/api/carts")
    Call<CarritoServ> getCarrito();

    @DELETE("/api/carts/{id}")
    Call<Void> deleteCarrito(@Path("id") int itemId);


}
