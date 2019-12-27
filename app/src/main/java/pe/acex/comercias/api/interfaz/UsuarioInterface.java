package pe.acex.comercias.api.interfaz;

import pe.acex.comercias.api.data.DireccionServ;
import pe.acex.comercias.modelos.DireccionSave;
import pe.acex.comercias.modelos.Login;
import pe.acex.comercias.modelos.User;
import pe.acex.comercias.modelos.UserAdd;
import pe.acex.comercias.modelos.UserData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UsuarioInterface {

    @POST("/api/login")
    @Headers({"Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest"})
    Call<User> getLogin(@Body Login login);

    @GET("/api/user")
    Call<UserData> getDataUser(@Header("Authorization") String authToken);

    @GET("/api/user/addresses")
    Call<DireccionServ> getDireccion(@Header("Authorization") String authToken);


    @POST("/api/user/addresses/guardar")
    Call<DireccionSave> addDirecc(@Header("Authorization") String authToken, @Body DireccionSave direcc);

    @POST("/api/register")
    @Headers({"Content-Type: application/json", "X-Requested-With: XMLHttpRequest"})
    Call<UserAdd> addUserEmail(@Body UserAdd login);

}
