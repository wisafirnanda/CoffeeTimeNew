package com.coffeetime.networkmanager;

import java.util.List;

import com.coffeetime.model.Komentar;
import com.coffeetime.model.Kopi;
import com.coffeetime.model.Pembayaran;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.Status;
import com.coffeetime.model.User;
import com.coffeetime.model.Warkop;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Endpoints {

    //user
    @POST("user.php?aksi=register")
    Call<Status> aadUser(@Body User user);

    @POST("user.php?aksi=login")
    Call<User> login (@Body User user);

    @GET("user.php?aksi=tampiluser")
    Call<User> getUser();

    //warkop
    @POST("warkop.php?aksi=inputwarkop")
    Call<ResponseBody> aadWarkop(@Body Warkop warkop);

    @POST("warkop.php?aksi=tampilwarkop")
    Call<Warkop> getWarkop(@Body Warkop warkop);

    @POST("warkop.php?aksi=tampilwarkopid")
    Call<Warkop> getWarkopId(@Body Warkop warkop);

    @POST("warkop.php?aksi=setfavorit")
    Call<Status> setFavorit(@Body Warkop warkop);

    @GET("warkop.php?aksi=tampilwarkops")
    Call<List<Warkop>> getWarkops();

    @GET("warkop.php?aksi=tampilfavorit")
    Call<List<Warkop>> getWarkopFavorite();

    @GET("warkop.php?aksi=tampillaris")
    Call<List<Warkop>> getWarkopLaris();

    @POST("warkop.php?aksi=komen")
    Call<Status> sendComment(@Body Komentar komentar);

    @POST("warkop.php?aksi=getKomen")
    Call<List<Komentar>> getComment(@Body Komentar komentar);

    //kopi
    @POST("kopi.php?aksi=inputkopi")
    Call<ResponseBody> aadKopi(@Body Kopi kopi);

    @POST("kopi.php?aksi=tampilkopi")
    Call<List<Kopi>> getKopi(@Body Kopi kopi);

    @GET("kopi.php?aksi=tampilkopis")
    Call<List<Kopi>> getKopis();

    @GET("kopi.php?aksi=tampilkopijadi")
    Call<List<Kopi>> getKopiJadi();

    @GET("kopi.php?aksi=tampilkopibubuk")
    Call<List<Kopi>> getKopiBubuk();

    @GET("kopi.php?aksi=tampilkopibiji")
    Call<List<Kopi>> getKopiBiji();

    //pemesanan
    @POST("pemesanan.php?aksi=inputpemesanan")
    Call<ResponseBody> addPemesanan(@Body Pemesanan pemesanan);

    @POST("pemesanan.php?aksi=tampilpemesanan")
    Call<List<Pemesanan>> getPemesanan(@Body Pemesanan pemesanan);

    @POST("pemesanan.php?aksi=tampilpemesananjual")
    Call<List<Pemesanan>> getPemesananJual(@Body Pemesanan pemesanan);

    @POST("pemesanan.php?aksi=tampilpemesanandetail")
    Call<Pemesanan> getPemesananDetail(@Body Pemesanan pemesanan);

    @POST("pemesanan.php?aksi=konfirmasipemesanan")
    Call<ResponseBody> konfirmasiPemesanan(@Body Pemesanan pemesanan);

    @POST("pemesanan.php?aksi=konfirmasipengiriman")
    Call<ResponseBody> konfirmasiPengiriman(@Body Pemesanan pemesanan);

    @GET("pemesanan.php?aksi=tampilpemesananadmin")
    Call<List<Pemesanan>> getPemesananAdmin();

    //pembayaran
    @POST("pembayaran.php?aksi=inputpembayaran")
    Call<ResponseBody> addPembayaran(@Body Pembayaran pembayaran);

    @GET("pembayaran.php?aksi=tampilpembayaran")
    Call<Pembayaran> getPembayaran();
}
