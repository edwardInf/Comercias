package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.R;
import pe.acex.comercias.actividad.ProductoActivity;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.modelos.Recientes;
import pe.acex.comercias.widgets.GridSpacingItemDecoration;

public class RecientesAdapter extends RecyclerView.Adapter<RecientesAdapter.ViewHolder>{

    private ArrayList<Recientes> items;
    private Context Scontext;
    View view;

    public RecientesAdapter(Context context, ArrayList<Recientes> apiObjects){
        this.Scontext = context;
        this.items =  apiObjects;
    }

    @NonNull
    @Override
    public RecientesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recientes, viewGroup, false);

        return new RecientesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecientesAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_nombre.setText(items.get(i).getTitle());
        if (items.get(i).getImage()==null){
            Glide.with(view.getContext())
                    .load("http://www.tiendasistemas.com/wp-content/uploads/2019/06/XNV081MER032.png")
                    .thumbnail(0.5f)
                    .into(viewHolder.img_produto);
        }else {

            Glide.with(view.getContext())
                    .load(items.get(i).getImage().getPath())
                    .apply(new RequestOptions()
                            .fitCenter()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(Target.SIZE_ORIGINAL))
                    .into(viewHolder.img_produto);
        }
        if ((items.get(i).getOffer_price())==null){
            viewHolder.tv_oferta.setText("S/. "+ String.valueOf(items.get(i).getSale_price()));
            viewHolder.tv_precio.setVisibility(View.GONE);
        }else {
            viewHolder.tv_precio.setText("S/. "+ String.valueOf(items.get(i).getSale_price()));
            viewHolder.tv_precio.setPaintFlags(viewHolder.tv_precio.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tv_oferta.setText("S/. "+ String.valueOf(items.get(i).getOffer_price()));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_nombreProducto) TextView tv_nombre;
        //@BindView(R.id.txt_marcaProducto) TextView tv_marca;
        @BindView(R.id.txt_precioProducto) TextView tv_precio;
        @BindView(R.id.txt_precioOfertaProducto) TextView tv_oferta;
        @BindView(R.id.img_productos) ImageView img_produto;
        @BindView(R.id.layout_recientes) LinearLayout Recientes;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(Scontext, ProductoActivity.class);
                        intent.putExtra("id", items.get(pos).getId());
                        intent.putExtra("nombre", items.get(pos).getTitle());
                        intent.putExtra("precio", items.get(pos).getSale_price());
                        intent.putExtra("oferta", items.get(pos).getOffer_price());
                        if (items.get(pos).getImage()==null){
                            intent.putExtra("img", "http://www.tiendasistemas.com/wp-content/uploads/2019/06/XNV081MER032.png");
                        }else {
                            intent.putExtra("img", items.get(pos).getImage().getPath());
                        }
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Scontext.startActivity(intent);
                    }
                }
            });
        }


    }
}
