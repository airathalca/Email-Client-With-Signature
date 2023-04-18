package com.fsck.k9.backend;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ServiceCipher {
    @POST("encrypt")
    Call<ResponseCipher> encrypt(@Body RequestBody body);

    @POST("decrypt")
    Call<ResponseCipher> decrypt(@Body RequestBody body);
}

