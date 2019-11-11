package com.coffeetime.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.adapter.KomentarAdapter;
import com.coffeetime.adapter.MenuClientAdapter;
import com.coffeetime.helper.CommentDialog;
import com.coffeetime.model.Komentar;
import com.coffeetime.model.Kopi;
import com.coffeetime.model.Status;
import com.coffeetime.model.User;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailWarkopActivity extends Activity {

    TextView namawarkop, alamatwarkop, waktubuka, txquantity, harga_kopi, namakopi, jeniskopi;
    private RecyclerView recyclerView, komentarRecycler;
    private MenuClientAdapter adapter;
    private KomentarAdapter komentarAdapter;
    private List<Kopi> kopiArrayList;
    private List<Komentar> komentarList;
    int quantity = 0;

    Endpoints endpoints;
    Kopi kopi;
    Warkop warkop;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;
    //Warkop warkop;
    List<Kopi> totalitem = new ArrayList<>();
    TextView harga_total;

    TextView latlong;
    Button btnArah;
    FloatingActionButton fabLike, fabComment;
//    String uriGeo = "geo:0,0?q=Lapangan Blangpadang - Banda Aceh";

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_warkop);

        progressDialog = new ProgressDialog(this);

        btnArah = findViewById(R.id.idbtnLoc);
        latlong = findViewById(R.id.latlong);

        //warkop
        namawarkop = findViewById(R.id.txnamawarkop);
        alamatwarkop = findViewById(R.id.txalamatwarkop);
        waktubuka = findViewById(R.id.txwaktubuka);

        //kopi
        namakopi = findViewById(R.id.txnama_kopi);
        jeniskopi = findViewById(R.id.txjenis_kopi);

        txquantity = findViewById(R.id.tx_quantity);
        harga_kopi = findViewById(R.id.txharga_kopi);
        harga_total = findViewById(R.id.txharga_total);

        recyclerView = findViewById(R.id.recyclerviewmenuclient);
        komentarRecycler = findViewById(R.id.listKomen);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailWarkopActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(DetailWarkopActivity.this);
        komentarRecycler.setLayoutManager(layoutManager1);

        sharedPreferences = DetailWarkopActivity.this.getSharedPreferences("coffee",0);
        editor = sharedPreferences.edit();
        editor.apply();

        gson = new Gson();
        String json = sharedPreferences.getString("user","");

        user = gson.fromJson(json, new TypeToken<User>(){

        }.getType());

        warkop = (Warkop) getIntent().getSerializableExtra("warkop");

        tampildata();
        getComment();

        btnArah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String uriGeoKu = "geo:0,0?q="+latlong.getText().toString();

                    Uri uri = Uri.parse(uriGeoKu);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW,uri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager())!=null){
                        startActivity(mapIntent);
                    }
            }
        });

        fabLike = findViewById(R.id.fab_like);
        fabLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Warkop warkop_ = new Warkop();
                warkop_.setIdUser(user.getIdUser());
                warkop_.setIdWarkop(warkop.getIdWarkop());

                endpoints = Connection.getEndpoints(DetailWarkopActivity.this);
                endpoints.setFavorit(warkop_).enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {

                        if(response.code() == 200)
                        {
                            Status status = response.body();

                            Toast.makeText(DetailWarkopActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                    }
                });
            }
        });

        fabComment = findViewById(R.id.fab_comment);
        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Komentar komentar = new Komentar();
                komentar.setId_warkop(warkop.getIdWarkop());
                komentar.setId_user(user.getIdUser());

                new CommentDialog(DetailWarkopActivity.this, komentar);
            }
        });
    }

    void tampildata() {
        kopi = new Kopi();
        kopi.setIdWarkop(warkop.getIdWarkop());

        namawarkop.setText(warkop.getNamaWarkop());
        alamatwarkop.setText(warkop.getAlamatWarkop());
        waktubuka.setText(warkop.getWaktuBuka());
        latlong.setText(warkop.getAlamatWarkopLatitude()+","+warkop.getAlamatWarkopLongitude());

        endpoints = Connection.getEndpoints(DetailWarkopActivity.this);
        endpoints.getKopi(kopi).enqueue(new Callback<List<Kopi>>() {
            @Override
            public void onResponse(Call<List<Kopi>> call, Response<List<Kopi>> response) {
                if (response.body() != null) {
                    kopiArrayList = new ArrayList<>(response.body());
                    adapter = new MenuClientAdapter(kopiArrayList);
                    recyclerView.setAdapter(adapter);

                    final Kopi kopi1 = new Kopi();

                    adapter.setOnQuantityChangedListener(new MenuClientAdapter.OnQuantityChangedListener() {
                        /*int totalakhir = 0;
                        List<Integer> totalitem = new ArrayList<>();*/

                        @Override
                        public void onQuantityChanged() {
                            /*int total1=total;
                            totalakhir += total1;*/
//
//                            kopi1.setQty(String.valueOf(qty));
//                            kopi1.setHargaKopi(String.valueOf(harga));
////
//                            if(opt == 1)
//                            {
//                                totalitem.add(kopi1);
//                            }
//                            else
//                            {
//                                totalitem.remove(kopi1);
//                            }

                            Hitung();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Kopi>> call, Throwable t) {

            }
        });
    }

    public void getComment()
    {
        Komentar komentar = new Komentar();
        komentar.setId_warkop(warkop.getIdWarkop());

        endpoints = Connection.getEndpoints(DetailWarkopActivity.this);
        endpoints.getComment(komentar).enqueue(new Callback<List<Komentar>>() {
            @Override
            public void onResponse(Call<List<Komentar>> call, Response<List<Komentar>> response) {
                if(!response.body().isEmpty())
                {
                    komentarList = new ArrayList<>(response.body());
                    komentarAdapter = new KomentarAdapter(komentarList);
                    komentarRecycler.setAdapter(komentarAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Komentar>> call, Throwable t) {
                Toast.makeText(DetailWarkopActivity.this, "Gagal ambil data komentar " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Hitung() {
        int qty = 0;
        int jumlah = 0;
        int totalakhir = 0;

        for (int i = 0; i < kopiArrayList.size(); i++) {
            qty = Integer.parseInt(kopiArrayList.get(i).getQty());
            jumlah = Integer.parseInt(kopiArrayList.get(i).getHargaKopi());

            totalakhir = totalakhir + (jumlah * qty);

            Log.e("JUMLAH","SUB = " + jumlah*qty);
        }

        Log.e("-----","-----");

        //Log.i("totalakhir", ""+totalakhir);

        harga_total.setText(String.valueOf(totalakhir));
    }

    public <T> void setListKopi(String key, List<T> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        sets(key, json);
    }

    public void sets(String key, String value)
    {
        editor.putString(key, value);
        editor.commit();
    }

    public void Pesan(View view) {
        String total =  harga_total.getText().toString();

        Intent intent = new Intent(DetailWarkopActivity.this, PesanActivity.class);
        intent.putExtra("id_user", user.getIdUser());
        intent.putExtra("id_warkop", warkop.getIdWarkop());
        intent.putExtra("token", warkop.getToken());
        intent.putExtra("total", total);

        setListKopi("listkopi", totalitem);

        if(total.equalsIgnoreCase("0"))
        {
            Toast.makeText(DetailWarkopActivity.this, "Anda belum memilih produk!", Toast.LENGTH_LONG).show();
        }
        else
        {
            DetailWarkopActivity.this.startActivity(intent);

            finish();
        }

//        startActivity(new Intent(DetailWarkopActivity.this, PesanActivity.class));
    }

    public void showProgress(String message){
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
