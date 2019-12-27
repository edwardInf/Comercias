package pe.acex.comercias.fragmentos;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.actividad.GruposActivity;
import pe.acex.comercias.MainActivity;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.RecientesAdapter;
import pe.acex.comercias.adaptadores.SliderAdapter;
import pe.acex.comercias.adaptadores.TendenciasAdapter;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.widgets.GridSpacingItemDecoration;

public class InicioFragment extends Fragment{

    View view;
    Unbinder unbinder;
    private ViewPager slider;
    private TabLayout indicador;
    @BindView(R.id.btn_categorias) AppCompatImageButton BTNCategorias;
    @BindView(R.id.recycler_tendencias) RecyclerView recyclerTendencias;
    @BindView(R.id.recycler_recientes) RecyclerView recyclerRecientes;
    @BindView(R.id.refresh_inicioFragment) SwipeRefreshLayout refreshLayout;
    private TendenciasAdapter adapterTendencias;
    private RecientesAdapter adapterRecientes;
    @BindView(R.id.constraint_sliders) ConstraintLayout layoutSliders;
    @BindView(R.id.txt_noHaySliders) TextView noHayDatosSliders;
    @BindView(R.id.txt_productosNuevos) TextView noHayNuevos;
    @BindView(R.id.txt_productosTendencia) TextView NoHayTendencia;


    public InicioFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_inicio,null);
        unbinder = ButterKnife.bind(this, view);

        if (Launcher.sliders){
            slider = (ViewPager)view.findViewById(R.id.viewPager_slider);
            indicador = (TabLayout) view.findViewById(R.id.indicator_slider);
            slider.setAdapter(new SliderAdapter(MainActivity.activity, Conexion.sliders));
            indicador.setupWithViewPager(slider, true);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0 , 6000);

        }else {
            layoutSliders.setVisibility(View.GONE);
            noHayDatosSliders.setVisibility(View.VISIBLE);
        }



        if (Launcher.tendencias){
            cargarTendencias();
        }else {
            NoHayTendencia.setVisibility(View.GONE);
        }

        if (Launcher.recientes){
            cargarRecientes();
        }else {
            noHayNuevos.setVisibility(View.GONE);
        }


        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        return view;

    }



    @OnClick({ R.id.btn_categorias, R.id.btn_envios})
    public void onBotonClick(View view) {
        Intent i = new Intent(getContext(), GruposActivity.class);
        switch(view.getId()) {
            case R.id.btn_categorias:
                startActivity(i);
                break;
            case R.id.btn_envios:

                break;
        }
    }


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            MainActivity.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (slider.getCurrentItem() < Conexion.sliders.size() - 1) {
                        slider.setCurrentItem(slider.getCurrentItem() + 1);
                    } else {
                        slider.setCurrentItem(0);
                    }
                }
            });
        }
    };

    private void cargarTendencias(){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTendencias.setHasFixedSize(true);
        recyclerTendencias.setLayoutManager(layoutManager);
        adapterTendencias = new TendenciasAdapter(getContext(), Launcher.dataListTendencias);
        recyclerTendencias.setAdapter(adapterTendencias);
    }

    private void cargarRecientes(){
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerRecientes.setLayoutManager(layoutManager);
        recyclerRecientes.setHasFixedSize(true);
        recyclerRecientes.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(7), true));
        adapterRecientes = new RecientesAdapter(getActivity(), Launcher.dataListRecientes);
        recyclerRecientes.setAdapter(adapterRecientes);
    }


    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (slider.getCurrentItem() < Conexion.sliders.size() - 1) {
                        slider.setCurrentItem(slider.getCurrentItem() + 1);
                    } else {
                        slider.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
