package pe.acex.comercias.actividad;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.accounts.AccountAuthenticatorActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.api.RetrofitInstance;
import pe.acex.comercias.api.interfaz.UsuarioInterface;
import pe.acex.comercias.auth.SessionManager;
import pe.acex.comercias.modelos.Login;
import pe.acex.comercias.modelos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AccountAuthenticatorActivity {
    AlertDialog alertDialog;
    @BindView(R.id.txt_olvidarcontraActivity) TextView recuperarContra;
    @BindView(R.id.txt_registrarseActivity) TextView registrase;

    @BindView(R.id.edt_emailActivity) EditText email;
    @BindView(R.id.edt_contrasenaActivity) EditText contrasena;

    @BindView(R.id.btn_ingresarLoginActivity) Button ingresar;
    LoginButton ingresarFacebook;

    private CallbackManager callbackManager;

    String mEmail, mPassword;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        email.addTextChangedListener(mTextWatcher);
        contrasena.addTextChangedListener(mTextWatcher);
        checkFieldsForEmptyValues();

        session = new SessionManager(getApplicationContext());


        recuperarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogA();
            }
        });

        registrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });


        ingresarFacebook = (LoginButton) findViewById(R.id.btn_loginFacebook);
        ingresarFacebook.setReadPermissions("public_profile, email");

        ingresarFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Launcher.logueadoFacebook =true;
                Launcher.logueadoEmail =false;
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();


        }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,"Cancel Login",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this,"Error Login",Toast.LENGTH_SHORT).show();
            }
        });


        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmail = email.getText().toString();
                mPassword = contrasena.getText().toString();

                ConexionLogin(email.getText().toString(),contrasena.getText().toString());
            }
        });
    }


    private void alertDialogA() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this, R.style.AlertDialogRecuperarContra);
        dialog.setTitle("RECUPERAR CONTRASEÑA");
        dialog.setMessage("Ingresa tu Correo Electrónico");

        final EditText edittext = new EditText(getApplication());
        edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dialog.setView(edittext);
        dialog.setCancelable(false);


        dialog.setPositiveButton("RECUPERAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        int which) {
                    }
                }).setCancelable(false);
        dialog.setNegativeButton("CANCELAR",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog=dialog.create();
        alertDialog.show();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };

    private void checkFieldsForEmptyValues(){
        if(email.getText().toString().isEmpty()|| contrasena.getText().toString().isEmpty()){
            ingresar.setBackground(ContextCompat.getDrawable(this, R.drawable.button_border1));
            ingresar.setEnabled(false);
        } else {
            ingresar.setEnabled(true);
            ingresar.setBackground(ContextCompat.getDrawable(this, R.drawable.button_border));
        }
    }

    private void irPantallaPrincipal(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        setResult(AccountAuthenticatorActivity.RESULT_CANCELED);
        super.onBackPressed();
    }

    private String token;

    private String ConexionLogin(String us, String cont){
        Login login = new Login(us,cont);
        UsuarioInterface apiService = RetrofitInstance.getComerciasApi().create(UsuarioInterface.class);
        Call<User> call = apiService.getLogin(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    //getDatosUsuario(token);

                    session.createLoginSession(token);
                    finish();



                }else {
                    Toast.makeText(MainActivity.activity, "DATOS INCORRECTOS " , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.activity, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });

        return token;
    }


}
