package com.coffeetime.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.adapter.PesananClientAdapter;
import com.coffeetime.model.Kopi;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.PesananClient;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private PesananClientAdapter adapter;
    private ArrayList<PesananClient> pesananClientArrayList;

    private TextView alamat_antarText, hargaPesanan, hargaAntar, unik, hargaTotal;
    String stxtaddresplace;

    // konstanta untuk mendeteksi hasil balikan dari place picker
    private int Place_Picker_Request = 1;
    private Place place_Picker;
    private static int MY_PERMISSION_FINE_LOCATION = 1;
    LatLng latLng ;
    float latitude;
    float longitude;
    private Context context;

    Bundle bundle;

    Kopi kopi;
    Warkop warkop;

    Button BtnBayar;

    Endpoints endpoints;

    Spinner spPembayaran;
    RelativeLayout rek_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        recyclerView = findViewById(R.id.recyclerview);
        hargaPesanan = findViewById(R.id.harga_pesanan);
        hargaAntar = findViewById(R.id.harga_antar);
        unik = findViewById(R.id.kode_unik);
        hargaTotal = findViewById(R.id.harga_totall);

        final String total = (String) getIntent().getSerializableExtra("total");

        hargaPesanan.setText(total);

        int antar = 10000;
        int unik = 425;
        int harga = Integer.parseInt(hargaPesanan.getText().toString());

//        hargaTotal.setText(total+antar+kode);

        int tot = harga+antar+unik;

        hargaTotal.setText(String.valueOf(tot));

        addData();

        /*adapter = new PesananClientAdapter(pesananClientArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PesanActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/

        alamat_antarText = findViewById(R.id.alamat_antar);

        bundle = getIntent().getExtras();

        BtnBayar = findViewById(R.id.btn_bayar);

        rek_layout = findViewById(R.id.rek_layout);

        spPembayaran = findViewById(R.id.paySpinner);
        String[] mPembayaran={"COD","TRANSFER BANK"};

        spPembayaran.setOnItemSelectedListener(this);
        ArrayAdapter adapterspinner=new ArrayAdapter(this,android.R.layout.simple_spinner_item,mPembayaran);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPembayaran.setAdapter(adapterspinner);

        BtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle!=null)
                {
                    if(latitude == 0 && longitude == 0)
                    {
                        Toast.makeText(PesanActivity.this, "Lokasi antar belum dipilih", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Pemesanan pemesanan = new Pemesanan();
                        pemesanan.setIdUser(bundle.getString("id_user"));
                        pemesanan.setIdKopi(bundle.getString("id_warkop"));
                        pemesanan.setTotalHarga(hargaTotal.getText().toString().trim());
                        pemesanan.setAlamat(alamat_antarText.getText().toString().trim());
                        pemesanan.setAlamatAntarLatitude(String.valueOf(latitude));
                        pemesanan.setAlamatAntarLongitude(String.valueOf(longitude));
                        pemesanan.setTipe_bayar(spPembayaran.getSelectedItem().toString());
                        pemesanan.setToken(bundle.getString("token"));

                        endpoints = Connection.getEndpoints(PesanActivity.this);
                        endpoints.addPemesanan(pemesanan).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getString("status").equals("sukses")) {
                                        Toast.makeText(PesanActivity.this, "Pemesanan berhasil dikirim, tunggu konfirmasi pembayaran!", Toast.LENGTH_LONG).show();

//                                        JSONObject json = jsonObject.getJSONObject("data");

                                        Intent main = new Intent(PesanActivity.this, MainClientActivity.class);
                                        main.putExtra("set_history", true);

                                        startActivity(main);
                                        finish();

                                    }
                                    else if(jsonObject.getString("status").equals("gagal"))
                                    {
                                        Toast.makeText(PesanActivity.this, "Pemesanan gagal, coba lagi!", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(PesanActivity.this, "Terjadi kesalahan, coba lagi!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    void addData(){
        pesananClientArrayList = new ArrayList<>();
        pesananClientArrayList.add(new PesananClient("Wisa", "Sanger", "Kopi jadi", "x 2", "10.000"));
        pesananClientArrayList.add(new PesananClient("Nanda", "Arabica Gayo", "Bubuk", "x 1", "30.000"));
        pesananClientArrayList.add(new PesananClient("Wisa", "Sanger", "Kopi jadi", "x 2", "10.000"));
        pesananClientArrayList.add(new PesananClient("Nanda", "Arabica Gayo", "Bubuk", "x 1", "30.000"));

    }

    public void pickAlamatAntar(View view) {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            startActivityForResult(intentBuilder.build(PesanActivity.this), 5);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(PesanActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), PesanActivity.this, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("masuk onresult", "onresult");
        Log.i("result   ", "request code "+requestCode+","+"result"+resultCode+"data"+data);
        super.onActivityResult(requestCode,resultCode,data);

        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == 5 && resultCode == RESULT_OK) {

            place_Picker = PlacePicker.getPlace(PesanActivity.this,data);

            stxtaddresplace = String.format("%s", place_Picker.getAddress().toString());
            latLng = place_Picker.getLatLng();
            latitude = (float) latLng.latitude;
            longitude = (float) latLng.longitude;
            alamat_antarText.setText(""+ stxtaddresplace);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getSelectedItem().toString())
        {
            case "COD":
                rek_layout.setVisibility(View.GONE);
                break;
            case "TRANSFER BANK":
                rek_layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


