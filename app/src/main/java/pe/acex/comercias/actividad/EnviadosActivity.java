package pe.acex.comercias.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import pe.acex.comercias.R;

public class EnviadosActivity extends AppCompatActivity {
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviados);
        unbinder = ButterKnife.bind(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_enviados);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
