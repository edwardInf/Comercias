package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.actividad.GruposActivity;
import pe.acex.comercias.fragmentos.SubGruposFragment;
import pe.acex.comercias.modelos.Grupos;
import pe.acex.comercias.R;

public class GruposActivityAdapter extends RecyclerView.Adapter<GruposActivityAdapter.ViewHolder>  {
    private ArrayList<Grupos> itemGrup;
    private Context Scontext;
    private int mPosition = RecyclerView.NO_POSITION;
    View view;

    public GruposActivityAdapter(Context context, ArrayList<Grupos> apiObjects){
        this.Scontext = context;
        this.itemGrup =  apiObjects;
    }
    public void selectItem(int position){
        mPosition = position;
    }

    @NonNull
    @Override
    public GruposActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grupos_activity, viewGroup, false);

        return new GruposActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GruposActivityAdapter.ViewHolder viewHolder,final int i) {

        viewHolder.itemView.setSelected(mPosition == i);

        viewHolder.tv_grupo.setText(itemGrup.get(i).getName());
        switch (itemGrup.get(i).getIcon()){
            case "fa-bolt":
                viewHolder.img_grupo.setText(R.string.fa_bolt);
                break;
            case "fa-female":
                viewHolder.img_grupo.setText(R.string.fa_female);
                break;
            case "fa-phone":
                viewHolder.img_grupo.setText(R.string.fa_phone);
                break;
            case "fa-male":
                viewHolder.img_grupo.setText(R.string.fa_male);
                break;
            case "fa-diamond":
                viewHolder.img_grupo.setText(R.string.fa_diamond);
                break;
            case "fa-home":
                viewHolder.img_grupo.setText(R.string.fa_home);
                break;
            case "fa-shopping-bag":
                viewHolder.img_grupo.setText(R.string.fa_shopping_bag);
                break;
            case "fa-child":
                viewHolder.img_grupo.setText(R.string.fa_child);
                break;
            case "fa-soccer-ball-o":
                viewHolder.img_grupo.setText(R.string.fa_soccer_ball_o);
                break;
            case "fa-automobile":
                viewHolder.img_grupo.setText(R.string.fa_automobile);
                break;
            case "fa-cog":
                viewHolder.img_grupo.setText(R.string.fa_cog);
                break;
            case "fa-mouse-pointer":
                viewHolder.img_grupo.setText(R.string.fa_mouse_pointer);
                break;
            case "fa-heart":
                viewHolder.img_grupo.setText(R.string.fa_heart);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemGrup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_gruposActivity) TextView tv_grupo;
        @BindView(R.id.img_gruposActivity) TextView img_grupo;
        @BindView(R.id.layout_indicador) LinearLayout indicador;
        @BindView(R.id.layout_gruposAcitvity) LinearLayout indicadorFondo;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    notifyItemChanged(mPosition);
                    mPosition = getLayoutPosition();
                    notifyItemChanged(mPosition);

                    navegarSubgrupos(itemGrup.get(pos).getName(),
                            itemGrup.get(pos).getId());
                }
            });
        }


        private void navegarSubgrupos(String nombre, int n) {
            Bundle bundle = new Bundle();
            bundle.putString("nombre", nombre);
            bundle.putInt("id", n);
            Fragment fragment = new SubGruposFragment();
            FragmentTransaction transaction = ((GruposActivity) Scontext).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_subGrupos, fragment).commitAllowingStateLoss();
            fragment.setArguments(bundle);
        }


    }

}