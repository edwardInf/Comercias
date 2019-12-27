package pe.acex.comercias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.dialogs.ConexionDialog;
import pe.acex.comercias.modelos.Carrito;
import pe.acex.comercias.modelos.Direccion;
import pe.acex.comercias.modelos.Grupos;
import pe.acex.comercias.modelos.Items;
import pe.acex.comercias.modelos.Recientes;
import pe.acex.comercias.modelos.Tendencias;
import pe.acex.comercias.modelos.UserData;
import pe.acex.comercias.utils.ConnectivityHelper;

public class Launcher extends AppCompatActivity {
    public static ArrayList<Grupos> dataList;
    public static ArrayList<Tendencias> dataListTendencias;
    public static ArrayList<Recientes> dataListRecientes;
    public static ArrayList<Carrito> dataListCarrito;
    public static ArrayList<Items> dataListItems;
    public static ArrayList<Direccion> dataListUserDirecc;


    private String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE};
    public static final int MULTIPLE_PERMISSIONS = 10;
    public static ConexionDialog dialog;
    FragmentManager fragmentManager;
    public static Boolean conexion;
    public static Activity activityL;
    public static boolean esMiCuenta =false;
    public static boolean login=false;
    public static boolean sliders;
    public static boolean tendencias =false;
    public static boolean recientes=false;

    public static boolean logueadoEmail=false;
    public static boolean logueadoFacebook=false;
    public static boolean editarDirecc=false;
    public static boolean seGuardoDirecc = false;
    public static boolean seCreoUser =false;



    public static String LOGIN_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        activityL = this;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        dialog = new ConexionDialog(Launcher.this);
        fragmentManager = getSupportFragmentManager();


        checkPermissions();
        if (ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
            conexion=true;
            Conexion.ConexionGrupos(true, Launcher.this);
            Conexion.ConexionSliders();
            Conexion.ConexionTendencias();
            Conexion.ConexionRecientes();

        } else {
            conexion=false;
            Intent intent = new Intent(Launcher.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

}
