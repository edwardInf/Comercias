package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import pe.acex.comercias.R;
import pe.acex.comercias.modelos.Sliders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Sliders> imagen;


    public SliderAdapter(Context context, ArrayList<Sliders> imagen) {
        this.context = context;
        this.imagen = imagen;
    }

    @Override
    public int getCount() {
        return imagen.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@Nullable ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_slider, null);
        ImageView imag = (ImageView) view.findViewById(R.id.img_slider);

        Glide.with(view.getContext())
                .load(imagen.get(position).getImage().getPath())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .into(imag);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}