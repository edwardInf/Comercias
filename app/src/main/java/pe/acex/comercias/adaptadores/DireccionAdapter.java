package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.actividad.DireccionActivity;
import pe.acex.comercias.modelos.Direccion;

public class DireccionAdapter extends RecyclerView.Adapter<DireccionAdapter.ViewHolder>{

    private ArrayList<Direccion> items;
    private Context Scontext;
    View view;


    public DireccionAdapter(Context context, ArrayList<Direccion> apiObjects){
        this.Scontext = context;
        this.items =  apiObjects;
    }

    @NonNull
    @Override
    public DireccionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_direccion, viewGroup, false);
        return new DireccionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DireccionAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_calle1.setText(items.get(i).getDireccion1());
        if (items.get(i).getDireccion2()==(null)){
            viewHolder.tv_calle2.setText("No hay Referencia");
        }else {
            viewHolder.tv_calle2.setText(items.get(i).getDireccion2());
        }
        viewHolder.tv_tipoDirecc.setText(items.get(i).getTipoDireccion());
        viewHolder.tv_dept.setText(items.get(i).getPais() +" - "+ items.get(i).getCiudad() +" - "+ items.get(i).getProvincia());
        viewHolder.tv_nombre.setText(items.get(i).getPropietario());
        viewHolder.tv_telef.setText(items.get(i).getTelefono());
        //viewHolder.tv_codPost.setText(items.get(i).getCodPostal());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_calle1Perfil) TextView tv_calle1;
        @BindView(R.id.txt_calle2Perfil) TextView tv_calle2;
        @BindView(R.id.txt_tipoDireccPerfil) TextView tv_tipoDirecc;
        @BindView(R.id.txt_departamentoPerfil) TextView tv_dept;
        @BindView(R.id.txt_nombreDireccPerfil) TextView tv_nombre;
        @BindView(R.id.txt_telefonoDireccPerfil) TextView tv_telef;
        @BindView(R.id.txt_editarDireccion) TextView tv_editarDirecc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            tv_editarDirecc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Launcher.editarDirecc = true;

                        Intent intent = new Intent(MainActivity.activity, DireccionActivity.class);
                        intent.putExtra("tipoDir", items.get(pos).getTipoDireccion());
                        intent.putExtra("nombreDir", items.get(pos).getPropietario());
                        intent.putExtra("direccion", items.get(pos).getDireccion1());
                        intent.putExtra("referencia", items.get(pos).getDireccion2());
                        intent.putExtra("ciudad", items.get(pos).getCiudad());
                        intent.putExtra("provincia", items.get(pos).getProvincia());
                        intent.putExtra("codPostal", items.get(pos).getCodPostal());
                        intent.putExtra("pais", items.get(pos).getPais());
                        intent.putExtra("telefono", items.get(pos).getTelefono());
                        MainActivity.activity.startActivity(intent);

                    }
                }
            });
        }


    }
}
