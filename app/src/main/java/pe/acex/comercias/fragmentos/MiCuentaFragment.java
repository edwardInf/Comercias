package pe.acex.comercias.fragmentos;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.R;
import pe.acex.comercias.actividad.CuponesActivity;
import pe.acex.comercias.actividad.EnviadosActivity;
import pe.acex.comercias.actividad.LoginActivity;
import pe.acex.comercias.actividad.Ordenes;
import pe.acex.comercias.actividad.PendienteEnvio;
import pe.acex.comercias.actividad.PendientePago;
import pe.acex.comercias.actividad.PendienteValoracion;
import pe.acex.comercias.actividad.PerfilActivity;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.auth.SessionManager;

public class MiCuentaFragment extends Fragment {

    @BindView(R.id.img_perfilMiCuenta) CircleImageView imagenPerfil;
    @BindView(R.id.txt_emailUsuario) TextView emailUsuario;
    @BindView(R.id.txt_nombreUsuario) TextView nombreUsuario;

    @BindView(R.id.txt_iniciarSesion) TextView iniciarSesion;
    @BindView(R.id.layout_datosMiCuenta) LinearLayout datosMiCuenta;


    private ProfileTracker profileTracker;
    private SessionManager session;
    private Unbinder unbinder;
    String nombres, email, foto;

    public MiCuentaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_mi_cuenta,null);
        unbinder = ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_miCuentaFragment);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        /*profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayPerfilInfoFacebook(currentProfile);
                }
            }
        };
*/
        session = new SessionManager(getActivity());

        nombres = getArguments().getString("nombre");
        email = getArguments().getString("email");
        foto = getArguments().getString("foto");


        comprobarLoginFacebook();
        Launcher.esMiCuenta = true;

        return view;
    }


    private void comprobarLoginFacebook(){
        if (AccessToken.getCurrentAccessToken() == null) {
            //goLoginScreen();
            iniciarSesion.setVisibility(View.VISIBLE);
            datosMiCuenta.setVisibility(View.GONE);

            Glide.with(getActivity())
                    .load(R.drawable.sin_usuario)
                    .into(imagenPerfil);

            Launcher.logueadoFacebook = false;

            comprobarLoginEmail();

        } else {

            if (nombres==null || email==null || foto ==null){
                Toast.makeText(getActivity(), "ERROR $404", Toast.LENGTH_LONG).show();
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        if (currentProfile != null) {
                            displayPerfilInfoFacebook(currentProfile);
                        }
                    }
                };
            }else {
                nombreUsuario.setText(nombres);
                emailUsuario.setText(email);
                Glide.with(getActivity())
                        .load(foto)
                        .into(imagenPerfil);
            }


            //requestEmailFacebook(AccessToken.getCurrentAccessToken());
            /*Profile perfil = Profile.getCurrentProfile();
            if (perfil != null) {
                displayPerfilInfoFacebook(perfil);
                Launcher.logueadoFacebook = true;
            } else {
                Profile.fetchProfileForCurrentAccessToken();
            }*/
        }

    }

    private void comprobarLoginEmail(){
        if (session.isLoggedIn()){

            Launcher.logueadoEmail = true;
            iniciarSesion.setVisibility(View.GONE);
            datosMiCuenta.setVisibility(View.VISIBLE);
            if (nombres==null || email==null){
                Toast.makeText(getActivity(), "ERROR $404", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getActivity(), "SIN ERROR", Toast.LENGTH_LONG).show();
                nombreUsuario.setText(getArguments().getString("nombre"));
                emailUsuario.setText(getArguments().getString("email"));
            }


        }else {
            Launcher.logueadoEmail = false;

        }
    }

    private void cargarDatosUsuario(String k) {
        Conexion.getDatosUsuario(k);
        //emailUsuario.setText(Launcher.dataListUser.get(1));

    }

    private void displayPerfilInfoFacebook(Profile profile) {
        String nombre = profile.getName();
        String fotoUrl = profile.getProfilePictureUri(100, 100).toString();
        nombreUsuario.setText(nombre);
        Glide.with(getActivity())
                .load(fotoUrl)
                .into(imagenPerfil);
    }

    private void setEmail(String email) {
        emailUsuario.setText(email);
    }

    private void requestEmailFacebook(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (response.getError() != null) {
                    Toast.makeText(getActivity(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    String email = object.getString("email");
                    setEmail(email);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //profileTracker.stopTracking();
        unbinder.unbind();

    }

    private void goLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick({  R.id.layout_usuarioMiCuenta, R.id.layout_ordenes, R.id.layout_cupones, R.id.txt_perfilMiCuenta,
            R.id.ly_pedidosEnviados, R.id.ly_pendienteValoracion, R.id.ly_pendientePago, R.id.ly_pendienteEnvio})
    public void onBotonClick(View view) {
        Intent intent;
        switch(view.getId()) {
            case R.id.layout_usuarioMiCuenta:

                if (Launcher.logueadoEmail || Launcher.logueadoFacebook){
                    intent = new Intent(getActivity(), PerfilActivity.class);
                    intent.putExtra("n", nombreUsuario.getText().toString());
                    intent.putExtra("e", emailUsuario.getText().toString());
                    intent.putExtra("f", imagenPerfil.getDrawable().toString());
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_ordenes:
                intent = new Intent(getActivity(), Ordenes.class);
                startActivity(intent);
                break;
            case R.id.layout_cupones:
                intent = new Intent(getActivity(), CuponesActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_perfilMiCuenta:

                break;
            case R.id.ly_pedidosEnviados:
                intent = new Intent(getActivity(), EnviadosActivity.class);
                startActivity(intent);
                break;

            case R.id.ly_pendienteValoracion:
                intent = new Intent(getActivity(), PendienteValoracion.class);
                startActivity(intent);
                break;
            case R.id.ly_pendientePago:
                intent = new Intent(getActivity(), PendientePago.class);
                startActivity(intent);
                break;
            case R.id.ly_pendienteEnvio:
                intent = new Intent(getActivity(), PendienteEnvio.class);
                startActivity(intent);
                break;


        }
    }

}
