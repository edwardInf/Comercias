package pe.acex.comercias.api.interfaz;

import pe.acex.comercias.modelos.DireccionSave;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillingInterface {

    @POST("https://api.mercadopago.com/v1/customers/{customer_id}/cards")
    Call<DireccionSave> addDirecc(@Path("customer_id") int itemId, @Body DireccionSave direcc);
}
