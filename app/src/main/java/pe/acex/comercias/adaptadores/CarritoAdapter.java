package pe.acex.comercias.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.acex.comercias.R;
import pe.acex.comercias.api.Eliminar;
import pe.acex.comercias.modelos.Carrito;
import pe.acex.comercias.modelos.Items;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder>{

    private ArrayList<Items> items;
    private Context Scontext;
    View view;
    private int cant;

    public CarritoAdapter(Context context, ArrayList<Items> apiObjects){
        this.Scontext = context;
        this.items =  apiObjects;
    }

    @NonNull
    @Override
    public CarritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_carrito, viewGroup, false);

        return new CarritoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_nombre.setText(items.get(i).getTituloCarrito());
        viewHolder.tv_cantidad.setText("Cantidad: " + String.valueOf(items.get(i).getPivot().getCantidad()));
        viewHolder.tv_cantidad2.setText(String.valueOf(items.get(i).getPivot().getCantidad()));
        viewHolder.tv_precio.setText("S/.: " + String.valueOf(items.get(i).getPivot().getPrecioUnit()*items.get(i).getPivot().getCantidad()));
        cant = items.get(i).getPivot().getCantidad();

        viewHolder.mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cant < items.get(i).getCantidadMax()){
                    cant = cant + 1;
                    //mostrarCantidad(cant,viewHolder,i);
                    viewHolder.tv_cantidad.setText("Cantidad: " + String.valueOf(cant));
                    viewHolder.tv_cantidad2.setText(String.valueOf(cant));
                    viewHolder.tv_precio.setText("S/.: " + String.valueOf(items.get(i).getPivot().getPrecioUnit()*cant));

                }
            }
        });
        viewHolder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cant > items.get(i).getCantidadMin()){
                    cant = cant - 1;
                    viewHolder.tv_cantidad.setText("Cantidad: " + String.valueOf(cant));
                    viewHolder.tv_cantidad2.setText(String.valueOf(cant));
                    viewHolder.tv_precio.setText("S/.: " + String.valueOf(items.get(i).getPivot().getPrecioUnit()*cant)); }
            }
        });

    }

    private void mostrarCantidad(int cantidad,ViewHolder viewHolder,int i) {
        viewHolder.tv_cantidad.setText("Cantidad: " + String.valueOf(cantidad));
        viewHolder.tv_cantidad2.setText(String.valueOf(cantidad));
        viewHolder.tv_precio.setText("S/.: " + String.valueOf(items.get(i).getPivot().getPrecioUnit()*cantidad));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_tituloProdCarrito) TextView tv_nombre;
        @BindView(R.id.txt_precioProdCarrito) TextView tv_precio;
        @BindView(R.id.txt_cantidadProdCarrito) TextView tv_cantidad;
        @BindView(R.id.txt_seleccionCantidadCarrito) TextView tv_cantidad2;
        @BindView(R.id.img_productoCarrito) ImageView img_produto;
        public LinearLayout layout_carrito;
        public RelativeLayout viewBackground;

        @BindView(R.id.btn_masC) Button mas;
        @BindView(R.id.btn_menosC) Button menos;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            layout_carrito = itemView.findViewById(R.id.card_carritoPedido);
            viewBackground = itemView.findViewById(R.id.view_background);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                    }
                }
            });
        }
    }


    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        Eliminar.EliminarCarrito();
    }

    public void restoreItem(Items item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }
}
