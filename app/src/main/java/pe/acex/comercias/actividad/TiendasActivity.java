package pe.acex.comercias.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.acex.comercias.R;

public class TiendasActivity extends AppCompatActivity {
    @BindView(R.id.webview_tiendasComercias) WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendas);
        ButterKnife.bind(this);

        //Window window = this.getWindow();

        /*window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tienda);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        WebSettings wsConfiguracion = mWebView.getSettings();
        wsConfiguracion.setJavaScriptEnabled(true);
        wsConfiguracion.setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(getIntent().getExtras().getString("url"));
    }
/*
    private void webview(String URL){
        WebSettings wsConfiguracion = mWebView.getSettings();
        wsConfiguracion.setJavaScriptEnabled(true);
        wsConfiguracion.setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(URL);

    }
*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
