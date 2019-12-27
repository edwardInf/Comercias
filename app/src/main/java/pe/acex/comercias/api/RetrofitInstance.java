package pe.acex.comercias.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://comercias.pe";


    public static Retrofit getComerciasApi() {



       /* OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor()           {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", " Bearer " + Conexion.token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();*/

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }




}
