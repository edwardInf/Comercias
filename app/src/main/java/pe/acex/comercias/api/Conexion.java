package pe.acex.comercias.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.actividad.DireccionActivity;
import pe.acex.comercias.actividad.PerfilActivity;
import pe.acex.comercias.adaptadores.SubGruposAdapter;
import pe.acex.comercias.api.data.CarritoServ;
import pe.acex.comercias.api.data.DireccionServ;
import pe.acex.comercias.api.data.GruposServ;
import pe.acex.comercias.api.data.RecientesServ;
import pe.acex.comercias.api.data.SlidersServ;
import pe.acex.comercias.api.data.SubGruposServ;
import pe.acex.comercias.api.data.TendenciasServ;
import pe.acex.comercias.api.interfaz.CarritoInterface;
import pe.acex.comercias.api.interfaz.GrupoInterface;
import pe.acex.comercias.api.interfaz.ProductoInterface;
import pe.acex.comercias.api.interfaz.SliderInterface;
import pe.acex.comercias.api.interfaz.UsuarioInterface;
import pe.acex.comercias.fragmentos.GruposFragment;
import pe.acex.comercias.fragmentos.MiCuentaFragment;
import pe.acex.comercias.fragmentos.SubGruposFragment;
import pe.acex.comercias.modelos.Direccion;
import pe.acex.comercias.modelos.DireccionSave;
import pe.acex.comercias.modelos.Items;
import pe.acex.comercias.modelos.Sliders;
import pe.acex.comercias.modelos.SubGrupos;
import pe.acex.comercias.modelos.User;
import pe.acex.comercias.modelos.UserAdd;
import pe.acex.comercias.modelos.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Conexion {


    public static void ConexionGrupos(boolean launcher, Activity con) {
        GrupoInterface service = RetrofitInstance.getComerciasApi().create(GrupoInterface.class);
        Call<GruposServ> call = service.getGrupos();
        call.enqueue(new Callback<GruposServ>() {
            @Override
            public void onResponse(Call<GruposServ> call, Response<GruposServ> response) {
                if(response.isSuccessful()){
                    GruposServ jsonResponse = response.body();
                    Launcher.dataList = new ArrayList<>(Arrays.asList(jsonResponse.getData()));

                    if (launcher){
                        Timer myTimer = new Timer();
                        myTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                con.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(con,MainActivity.class);
                                        con.startActivity(intent);
                                        con.finish();
                                    }
                                });
                            }
                        }, 1500);}

                }
            }
            @Override
            public void onFailure(Call<GruposServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static ArrayList<SubGrupos> subGrupos;
    public static void ConexionSubGrupos(int id, boolean bandera_grupos) {
        GrupoInterface service = RetrofitInstance.getComerciasApi().create(GrupoInterface.class);
        Call<SubGruposServ> call = service.getSubGrupos(id);
        call.enqueue(new Callback<SubGruposServ>() {
            @Override
            public void onResponse(Call<SubGruposServ> call, Response<SubGruposServ> response) {
                if(response.isSuccessful()){
                    SubGruposServ jsonResponse = response.body();
                    subGrupos = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    if (bandera_grupos){
                        GruposFragment.subAdapter = new SubGruposAdapter(GruposFragment.activity, subGrupos,false);
                        GruposFragment.recyclerView.setAdapter(GruposFragment.subAdapter);
                    }else {
                        SubGruposFragment.subAdapter = new SubGruposAdapter(SubGruposFragment.activity, subGrupos,true);
                        SubGruposFragment.recyclerView.setAdapter(SubGruposFragment.subAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<SubGruposServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static ArrayList<Sliders> sliders;

    public static void ConexionSliders() {
        SliderInterface service = RetrofitInstance.getComerciasApi().create(SliderInterface.class);
        Call<SlidersServ> call = service.getSliders();
        call.enqueue(new Callback<SlidersServ>() {
            @Override
            public void onResponse(Call<SlidersServ> call, Response<SlidersServ> response) {
                if(response.isSuccessful()){
                    SlidersServ jsonResponse = response.body();
                    sliders = new ArrayList<>(Arrays.asList(jsonResponse.getData()));

                    //InicioFragment.slider.setAdapter(new SliderAdapter(MainActivity.activity,sliders));
                    //InicioFragment.indicador.setupWithViewPager(InicioFragment.slider, true);
                    if (sliders.size()>0){
                        Launcher.sliders = true;
                    }else {
                        Launcher.sliders = false;
                    }
                }
            }
            @Override
            public void onFailure(Call<SlidersServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void ConexionTendencias() {
        ProductoInterface service = RetrofitInstance.getComerciasApi().create(ProductoInterface.class);
        Call<TendenciasServ> call = service.getTendencias();
        call.enqueue(new Callback<TendenciasServ>() {
            @Override
            public void onResponse(Call<TendenciasServ> call, Response<TendenciasServ> response) {
                if(response.isSuccessful()){
                    TendenciasServ jsonResponse = response.body();
                    Launcher.dataListTendencias = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    if (Launcher.dataListTendencias.size()>0) {
                        Launcher.tendencias = true;
                    }else {
                        Launcher.tendencias = false;
                    }
                }
            }
            @Override
            public void onFailure(Call<TendenciasServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static void ConexionRecientes() {
        ProductoInterface service = RetrofitInstance.getComerciasApi().create(ProductoInterface.class);
        Call<RecientesServ> call = service.getRecientes();
        call.enqueue(new Callback<RecientesServ>() {
            @Override
            public void onResponse(Call<RecientesServ> call, Response<RecientesServ> response) {
                if(response.isSuccessful()){
                    RecientesServ jsonResponse = response.body();
                    Launcher.dataListRecientes = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    if (Launcher.dataListRecientes.size()>0) {
                        Launcher.recientes = true;
                    }else {
                        Launcher.recientes = false;
                    }
                }
            }
            @Override
            public void onFailure(Call<RecientesServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void ConexionCarrito(){
        CarritoInterface apiService = RetrofitInstance.getComerciasApi().create(CarritoInterface.class);
        Call<CarritoServ> call = apiService.getCarrito();
        call.enqueue(new Callback<CarritoServ>() {
            @Override
            public void onResponse(Call<CarritoServ> call, Response<CarritoServ> response) {
                if(response.isSuccessful()){
                    CarritoServ jsonResponse = response.body();
                    Launcher.dataListCarrito = new ArrayList<>(Arrays.asList(jsonResponse.getData()));

                    if (Launcher.dataListCarrito.size()>0){
                        Launcher.dataListItems = new ArrayList<Items>();
                        Launcher.dataListItems.addAll(Launcher.dataListCarrito.get(0).getItems());
                        if (Launcher.dataListItems.size()==0){
                            MainActivity.carrito = false;
                        }else {
                            MainActivity.carrito = true;
                        }
                    }
                    //Toast.makeText(MainActivity.activity,String.valueOf(Launcher.dataListCarrito.get(0).getItems().size()) , Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<CarritoServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });
    }


    public static void getDatosUsuario(String t){
        UsuarioInterface apiService = RetrofitInstance.getComerciasApi().create(UsuarioInterface.class);
        Call<UserData> call = apiService.getDataUser("Bearer "+t);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()){
                    UserData jsonResponse = response.body();



                    MainActivity.nombre = (jsonResponse.getNombre());
                    MainActivity.email = (jsonResponse.getEmail());
                    MainActivity.nacimiento = (jsonResponse.getNacimiento());
                    MainActivity.sexo = (jsonResponse.getSexo());

                    //MiCuentaFragment.nombreUsuario.setText(jsonResponse.getNombre());
                    //MiCuentaFragment.emailUsuario.setText(jsonResponse.getEmail());

                }else {
                    Toast.makeText(MainActivity.activity,String.valueOf(response.errorBody()) , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });

    }

    public static void getDireccionUsuario(String t){
        UsuarioInterface apiService = RetrofitInstance.getComerciasApi().create(UsuarioInterface.class);
        Call<DireccionServ> call = apiService.getDireccion("Bearer "+t);
        call.enqueue(new Callback<DireccionServ>() {
            @Override
            public void onResponse(Call<DireccionServ> call, Response<DireccionServ> response) {
                if(response.isSuccessful()){
                    DireccionServ jsonResponse = response.body();
                    Launcher.dataListUserDirecc = new ArrayList<>(Arrays.asList(jsonResponse.getAddress()));

                }else {
                    Toast.makeText(MainActivity.activity,String.valueOf(response.errorBody()) , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DireccionServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });

    }

    public static void addDireccion(String t, String tipo, String nomb, String direcc, String refer, String ciudad,
                                    String estado, String cod, String pais, String telef){
        DireccionSave direccion = new DireccionSave(tipo,nomb,direcc,refer,ciudad,estado,cod,pais,telef);
        UsuarioInterface apiService = RetrofitInstance.getComerciasApi().create(UsuarioInterface.class);
        Call<DireccionSave> call = apiService.addDirecc("Bearer "+t, direccion);
        call.enqueue(new Callback<DireccionSave>() {
            @Override
            public void onResponse(Call<DireccionSave> call, Response<DireccionSave> response) {
                if(response.isSuccessful()){
                    Launcher.seGuardoDirecc = true;
                }else {
                    Launcher.seGuardoDirecc = false;
                }
            }
            @Override
            public void onFailure(Call<DireccionSave> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Launcher.seGuardoDirecc = false;
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });
    }

    public static boolean addUsuarioEmail(String n, String e, String pa, String ph){
        UserAdd direccion = new UserAdd(n,e,pa,ph);
        UsuarioInterface apiService = RetrofitInstance.getComerciasApi().create(UsuarioInterface.class);
        Call<UserAdd> call = apiService.addUserEmail(direccion);
        call.enqueue(new Callback<UserAdd>() {
            @Override
            public void onResponse(Call<UserAdd> call, Response<UserAdd> response) {
                if(response.isSuccessful()){
                    Launcher.seCreoUser = true;
                    //Toast.makeText(MainActivity.activity, "REGISTRO EXITOSO ", Toast.LENGTH_LONG).show();
                }else {
                Toast.makeText(MainActivity.activity, "NO SE PUDO REGISTRAR " , Toast.LENGTH_LONG).show();
                    Launcher.seCreoUser = false;
                }
            }
            @Override
            public void onFailure(Call<UserAdd> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Launcher.seGuardoDirecc = false;
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });
        return Launcher.seCreoUser;
    }

}
