package com.knick.cashfreeexample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APi {
    @FormUrlEncoded
    @POST("cashfree_token_api.php")
    Call<Res> getToken(@Field("orderAmount") String orderAmount, @Field("orderId") String orderId);
}
