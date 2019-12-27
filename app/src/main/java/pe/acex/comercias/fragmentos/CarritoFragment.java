package pe.acex.comercias.fragmentos;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.CarritoAdapter;
import pe.acex.comercias.api.Eliminar;
import pe.acex.comercias.modelos.Items;
import pe.acex.comercias.widgets.RecyclerItemTouchHelper;

public class CarritoFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    @BindView(R.id.recycler_carrito) RecyclerView recyclerCarrito;
    @BindView(R.id.txt_noHayCarrito) TextView txtNoCarrito;

    private CarritoAdapter adapterCarrito;
    Unbinder unbinder;

    public CarritoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_carrito,null);
        unbinder = ButterKnife.bind(this, view);

        Toolbar mToolbar = view.findViewById(R.id.toolbar_carritoFragment);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);


        if (MainActivity.carrito){
            iniciar();
        }else {
            recyclerCarrito.setVisibility(View.GONE);
            txtNoCarrito.setVisibility(View.VISIBLE);
        }


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,
                ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerCarrito);
        return view;
    }

    private void iniciar() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerCarrito.setHasFixedSize(true);
        recyclerCarrito.setLayoutManager(layoutManager);
        adapterCarrito = new CarritoAdapter(getContext(), Launcher.dataListItems);
        recyclerCarrito.setAdapter(adapterCarrito);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        final Items deletedItem = Launcher.dataListItems.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        //Eliminar.EliminarCarrito();

        //Toast.makeText(getContext(), String.valueOf(Launcher.dataListItems.get(position).getId()),Toast.LENGTH_SHORT).show();
        adapterCarrito.removeItem(viewHolder.getAdapterPosition());

    }
}
