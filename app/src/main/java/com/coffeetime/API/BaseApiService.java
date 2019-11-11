package com.coffeetime.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("pemesanan.php")
    Call<ResponseBody> orderRequest(@Field("nama_kopi") String nama_kopi,
                                    @Field("jenis_kopi") String jenis_kopi,
                                    @Field("qty") String qty,
                                    @Field("total_harga") String total_harga,
                                    @Field("tipe_bayar") String tipe_bayar);

    @GET("orderan.php")
    Call<OrderanList> getOrderanData(@Query("id_order") int id_order);

}