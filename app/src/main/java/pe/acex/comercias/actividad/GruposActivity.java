package pe.acex.comercias.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.GruposActivityAdapter;

public class GruposActivity extends AppCompatActivity {
    @BindView(R.id.recycler_gruposActivity) RecyclerView recycler;
    GruposActivityAdapter adapter;
    @BindView(R.id.toolbar_grupos) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Launcher.esMiCuenta = false;
        iniciarGrupos();
    }

    private void iniciarGrupos(){
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GruposActivityAdapter(this, Launcher.dataList);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
