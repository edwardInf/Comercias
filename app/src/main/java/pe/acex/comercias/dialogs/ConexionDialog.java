package pe.acex.comercias.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;

public class ConexionDialog  extends DialogFragment {
    AppCompatActivity activity;

    public ConexionDialog(final AppCompatActivity activity) {
        this.activity =activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }
    final String action = Settings.ACTION_WIRELESS_SETTINGS;

    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_conexion, null);

        builder.setView(v);

        Button red = (Button) v.findViewById(R.id.btn_habilitarRed);

        red.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.startActivity(new Intent(action));
                        dismiss();
                    }
                }
        );

        Launcher.dialog.setCancelable(false);


        return builder.create();
    }



}

