package pe.acex.comercias.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.material.textfield.TextInputLayout;
import com.tiper.MaterialSpinner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.R;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.auth.SessionManager;
import pe.acex.comercias.utils.Validar;

public class DireccionActivity extends AppCompatActivity {

    @BindView(R.id.txt_toolbarDireccion) TextView tituloDirecc;

    @BindView(R.id.edt_nombresAddDirecc) EditText nombres;
    //@BindView(R.id.edt_apellAddDirecc) EditText apellidos;
    @BindView(R.id.edt_celularAddDirecc) EditText celular;
    @BindView(R.id.edt_referenciaAddDirecc) EditText referencia;
    @BindView(R.id.edt_direccionAddDirecc) EditText direccion;
    @BindView(R.id.edt_distritoDirecc) EditText distrito;
    @BindView(R.id.edt_codPostal) EditText codPostal;


    @BindView(R.id.input_layout_nombres) TextInputLayout textNombres;
    //@BindView(R.id.input_layout_apellidos) TextInputLayout textApellidos;
    @BindView(R.id.input_layout_celular) TextInputLayout textCelular;
    @BindView(R.id.input_layout_addDirecc) TextInputLayout textDireccion;
    @BindView(R.id.input_layout_referenciaDirecc) TextInputLayout textReferencia;
    @BindView(R.id.input_layout_codPostal) TextInputLayout textCodPostal;
    @BindView(R.id.input_layout_distritoDirecc) TextInputLayout textDistrito;

    @BindView(R.id.spinner_departamento) MaterialSpinner departamento;
    @BindView(R.id.spinner_provincia) MaterialSpinner provincia;
    @BindView(R.id.spinner_tipoDirecc) MaterialSpinner tipoDireccion;

    private LocationManager mlocManager;
    private LocationListener locationListener;
    private ArrayAdapter<String> a;
    SessionManager session;
    String tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_direccion);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session = new SessionManager(getApplicationContext());

        if (Launcher.editarDirecc){
            tituloDirecc.setText(R.string.editarDirecc);
            cargarDatosEditar();
        }else {
            tituloDirecc.setText(R.string.addDirecc);
        }


        final String[] str = {"Principal", "Secundario", "Envios"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDireccion.setAdapter(adapter);

        final String[] departamentos = new String[]{"Amazonas", "Ancash", "Apurimac", "Arequipa", "Ayacucho", "Cajamarca",
                "Cusco", "Huancavelica", "Huanuco", "Ica", "Junin", "La Libertad", "Lambayeque", "Lima", "Loreto",
                "Madre De Dios", "Moquegua", "Pasco", "Piura", "Puno", "San Martin", "Tacna", "Tumbes", "Ucayali"};
        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departamentos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departamento.setAdapter(adapter2);

        // Listener cambios
        nombres.addTextChangedListener(new MyTextWatcher(nombres));
        celular.addTextChangedListener(new MyTextWatcher(celular));
        direccion.addTextChangedListener(new MyTextWatcher(direccion));
        referencia.addTextChangedListener(new MyTextWatcher(referencia));
        distrito.addTextChangedListener(new MyTextWatcher(distrito));


        departamento.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                showProvincias(i);
            }

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }
        });

    }


    private void locationInicio() {
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mandarDireccion(location.getLatitude(),location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    public void mandarDireccion(double lati, double longi) {

        if (lati != 0.0 && longi != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(lati, longi, 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion.setText(DirCalle.getThoroughfare());
                    mlocManager.removeUpdates(locationListener);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationInicio();
            }
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_nombresAddDirecc:
                    Validar.validarNombres(DireccionActivity.this,nombres,textNombres);
                    break;
                case R.id.edt_direccionAddDirecc:
                    Validar.validarNombres(DireccionActivity.this,direccion,textDireccion);
                    break;
                case R.id.edt_distritoDirecc:
                    Validar.validarNro(DireccionActivity.this,distrito,textDistrito);
                    break;

            }
        }
    }


    private void showProvincias(int i ){
        switch (i){
            case 0:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provAmazonas));

                break;
            case 1:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provAncash));
                break;
            case 2:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provApurimac));
                break;
            case 3:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provArequipa));
                break;
            case 4:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provAyacucho));
                break;
            case 5:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provCajamarca));
                break;
            case 6:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provCusco));
                break;
            case 7:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provHuancavelica));
                break;
            case 8:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provHuanuco));
                break;
            case 9:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provIca));
                break;
            case 10:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provJunin));
                break;
            case 11:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provLaLibertad));
                break;
            case 12:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provLambayeque));
                break;
            case 13:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provLima));
                break;
            case 14:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provLoreto));
                break;
            case 15:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provMadre));
                break;
            case 16:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provMoquegua));
                break;
            case 17:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provPasco));
                break;
            case 18:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provPiura));
                break;
            case 19:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provPuno));
                break;
            case 20:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provSanMartin));
                break;
            case 21:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provTacna));
                break;
            case 22:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provTumbes));
                break;
            case 23:
                a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.provUcayali));
                break;

        }
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provincia.setAdapter(a);
    }


    @OnClick({ R.id.btn_guardarAddDirecc, R.id.btn_getCalleAddDirecc})
    public void onBotonClick(View view) {
        switch(view.getId()) {
            case R.id.btn_guardarAddDirecc:
                if (((nombres.getText().toString().isEmpty())
                || celular.getText().toString().isEmpty() || direccion.getText().toString().isEmpty() || departamento.getSelectedItem().toString().isEmpty()
                || provincia.getSelectedItem().toString().isEmpty() || distrito.getText().toString().isEmpty())){
                    Toast.makeText(this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
                }else {
                    if (Launcher.editarDirecc){


                    }else {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            Toast.makeText(this, String.valueOf(AccessToken.getCurrentAccessToken()), Toast.LENGTH_SHORT).show();

                            //guardarDatos(String.valueOf(AccessToken.getCurrentAccessToken()));
                        } else {
                            HashMap<String, String> user = session.getUserDetails();
                            String key = user.get(SessionManager.KEY_TOKEN);
                            guardarDatos(key);
                        }
                    }
                }


                break;
            case R.id.btn_getCalleAddDirecc:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    locationInicio();
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void cargarDatosEditar(){
        nombres.setText(getIntent().getExtras().getString("nombreDir"));
        celular.setText(getIntent().getExtras().getString("telefono"));
        referencia.setText(getIntent().getExtras().getString("referencia"));
        direccion.setText(getIntent().getExtras().getString("direccion"));
        codPostal.setText(getIntent().getExtras().getString("codPostal"));
        distrito.setText(getIntent().getExtras().getString("provincia"));

    }

    private void guardarDatos(String token){
        Conexion.addDireccion(
                token,
                tipoDirecc(tipoDireccion.getSelectedItem().toString()),
                nombres.getText().toString(),
                direccion.getText().toString(),
                referencia.getText().toString(),
                departamento.getSelectedItem().toString(),
                departamento.getSelectedItem().toString()+"/"+provincia.getSelectedItem().toString()+ "/"+distrito.getText().toString(),
                codPostal.getText().toString(),
                "604",
                celular.getText().toString());
        finish();
    }

    private String tipoDirecc(String c){
        switch (c){
            case "Principal":
                tip = "Primary";
                break;
            case "Secundario":
                tip = "Billing";
            break;
            case "Envios":
                tip = "Shipping";
            break;

        }
        return tip;
    }


}
