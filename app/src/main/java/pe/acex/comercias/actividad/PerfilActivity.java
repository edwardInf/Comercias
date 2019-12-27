package pe.acex.comercias.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.DireccionAdapter;
import pe.acex.comercias.auth.SessionManager;
import pe.acex.comercias.dialogs.TarjetaDialog;

public class PerfilActivity extends AppCompatActivity {


    @BindView(R.id.img_perfil) CircleImageView imagenPerfil;
    @BindView(R.id.txt_nombreUsuarioPerfil) TextView nombreUsuario;
    @BindView(R.id.txt_emailUsuarioPerfil) TextView emailUsuario;
    @BindView(R.id.txt_cumpleUsuarioPerfil) TextView nacimientoUsuario;
    @BindView(R.id.txt_sexoUsuarioPerfil) TextView sexoUsuario;

    @BindView(R.id.recycler_direccionPerfil) RecyclerView recyclerDireccion;
    @BindView(R.id.card_addDireccPerfil) CardView cardDireccionEmpty;

    private ProfileTracker profileTracker;
    private CallbackManager callbackManager;
    SessionManager session;
    TarjetaDialog dialogTarjeta;
    public static FragmentManager fragmentManager;
    private DireccionAdapter adapterDireccion;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_perfil);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayPerfilInfoFacebook(currentProfile);
                }
            }
        };
        session = new SessionManager(getApplicationContext());

        fragmentManager = getSupportFragmentManager();
        comprobarLoginFacebook();

        nombreUsuario.setText(getIntent().getExtras().getString("n"));
        emailUsuario.setText(getIntent().getExtras().getString("e"));
        if (getIntent().getExtras().getString("f").equals(null)) {
            Glide.with(getApplicationContext())
                    .load(getIntent().getExtras().getString("f"))
                    .into(imagenPerfil);
        } else {
            Glide.with(getApplicationContext())
                    .load(R.drawable.sin_usuario)
                    .into(imagenPerfil);
        }

        view = getWindow().getDecorView().findViewById(android.R.id.content);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @OnClick({R.id.btn_cerrarSesionPerfil, R.id.btn_addDireccPerfil, R.id.btn_addTarjetaPerfil, R.id.card_addDireccPerfil,
            R.id.card_addTarjetaPerfil})
    public void onBotonClick(View view) {
        final Intent intent;
        intent = new Intent(PerfilActivity.this, DireccionActivity.class);
        dialogTarjeta = new TarjetaDialog(PerfilActivity.this);
        switch (view.getId()) {

            case R.id.btn_cerrarSesionPerfil:
                if (AccessToken.getCurrentAccessToken() != null) {
                    logOutFacebook();
                } else {
                    session.logoutUser();
                }
                finish();
                break;
            case R.id.btn_addDireccPerfil:
                startActivity(intent);
                break;
            case R.id.btn_addTarjetaPerfil:
                dialogTarjeta.show(fragmentManager, "TarjetaDialog");
                break;

            case R.id.card_addDireccPerfil:
                startActivity(intent);
                break;
            case R.id.card_addTarjetaPerfil:
                dialogTarjeta.show(fragmentManager, "TarjetaDialog");
                break;
        }
    }

    private void comprobarLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();
        if (AccessToken.getCurrentAccessToken() == null) {
            Launcher.logueadoFacebook = false;
            comprobarLoginEmail();
        } else {

            if (getIntent().getExtras().getString("n") == null || getIntent().getExtras().getString("e") == null
                    || getIntent().getExtras().getString("f") == null) {
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

    }

    private void comprobarLoginEmail() {

        if (session.isLoggedIn()) {

            displayDatosEmail();
            Launcher.logueadoEmail = true;
            iniciar();
        } else {
            Launcher.logueadoEmail = false;
        }
    }

    private void logOutFacebook() {
        LoginManager.getInstance().logOut();
    }

    private void displayPerfilInfoFacebook(Profile profile) {
        String nombre = profile.getName();
        nombreUsuario.setText(nombre);

        String fotoUrl = profile.getProfilePictureUri(100, 100).toString();
        Glide.with(getApplicationContext())
                .load(fotoUrl)
                .into(imagenPerfil);

        view = getWindow().getDecorView().findViewById(android.R.id.content);

    }


    private void setEmailFacebook(String email) {
        emailUsuario.setText(email);
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

                    String email = object.getString("email");

                    setEmailFacebook(email);
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

    private void displayDatosEmail() {
        sexoUsuario.setText(MainActivity.sexo);
        nacimientoUsuario.setText(String.valueOf(formatearFecha(MainActivity.nacimiento)));
        if (Launcher.dataListUserDirecc.size() != 0) {
            cardDireccionEmpty.setVisibility(View.GONE);
            recyclerDireccion.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public String formatearFecha(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void iniciar() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerDireccion.setHasFixedSize(true);
        recyclerDireccion.setLayoutManager(layoutManager);
        adapterDireccion = new DireccionAdapter(getApplicationContext(), Launcher.dataListUserDirecc);
        recyclerDireccion.setAdapter(adapterDireccion);
    }

    private void showAddDireccionSnack(String c) {
        Snackbar snackbar = Snackbar.make(view,  c, Snackbar.LENGTH_LONG)
                .setAction("", null);

        //ACTION
        snackbar.setActionTextColor(view.getResources().getColor(R.color.blanco));
        View snackBarView = snackbar.getView();
        //BACKGROUND
        snackBarView.setBackgroundColor(view.getResources().getColor(R.color.resaltado));
        //MESSAGE

        snackbar.show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        if (Launcher.seGuardoDirecc){
            showAddDireccionSnack(getResources().getString(R.string.direccAgregada));
        }else {
            showAddDireccionSnack(getResources().getString(R.string.direccNoAgregada));

        }
    }


}