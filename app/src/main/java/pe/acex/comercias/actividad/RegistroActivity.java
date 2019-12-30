package pe.acex.comercias.actividad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.utils.Validar;

public class RegistroActivity extends AppCompatActivity {

    @BindView(R.id.edt_crearNombre) EditText nombre;
    @BindView(R.id.edt_crearNumeroContacto) EditText numero;
    @BindView(R.id.edt_crearCorreoElectronico) EditText email;
    @BindView(R.id.edt_crearContrasena) EditText contra;

    @BindView(R.id.btn_crearUsuario) Button crear;
    @BindView(R.id.checkBox_acuerdo) CheckBox deAcuerdo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        nombre.addTextChangedListener(mTextWatcher);
        numero.addTextChangedListener(mTextWatcher);
        email.addTextChangedListener(mTextWatcher);
        contra.addTextChangedListener(mTextWatcher);

        checkFieldsForEmptyValues();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };

    private void checkFieldsForEmptyValues(){

        if((nombre.getText().toString().isEmpty()|| numero.getText().length()!=9||
                email.getText().toString().isEmpty()|| contra.getText().toString().isEmpty())){
            crear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_border1));
            crear.setEnabled(false);

        } else {
            crear.setEnabled(true);
            crear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_border));

        }
    }

    private void crearUsuario(){
        if (Conexion.addUsuarioEmail(
                nombre.getText().toString(),
                email.getText().toString(),
                contra.getText().toString(),
                numero.getText().toString())) {

            showAddUsuarioEmail();
        }
    }

    @OnClick( R.id.btn_crearUsuario)
    public void onBotonClick(View view) {
        if (deAcuerdo.isChecked()) {
            crearUsuario();
        } else {
            Toast.makeText(this, "TIENE QUE MARCAR 'ESTOY DE ACUERDO'" , Toast.LENGTH_LONG).show();
        }
    }

    private void showAddUsuarioEmail() {
        AlertDialog alertDialog = new AlertDialog.Builder(RegistroActivity.this).create();
        alertDialog.setTitle("Bienvenido a Comercias");
        alertDialog.setMessage("Tu usuario ha sido creado EXITOSAMENTE");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }
}
