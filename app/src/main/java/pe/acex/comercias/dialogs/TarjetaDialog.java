package pe.acex.comercias.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import pe.acex.comercias.R;

public class TarjetaDialog extends DialogFragment {
    AppCompatActivity activity;

    public TarjetaDialog(final AppCompatActivity activity) {
        this.activity =activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_config_tarjeta, null);

        builder.setView(v);

        Button red = (Button) v.findViewById(R.id.btn_guardarConfigTarjeta);

        red.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );



        return builder.create();
    }



}

