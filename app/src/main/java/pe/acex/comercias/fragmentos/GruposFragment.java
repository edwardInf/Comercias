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

import java.util.ArrayList;

import butterknife.ButterKnife;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.SubGruposAdapter;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.modelos.SubGrupos;


public class GruposFragment extends Fragment {
    public static RecyclerView recyclerView;
    private View view;
    public static SubGruposAdapter subAdapter;
    public static Activity activity;


    public GruposFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_grupos,null);

        ButterKnife.bind(this, view);
        recyclerView = view.findViewById(R.id.recycler_grupos);
        iniciar(getArguments().getInt("id"));
        return view;
    }


    private void iniciar(int i) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        Conexion.ConexionSubGrupos(i,true);
    }


}
