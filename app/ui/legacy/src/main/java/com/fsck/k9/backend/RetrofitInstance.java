package com.fsck.k9.backend;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static ServiceCipher retrofit;
    private static final String BASE_URL = "http://192.168.18.8:5000/";

    public static ServiceCipher getRetrofitInstance() {
        if (retrofit == null) {
            Retrofit retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofit = retrofitInstance.create(ServiceCipher.class);
        }
        return retrofit;
    }
}
