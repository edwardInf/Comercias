package pe.acex.comercias.api.interfaz;

import pe.acex.comercias.api.data.SlidersServ;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderInterface {
    @GET("/api/sliders")
    Call<SlidersServ> getSliders();
}
