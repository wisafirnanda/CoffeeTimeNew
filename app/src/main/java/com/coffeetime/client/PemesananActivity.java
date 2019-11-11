package com.coffeetime.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.API.BaseApiService;
import com.coffeetime.API.UtilsApi;
import com.coffeetime.R;
import com.coffeetime.model.Kopi;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.PesananClient;
import com.coffeetime.model.User;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Bundle b;

//    private RecyclerView recyclerView;
//    private PesananClientAdapter adapter;
    private ArrayList<PesananClient> pesananClientArrayList;

    private TextView namawarkop, alamatwarkop, waktubuka, txquantity, harga_kopi, harga_pesanan, namakopi,
            jeniskopi,harga_antar,kode_unik,harga_total;

    int quantity = 0;

    Endpoints endpoints;
    Kopi kopi;
    Warkop warkop;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;

    List<Integer> totalitem = new ArrayList<>();

    private TextView alamat_antarText,noRek;
    String stxtaddresplace;

    // konstanta untuk mendeteksi hasil balikan dari place picker
    private int Place_Picker_Request = 1;
    private Place place_Picker;
    private static int MY_PERMISSION_FINE_LOCATION = 1;
    LatLng latLng ;
    float latitude;
    float longitude;
    private Context context;

    Button btn_order;

    Context mContext;
    BaseApiService mApiService;

    Spinner spPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        //warkop
        namawarkop = findViewById(R.id.txWarkopPsnBiji);
        alamatwarkop = findViewById(R.id.txAlmtWrkpBiji);
        waktubuka = findViewById(R.id.txWktuBukaBiji);

        //kopi
        namakopi = findViewById(R.id.txNamaPesan_kopi);
        jeniskopi = findViewById(R.id.txJenisPesan_kopi);

        txquantity = findViewById(R.id.tx_quantityPesan);
        harga_kopi = findViewById(R.id.txHrgPesan);

        harga_pesanan = findViewById(R.id.harga_pesanan);
        harga_antar = findViewById(R.id.harga_antar);
        kode_unik = findViewById(R.id.kode_unik);
        harga_total = findViewById(R.id.harga_total);

        noRek = findViewById(R.id.text6);

        kopi = (Kopi) getIntent().getSerializableExtra("kopi");

        Intent iin= getIntent();
        b = iin.getExtras();

        if(b!=null)
        {

            String idUser = (String) b.get("idUser");
            String idKopi = (String) b.get("idKopi");
            String alamat = (String) b.get("alamat");

            String namaKopi =(String) b.get("namaKopi");
            namakopi.setText(namaKopi);

            String jenisKopi =(String) b.get("jenisKopi");
            jeniskopi.setText(jenisKopi);

            String qtyKopi =(String) b.get("qtyKopi");
            txquantity.setText(qtyKopi);

            String hrgaKopi =(String) b.get("hrgaKopi");
            harga_kopi.setText(hrgaKopi);

            String hrga_total =(String) b.get("hrga_total");
            harga_pesanan.setText(hrga_total);
        }

        int harga = Integer.parseInt(harga_pesanan.getText().toString());

        int kode = 121;
        int biaya =10000;
        int total = harga+kode;

        kode_unik.setText(String.valueOf(kode));

        harga_total.setText(String.valueOf(total));

        btn_order = findViewById(R.id.btn_order);


        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order();

            }
        });

        //        INIT SPIN
        spPembayaran = findViewById(R.id.idSpinner);
        String[] mPembayaran={"BANK MANDIRI","COD"};

        spPembayaran=(Spinner)findViewById(R.id.idSpinner);
        spPembayaran.setOnItemSelectedListener(this);
        ArrayAdapter adapterspinner=new ArrayAdapter(this,android.R.layout.simple_spinner_item,mPembayaran);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPembayaran.setAdapter(adapterspinner);

    }

    private void order(){

        mApiService.orderRequest(
                namakopi.getText().toString(),
                jeniskopi.getText().toString(),
                txquantity.getText().toString(),
                harga_total.getText().toString(),
                spPembayaran.getSelectedItem().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "BERHASIL TERSIMPAN !", Toast.LENGTH_SHORT).show();

                            Intent order = new Intent(getApplicationContext(), OrderanUser.class);
                            startActivity(order);
                            finish();

                        } else {
                            Log.i("debug", "onResponse: TERJADI KESALAHAN!");

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
