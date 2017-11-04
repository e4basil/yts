package com.example.basi.test.services;

import android.content.Context;
import android.util.Log;

import com.example.basi.test.BuildConfig;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieService {

    private static Retrofit retrofit = null;

    private static OkHttpClient buildClient(Context context) {
        return new OkHttpClient
                .Builder()
                .addInterceptor(
                        new LoggingInterceptor.Builder()
                                .loggable(BuildConfig.DEBUG)
                                .setLevel(Level.BASIC)
                                .log(Log.INFO)
                                .request("Request")
                                .response("Response")
                                .addHeader("version", BuildConfig.VERSION_NAME)
                                .build()
                )

                .build();
    }

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(buildClient(context))
                    .baseUrl("https://yts.ag/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
