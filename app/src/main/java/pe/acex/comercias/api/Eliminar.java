package pe.acex.comercias.api;

import android.widget.Toast;

import pe.acex.comercias.MainActivity;
import pe.acex.comercias.api.data.CarritoServ;
import pe.acex.comercias.api.data.ProductosServ;
import pe.acex.comercias.api.data.RecientesServ;
import pe.acex.comercias.api.interfaz.CarritoInterface;
import pe.acex.comercias.api.interfaz.ProductoInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Eliminar {

    public static void EliminarCarrito(){
        ProductoInterface apiService = RetrofitInstance.getComerciasApi().create(ProductoInterface.class);
        Call<ProductosServ> call = apiService.delteRecientes(11);

        call.enqueue(new Callback<ProductosServ>() {
            @Override
            public void onResponse(Call<ProductosServ> call, Response<ProductosServ> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.activity, "EXITO" , Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.activity, "EXITO 2" , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductosServ> call, Throwable t) {
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
