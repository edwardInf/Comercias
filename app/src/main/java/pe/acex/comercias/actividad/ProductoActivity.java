package pe.acex.comercias.actividad;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.github.chuross.library.ExpandableLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.smarteist.autoimageslider.SliderView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.acex.comercias.R;
import pe.acex.comercias.adaptadores.ImagenProdAdapter;
import pe.acex.comercias.api.Conexion;
import pe.acex.comercias.api.RetrofitInstance;
import pe.acex.comercias.api.data.ProductosServ;
import pe.acex.comercias.api.interfaz.ProductoInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoActivity extends AppCompatActivity {
    @BindView(R.id.txt_nombreProductoDetalle) TextView nombreProducto;
    @BindView(R.id.txt_disponibleProductoDetalle) TextView disponibleProducto;
    @BindView(R.id.txt_precioProductoDetalle) TextView precioProducto;
    @BindView(R.id.txt_ofertaProductoDetalle) TextView ofertaProducto;
    @BindView(R.id.txt_seleccionCantidad) TextView txtCantidad;

    @BindView(R.id.rating_calificacion) RatingBar ratingBar;
    @BindView(R.id.txt_marcaProductoDetalle) TextView marcaProducto;
    @BindView(R.id.txt_estadoProductoDetalle) TextView estadoProducto;
    @BindView(R.id.txt_descripcionDetalleProducto) HtmlTextView descripcionProducto;
    @BindView(R.id.txt_especificacionDetalleProducto) HtmlTextView especificacionProducto;
    @BindView(R.id.flipper_imagenesProducto) SliderView slider;
    @BindView(R.id.app_bar_producto) AppBarLayout appBar;

    @BindView(R.id.layout_especificacionProducto) LinearLayout layoutEspecificacion;
    @BindView(R.id.expandable_EspecificacionProduto) ExpandableLayout expandableEspecificacion;
    @BindView(R.id.expandable_DescripcionProduto) ExpandableLayout expandableDescripcion;


    int cant = 1;
    float precio;
    private boolean oferta;
    private int cantidad;
    private int mincantidad =1;
    int num_sliders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        ButterKnife.bind(this);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.negro));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_producto);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtCantidad.setText("1");

        ConexionDetalleProducto(getIntent().getExtras().getInt("id"));
        cargarDetalles();
    }

    private void cargarDetalles(){
       // Glide.with(this).load(getIntent().getExtras().getString("img")).thumbnail(0.1f).into(imgProducto);
        nombreProducto.setText(getIntent().getExtras().getString("nombre"));
        disponibleProducto.setText(R.string.disponibilidad);
    }


    @OnClick({ R.id.btn_mas, R.id.btn_menos, R.id.layout_especificacionProducto, R.id.layout_descripcionProducto,
    R.id.btn_comprar})
    public void onBotonClick(View view) {
        Intent intent = new Intent(this, ConfirmarPedido.class);
        switch(view.getId()) {
            case R.id.btn_mas:
                if (cant < cantidad){
                    cant = cant + 1;
                    mostrarCantidad(cant); }
                break;
            case R.id.btn_menos:
                if (cant > mincantidad){
                    cant = cant - 1;
                    mostrarCantidad(cant); }
                break;
            case R.id.layout_especificacionProducto:
                expandableEspecificacion.toggle();
                break;
            case R.id.layout_descripcionProducto:
                expandableDescripcion.toggle();
                break;
            case R.id.btn_comprar:
                intent.putExtra("nombre", getIntent().getExtras().getString("nombre"));
                intent.putExtra("precio", precio);

                /*if (oferta){
                    intent.putExtra("precio", precioProducto.getText().toString());
                }else {
                    intent.putExtra("precio", ofertaProducto.getText().toString());
                }*/
                startActivity(intent);
                break;
        }
    }

    private void mostrarCantidad(int numero) {
        txtCantidad.setText(String.valueOf(numero));
        if (oferta){
            precioProducto.setText("S/. "+ String.format("%.2f", getIntent().getExtras().getFloat("precio")*numero));
            precio = getIntent().getExtras().getFloat("oferta")*numero;
            ofertaProducto.setText("S/. "+ String.format("%.2f", precio));
            //ofertaProducto.setText("S/. "+ String.format("%.2f", getIntent().getExtras().getFloat("oferta")*numero));
        }else {
            precio = getIntent().getExtras().getFloat("precio")*numero;
            ofertaProducto.setText("S/. "+ String.format("%.2f", precio));
            //ofertaProducto.setText("S/. "+ String.format("%.2f", getIntent().getExtras().getFloat("precio")*numero));

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    List<String> namesList;

    private void ConexionDetalleProducto(int id){
        ProductoInterface apiService = RetrofitInstance.getComerciasApi().create(ProductoInterface.class);
        Call<ProductosServ> call = apiService.getProductos(id);
        call.enqueue(new Callback<ProductosServ>() {
            @Override
            public void onResponse(Call<ProductosServ> call, Response<ProductosServ> response) {
                if(response.isSuccessful()) {
                    ProductosServ jsonResponse = response.body();
                    num_sliders =jsonResponse.getData().getImagenes().size();

                    namesList = new ArrayList<String>();
                    for (int i =0; i<num_sliders; i++){
                        namesList.add(jsonResponse.getData().getImagenes().get(i).getPath());
                    }

                    ImagenProdAdapter adapter = new ImagenProdAdapter(ProductoActivity.this, namesList);
                    slider.setSliderAdapter(adapter);

                    cantidad= jsonResponse.getData().getCantidad();
                    mincantidad= jsonResponse.getData().getMinimaCantidad();

                    marcaProducto.setText(jsonResponse.getData().getMarca());
                    ratingBar.setRating(jsonResponse.getData().getCalificacion());
                    estadoProducto.setText(jsonResponse.getData().getCondicion());
                    disponibleProducto.setText("Disponible: " + String.valueOf(jsonResponse.getData().getCantidad()));
                    if (jsonResponse.getData().getCondicion().equals("New")){
                        estadoProducto.setTextColor(ContextCompat.getColor(ProductoActivity.this, R.color.verde1));
                    }
                    habilitarCampos();
                    descripcionProducto.setHtml(jsonResponse.getData().getProductoFinal().getDescripcion(),
                            new HtmlResImageGetter(descripcionProducto));
                    if (jsonResponse.getData().getEspecificacion()!= null){
                        especificacionProducto.setHtml(jsonResponse.getData().getEspecificacion(),
                                new HtmlResImageGetter(especificacionProducto));
                        layoutEspecificacion.setVisibility(View.VISIBLE);
                    }

                    if (jsonResponse.getData().isTieneOferta()){
                        precioProducto.setText("S/. " + String.valueOf(getIntent().getExtras().getFloat("precio")));
                        precioProducto.setPaintFlags(precioProducto.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        ofertaProducto.setText("S/. " + String.valueOf(getIntent().getExtras().getFloat("oferta")));
                        oferta =true;
                        precio =getIntent().getExtras().getFloat("oferta");
                    }else {
                        oferta =false;
                        ofertaProducto.setText("S/. " + String.valueOf(getIntent().getExtras().getFloat("precio")));
                        precioProducto.setVisibility(View.GONE);
                        precio =getIntent().getExtras().getFloat("precio");

                    }

                }

        }
            @Override
            public void onFailure(Call<ProductosServ> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(ProductoActivity.this, "REPORTAR ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();            }
        });
    }


    @BindView(R.id.layout_marca) LinearLayout lin;
    private void habilitarCampos(){
        lin.setVisibility(View.VISIBLE);
        estadoProducto.setVisibility(View.VISIBLE);
        disponibleProducto.setVisibility(View.VISIBLE);
        appBar.setVisibility(View.VISIBLE);
    }


}
