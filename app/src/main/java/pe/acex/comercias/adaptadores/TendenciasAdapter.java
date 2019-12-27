package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.actividad.ProductoActivity;
import pe.acex.comercias.modelos.Tendencias;
import pe.acex.comercias.R;

public class TendenciasAdapter extends RecyclerView.Adapter<TendenciasAdapter.ViewHolder>  {

    private ArrayList<Tendencias> itemGrup;
    private Context Scontext;
    View view;
    public TendenciasAdapter(Context context, ArrayList<Tendencias> apiObjects){
        this.Scontext = context;
        this.itemGrup =  apiObjects;
    }

    @NonNull
    @Override
    public TendenciasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tendencias, viewGroup, false);

        return new TendenciasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TendenciasAdapter.ViewHolder viewHolder,final int i) {

        viewHolder.tv_nombre.setText(itemGrup.get(i).getTitle());
        //viewHolder.tv_marca.setText(itemGrup.get(i).getBrand());
        viewHolder.tv_precio.setPaintFlags(viewHolder.tv_precio.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tv_precio.setText("S/. "+ String.valueOf(itemGrup.get(i).getSale_price()));
        viewHolder.tv_oferta.setText("S/. "+ String.valueOf(itemGrup.get(i).getOffer_price()));
        if (itemGrup.get(i).getImage()==null){
            Glide.with(view.getContext())
                    .load("http://www.tiendasistemas.com/wp-content/uploads/2019/06/XNV081MER032.png")
                    .thumbnail(0.5f)
                    .into(viewHolder.img_produto);
        }else {
            Glide.with(view.getContext())
                    .load(itemGrup.get(i).getImage().getPath())
                    .thumbnail(0.5f)
                    .into(viewHolder.img_produto);
        }
    }

    @Override
    public int getItemCount() {
        return itemGrup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_nombreProducto) TextView tv_nombre;
        //@BindView(R.id.txt_marcaProducto) TextView tv_marca;
        @BindView(R.id.txt_precioProducto) TextView tv_precio;
        @BindView(R.id.txt_precioOfertaProducto) TextView tv_oferta;
        @BindView(R.id.img_productos) ImageView img_produto;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(Launcher.activityL, ProductoActivity.class);
                        intent.putExtra("id", itemGrup.get(pos).getId());
                        intent.putExtra("nombre", itemGrup.get(pos).getTitle());
                        intent.putExtra("precio", itemGrup.get(pos).getSale_price());
                        intent.putExtra("oferta", itemGrup.get(pos).getOffer_price());
                        if (itemGrup.get(pos).getImage()==null){
                            intent.putExtra("img", "http://www.tiendasistemas.com/wp-content/uploads/2019/06/XNV081MER032.png");
                        }else {
                            intent.putExtra("img", itemGrup.get(pos).getImage().getPath());
                        }
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Launcher.activityL.startActivity(intent);

                    }
                }
            });
        }


    }

}
