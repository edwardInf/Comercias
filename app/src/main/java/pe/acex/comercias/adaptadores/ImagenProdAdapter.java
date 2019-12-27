package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pe.acex.comercias.R;
import pe.acex.comercias.modelos.Product;
import pe.acex.comercias.modelos.Productos;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagenProdAdapter extends SliderViewAdapter<ImagenProdAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> imagen;


    public ImagenProdAdapter(Context context, List<String> imagen) {
        this.context = context;
        this.imagen = imagen;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_slider_prod, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        Glide.with(viewHolder.itemView)
                .load(imagen.get(position))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return imagen.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.img_sliderProductos);
            this.itemView = itemView;
        }
    }
}