package com.fsck.k9.backend;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ServiceCipher {
    @POST("encrypt")
    Call<ResponseCipher> encrypt(@Body RequestBody body);

    @POST("decrypt")
    @FormUrlEncoded
    Call<ResponseCipher> decrypt(@Field("key") String key,
        @Field("message") String message);
}

