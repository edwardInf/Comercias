package pe.acex.comercias.api.interfaz;

import pe.acex.comercias.api.data.GruposServ;
import pe.acex.comercias.api.data.SubGruposServ;
import pe.acex.comercias.api.data.TendenciasServ;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GrupoInterface {

    @GET("/api/grupos")
    Call<GruposServ> getGrupos();

    @GET("/api/sub-grupos/{id}")
    Call<SubGruposServ> getSubGrupos(@Path("id") int id);


}
