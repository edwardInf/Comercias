package pe.acex.comercias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.actividad.LoginActivity;
import pe.acex.comercias.actividad.TiendasActivity;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.auth.SessionManager;
import pe.acex.comercias.fragmentos.BaseFragment;
import pe.acex.comercias.fragmentos.CarritoFragment;
import pe.acex.comercias.fragmentos.MiCuentaFragment;
import pe.acex.comercias.utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static Activity activity;
    @BindView(R.id.navBottom_main) BottomNavigationView navBottom;
    final String action = Settings.ACTION_WIRELESS_SETTINGS;
    IntentResult result;
    public static boolean carrito;

    private ProfileTracker profileTracker;
    private SessionManager session;
    public static String nombre,fotoUrl,email,nacimiento,sexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Launcher.conexion){

            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

         /*   Toolbar mToolbar = findViewById(R.id.toolbar_main);
            setSupportActionBar(mToolbar);
*/
            fragmentManager = getSupportFragmentManager();
            activity = this;
            navegarBase();
            navBottom.setOnNavigationItemSelectedListener(this);
            BottomNavigationViewHelper.removeNavigationShiftMode(navBottom);

            if (Launcher.esMiCuenta){
                navBottom.setSelectedItemId(R.id.action_cuenta);
            }else {navBottom.setSelectedItemId(R.id.action_inicio);}

            profileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    if (currentProfile != null) {
                        displayPerfilInfoFacebook(currentProfile);
                    }
                }
            };
            session = new SessionManager(getApplicationContext());


            Conexion.ConexionCarrito();
            comprobarLoginFacebook();


        }else {
            setContentView(R.layout.activity_main2);
            Button BTNsinConexion = (Button)findViewById(R.id.btn_habilitarRed);
            BTNsinConexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(action));
                    finish();
                }
            });
        }




    }


    Intent i;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelaste el escaneo", Toast.LENGTH_LONG).show();
            } else {
                Uri contentUri = data.getData();
                i = new Intent(MainActivity.this, TiendasActivity.class).setData(contentUri);
                i.putExtra("url",String.valueOf(result.getContents()));
                startActivity(i);

                //startActivity(new Intent(this, TiendasActivity.class).setData(contentUri));
                //Toast.makeText(this, String.valueOf(result.getContents()), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void navegarBase() {
        Fragment fragment = new BaseFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_principal, fragment).commitAllowingStateLoss();
    }


/*
    private void setUpViewPagerAdapter() {
        tabLayout.addTab(tabLayout.newTab().setText("Inicio"));
        if (Launcher.dataList==null) {
            //Conexion.ConexionGrupos();
        }else {
            for (int j =0; j <Launcher.dataList.size();j++){
                tabLayout.addTab(tabLayout.newTab().setText(Launcher.dataList.get(j).getName()));
            }
        }

        tabLayout.getTabAt(0);
        bundle = new Bundle();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    navegarInicio();

                }else {
                    navegarGruposMainTab(Launcher.dataList.get(tab.getPosition()-1).getId());
                }
                Toast.makeText(MainActivity.this,String.valueOf(tab.getPosition()),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        */
/*TviewPagerAdapter.addFragment(new InicioFragment(), "INICIO");
        for (int j =0; j <Launcher.dataList.size();j++){
            viewPagerAdapter.addFragment(new GruposFragment(), Launcher.dataList.get(j).getName());
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (tabLayout.getTabCount()<=1){
            Toast.makeText(getApplicationContext(),"NO SE PUEDE",Toast.LENGTH_SHORT).show();
        }
        GruposFragment fragmentB = new GruposFragment();
        Bundle bundle = new Bundle();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()!=0){
                    bundle.putInt("id", tabLayout.getSelectedTabPosition());
                    fragmentB.setArguments(bundle);
                }
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("TAG1", "Posicion: " + tabLayout.getSelectedTabPosition() + " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;

                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });*//*

    }
*/

    FragmentManager fragmentManager;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_inicio:
                //BaseFragment.tabLayout.getTabAt(0).select();
                Launcher.esMiCuenta = false;
                navegarBase();
                break;
            case R.id.action_deseos:
                break;
            case R.id.action_mensaje:

                break;
            case R.id.action_carrito:
                navegarCarrito();
                break;
            case R.id.action_cuenta:

                navegarMiCuenta();
                break;
        }
        return true;
    }

    private void navegarLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void navegarCarrito() {
        Fragment fragment = new CarritoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_principal, fragment).commitAllowingStateLoss();
    }

    private void lanzarBottomMenu(Fragment fragment){
        if (AccessToken.getCurrentAccessToken() == null) {
            navegarLogin();
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_principal, fragment).commitAllowingStateLoss();
        }
    }


    private void navegarMiCuenta() {
        Bundle bundle = new Bundle();
        Fragment fragment = new MiCuentaFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (session.isLoggedIn()|| (AccessToken.getCurrentAccessToken() != null)){
            bundle.putString("nombre", nombre);
            bundle.putString("email", email);
            bundle.putString("foto", fotoUrl);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container_principal, fragment).commitAllowingStateLoss();
        }else {
            navegarLogin();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Launcher.esMiCuenta){
            navBottom.setSelectedItemId(R.id.action_cuenta);
        }else {
            navBottom.setSelectedItemId(R.id.action_inicio);
        }
        if (Launcher.seCreoUser){
            showAddUsuarioEmail();
        }
    }

    private void comprobarLoginFacebook(){
        if (AccessToken.getCurrentAccessToken() == null) {
            Launcher.logueadoFacebook = false;
            comprobarLoginEmail();

        } else {
            requestEmailFacebook(AccessToken.getCurrentAccessToken());
            Profile perfil = Profile.getCurrentProfile();
            if (perfil != null) {
                displayPerfilInfoFacebook(perfil);
                Launcher.logueadoFacebook = true;
            } else {
                Profile.fetchProfileForCurrentAccessToken();
            }
        }

    }

    private void comprobarLoginEmail(){
        if (session.isLoggedIn()){
            HashMap<String, String> user = session.getUserDetails();
            String key = user.get(SessionManager.KEY_TOKEN);
            Launcher.logueadoEmail = true;
            cargarDatosUsuario(key);
        }else {
            Launcher.logueadoEmail = false;

        }
    }
    private void cargarDatosUsuario(String k) {
        Conexion.getDatosUsuario(k);
        Conexion.getDireccionUsuario(k);

    }

    private void displayPerfilInfoFacebook(Profile profile) {
        nombre = profile.getName();
        fotoUrl = profile.getProfilePictureUri(100, 100).toString();
    }


    private void requestEmailFacebook(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (response.getError() != null) {
                    Toast.makeText(getApplicationContext(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    email = object.getString("email");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void showAddUsuarioEmail() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage("Tu usuario ha sido creado EXITOSAMENTE");
        alertDialog.show();
    }

}