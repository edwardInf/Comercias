package pe.acex.comercias.fragmentos;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.SubGruposAdapter;
import pe.acex.comercias.api.Conexion;


public class SubGruposFragment extends Fragment {
    public static RecyclerView recyclerView;
    public static SubGruposAdapter subAdapter;
    public static Activity activity;


    public SubGruposFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subgrupos, container, false);
        ButterKnife.bind(this,view);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_subGruposActivity);
        TextView subGrupo = (TextView) view.findViewById(R.id.txt_subgGuposActivity);

        subGrupo.setText(getArguments().getString("nombre"));
        iniciarSubGrupos(getArguments().getInt("id"));
        return view;
    }

    private void iniciarSubGrupos(int i) {

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        Conexion.ConexionSubGrupos(i,false);

    }


}
