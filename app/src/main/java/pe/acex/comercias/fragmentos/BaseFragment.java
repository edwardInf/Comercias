package pe.acex.comercias.fragmentos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.actividad.GruposActivity;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.actividad.LoginActivity;
import pe.acex.comercias.auth.SessionManager;
import pe.acex.comercias.utils.CaptureActivityPortrait;


public class BaseFragment extends Fragment implements View.OnClickListener{
    private FragmentActivity myContext;
    Unbinder unbinder;
    @BindView(R.id.toolbar_scan) LinearLayout BTNscan;
    //@BindView(R.id.img_toolbarLogin) CircleImageView imgLogin;
    //@BindView(R.id.toolbar_login) LinearLayout BTNlogin;
    //@BindView(R.id.tabs_grupos) TabLayout tabLayout;
    //@BindView(R.id.txt_loginEstadoFragmentBase) TextView estadoLogin;
    private Bundle bundle;
    SessionManager session;


    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_base,null);
        unbinder = ButterKnife.bind(this, view);

        Toolbar mToolbar = view.findViewById(R.id.toolbar_main);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        //comprobarLoginFacebook();
        //comprobarLoginEmail();
        navegarInicio();

        session = new SessionManager(getActivity());

        BTNscan.setOnClickListener(this);
        //BTNlogin.setOnClickListener(this);
        //setUpViewPagerAdapter();

        return view;
    }

    /*private void comprobarLoginFacebook(){
        if (AccessToken.getCurrentAccessToken() == null) {
            estadoLogin.setText("Iniciar");
            Glide.with(this)
                    .load(R.drawable.ic_login)
                    .into(imgLogin);
        } else {
            estadoLogin.setText("Cerrar");
            Profile perfil = Profile.getCurrentProfile();
            if (perfil != null) {
                displayPerfilInfo(perfil);
            } else {
                Profile.fetchProfileForCurrentAccessToken();
            }
        }

        if (Launcher.esMiCuenta){ navegarMiCuenta();
        }else { navegarInicio(); }
        }
*/
 /*   private void comprobarLoginEmail(){
        if (!Launcher.logueadoEmail) {
            estadoLogin.setText("Iniciar");
        } else {
            estadoLogin.setText("Cerrar");
        }
    }*/

    private boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

/*    private void setUpViewPagerAdapter() {
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
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }*/

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getContext(), GruposActivity.class);
        switch (view.getId()){
            case R.id.btn_envios:
                break;

            case R.id.toolbar_scan:
                onClickEscanear();
                break;
           /* case R.id.toolbar_login:
                //Launcher.esMiCuenta = false;

                if ((AccessToken.getCurrentAccessToken() == null) || (!Launcher.logueadoEmail)) {
                    navegarLogin();
                }

                if (Launcher.logueadoEmail){
                    session.logoutUser();
                    estadoLogin.setText("Iniciar");
                    Launcher.logueadoEmail = false;
                }else {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        logOut();
                        Glide.with(this)
                                .load(R.drawable.ic_login)
                                .into(imgLogin);
                    }
                }
                break;*/
        }
    }



    public void onClickEscanear() {
        IntentIntegrator intent = new IntentIntegrator(MainActivity.activity);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intent.setPrompt("Escanea Codigo QR de una Tienda");
        intent.setCameraId(0);
        intent.setCaptureActivity(CaptureActivityPortrait.class);
        intent.setBeepEnabled(true);
        intent.setBarcodeImageEnabled(true);
        intent.setOrientationLocked(true);
        intent.initiateScan();
    }

    private void navegarInicio() {
        Fragment fragment = new InicioFragment();
        FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_grupos, fragment).commitAllowingStateLoss();
    }
    private void navegarMiCuenta() {
        Fragment fragment = new MiCuentaFragment();
        FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_principal, fragment).commitAllowingStateLoss();
    }
    private void navegarLogin() {
        Intent intent = new Intent(MainActivity.activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void navegarGruposMainTab(int i) {
        GruposFragment fragment = new GruposFragment();
        FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_grupos, fragment).commitAllowingStateLoss();
        bundle.putInt("id", i);
        fragment.setArguments(bundle);
    }

  /*  private void logOut() {
        LoginManager.getInstance().logOut();
        estadoLogin.setText("Iniciar");
    }


    private void displayPerfilInfo(Profile profile) {
        String fotoUrl = profile.getProfilePictureUri(100, 100).toString();
        Glide.with(this)
                .load(fotoUrl)
                .into(imgLogin);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
