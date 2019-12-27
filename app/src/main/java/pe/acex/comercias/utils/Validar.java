package pe.acex.comercias.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import pe.acex.comercias.R;

import com.google.android.material.textfield.TextInputLayout;

public class Validar {

    public static boolean validarNombres(Activity c, EditText inputName, TextInputLayout inputLayoutName) {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(c.getString(R.string.err_msg_name));
            requestFocus(inputName,c);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validarEmail(Activity c, EditText inputEmail, TextInputLayout inputLayoutEmail) {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(c.getString(R.string.err_msg_email));
            requestFocus(inputEmail,c);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validarPassword(Activity c, EditText inputPassword, TextInputLayout inputLayoutPassword) {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(c.getString(R.string.err_msg_password));
            requestFocus(inputPassword,c);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validarCelular(Activity c, EditText inputCelular, TextInputLayout inputLayoutCelular) {
        if (inputCelular.getText().length()<9) {
            inputLayoutCelular.setError("Ingresa un numero Correcto");
            requestFocus(inputCelular,c);
            return false;
        } else {
            inputLayoutCelular.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validarNro(Activity c, EditText inputNro, TextInputLayout inputLayoutNro) {
        if (inputNro.getText().length()==0) {
            inputLayoutNro.setError("Ingresa el Distrito");
            requestFocus(inputNro,c);
            return false;
        } else {
            inputLayoutNro.setErrorEnabled(false);
        }
        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void requestFocus(View view, Activity c) {
        if (view.requestFocus()) {
            c.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

