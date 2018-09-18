package com.jdswarehouse.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dikhong on 02-07-2018.
 */

public class RestClient {
    public static String BASE_URL = "http://jdskassa.nl/kassa/warehouse_api/";
    private static Retrofit retrofit = null;
    private static final String TAG = "RestClient";
    private static JDSWarehouseService REST_CLIENT = null;
    public static JDSWarehouseService get() {
        REST_CLIENT= getClient().create(JDSWarehouseService.class);
        return REST_CLIENT;
    }
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES).connectTimeout(5,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
