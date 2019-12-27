package pe.acex.comercias.adaptadores;

import android.content.Context;
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
import pe.acex.comercias.R;
import pe.acex.comercias.modelos.SubGrupos;

public class SubGruposAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<SubGrupos> itemGrup;
    private Context Scontext;
    private boolean esCategorias;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;



    public SubGruposAdapter(Context context, ArrayList<SubGrupos> apiObjects, boolean tipo){
        this.Scontext = context;
        this.itemGrup =  apiObjects;
        this.esCategorias = tipo;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (esCategorias) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subgrupos2, viewGroup, false);
            return new SubGruposAdapter.ViewHolder(view);
        } else {
            //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subgrupos, viewGroup, false);
            if (i == TYPE_ITEM) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subgrupos, viewGroup, false);
                return new ViewHolder(itemView);
            } else if (i == TYPE_FOOTER) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_footer, viewGroup, false);
                return new FooterViewHolder(itemView);
            }
            //return new SubGruposAdapter.ViewHolder(view);
        } return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int i) {
        //holder.tv_titulo.setText(itemGrup.get(i).getName());
      /*  Glide.with(holder.itemView.getContext())
                .load("https://cdn0.iconfinder.com/data/icons/kameleon-free-pack-rounded/110/T-Shirt-2-512.png")
                .thumbnail(0.1f)
                .into(holder.tv_imagen);*/



        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.footerText.setText("Footer View");
        } else if (holder instanceof ViewHolder) {
            ViewHolder itemViewHolder = (ViewHolder) holder;
            itemViewHolder.tv_titulo.setText(itemGrup.get(i).getName());
        }
    }

    @Override
    public int getItemCount() {
        return itemGrup.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == itemGrup.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_subGrupos) TextView tv_titulo;
        @BindView(R.id.img_subGrupos) ImageView tv_imagen;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        FooterViewHolder(View view) {
            super(view);
            footerText = (TextView) view.findViewById(R.id.footer_text);
        }
    }


}
