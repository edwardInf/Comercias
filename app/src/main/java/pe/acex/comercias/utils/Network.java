package pe.acex.comercias.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import pe.acex.comercias.R;

public class Network {
    public static boolean enabled = false;

    public static void mostrarDialogoParaHabilitarWifi(final AppCompatActivity activity) {
        final String action = Settings.ACTION_WIRELESS_SETTINGS;
        final String titulo = activity.getString(R.string.dialogo_habilitar_wifi_titulo);
        final String mensaje = activity.getString(R.string.dialogo_habilitar_wifi_mensaje);

        ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(titulo);
            builder.setMessage(mensaje);

            builder.setPositiveButton(activity.getString(R.string.dialogo_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    activity.startActivity(new Intent(action));
                }
            });
            builder.setNegativeButton(activity.getString(R.string.dialogo_cancelar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    activity.finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            enabled = false;
        } else {
            enabled = true;
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }
}

