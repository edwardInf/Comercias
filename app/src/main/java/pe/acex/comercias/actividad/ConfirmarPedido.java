package pe.acex.comercias.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.acex.comercias.Launcher;
import pe.acex.comercias.R;
import pe.acex.comercias.dialogs.TarjetaDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class ConfirmarPedido extends AppCompatActivity {
    @BindView(R.id.txt_aceptarTerminos) TextView aceptarTerminos;
    @BindView(R.id.txt_precioComprarAhora) TextView precioProducto;
    @BindView(R.id.txt_nombreProdComprarAhora) TextView nombrecomprarAhora;
    @BindView(R.id.txt_subTotalPagarAhora) TextView subTotal;
    @BindView(R.id.txt_totalPagarAhora) TextView totalPagar;
    @BindView(R.id.txt_totalPagar) TextView total;
    @BindView(R.id.txt_cuponPagarAhora) TextView txt_cupon;

    FragmentManager fragmentManager;
    TarjetaDialog dialogTarjeta;
    boolean cupon = false;
    float descuentoCupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_confirmarPedido);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        aceptarTerminos();
        fragmentManager = getSupportFragmentManager();

        nombrecomprarAhora.setText(getIntent().getExtras().getString("nombre"));
        precioProducto.setText("S/. " + String.format("%.2f",getIntent().getExtras().getFloat("precio")));

        if (!cupon){descuentoCupon = 0;}
        resumenPedido();


    }
    float f;
    private void aceptarTerminos(){
        SpannableString ss = new SpannableString(this.getResources().getString(R.string.aceptarTerminos));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //startActivity(new Intent(MyActivity.this, NextActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 69, 91, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        aceptarTerminos.setText(ss);
        aceptarTerminos.setMovementMethod(LinkMovementMethod.getInstance());
        aceptarTerminos.setHighlightColor(Color.TRANSPARENT);
    }

    private void resumenPedido(){
        subTotal.setText("S/. " + String.format("%.2f",getIntent().getExtras().getFloat("precio")));
        totalPagar.setText("S/. " + String.format("%.2f",(getIntent().getExtras().getFloat("precio")+ descuentoCupon)));

        total.setText(totalPagar.getText().toString());
    }

    @OnClick({ R.id.layout_configTarjeta, R.id.layout_addDirecc})
    public void onBotonClick(View view) {
        switch(view.getId()) {
            case R.id.layout_configTarjeta:
                dialogTarjeta = new TarjetaDialog(ConfirmarPedido.this);
                dialogTarjeta.show(fragmentManager, "TarjetaDialog");
                break;
            case R.id.layout_addDirecc:
                Launcher.editarDirecc = false;
                Intent intent = new Intent(ConfirmarPedido.this, DireccionActivity.class);
                startActivity(intent);
                break;
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
