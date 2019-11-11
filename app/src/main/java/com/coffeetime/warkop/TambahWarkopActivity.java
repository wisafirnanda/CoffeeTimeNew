package com.coffeetime.warkop;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.coffeetime.Config;
import com.coffeetime.R;
import com.coffeetime.model.User;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahWarkopActivity extends Activity {
    LayoutInflater inflater;
    EditText nama_warkopText, nama_pemilikText, cp_warkopText, waktu_bukaText,waktu_tutupText;
    TextView alamat_warkopText;
    String stxtaddresplace;
    LatLng latLng ;
    float latitude;
    float longitude;

    Endpoints endpoints;
    Warkop warkop;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;

    private Place place_Picker;
    private static int Place_Picker_Request = 1;

    private ImageButton btnTime,btnTimeOff;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_warkop);

        nama_warkopText = findViewById(R.id.nama_warkop);
        nama_pemilikText = findViewById(R.id.nama_pemilik);
        cp_warkopText = findViewById(R.id.cp_warkop);
        waktu_bukaText = findViewById(R.id.waktu_buka);
        waktu_tutupText = findViewById(R.id.waktu_tutup);
        alamat_warkopText = findViewById(R.id.alamat_warkop);

        sharedPreferences = TambahWarkopActivity.this.getSharedPreferences("coffee", 0);
        editor = sharedPreferences.edit();
        editor.apply();

        gson = new Gson();
        String json = sharedPreferences.getString("user","");

        user = gson.fromJson(json, new TypeToken<User>(){

        }.getType());

        btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(TambahWarkopActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        waktu_bukaText.setText(hourOfDay + ":" + minute);

                    }
                }, 0, 0,false);

                timePickerDialog.show();
            }
        });

        btnTimeOff = findViewById(R.id.btnTimeOff);
        btnTimeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(TambahWarkopActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        waktu_tutupText.setText(hourOfDay + ":" + minute);


                    }
                }, 0, 0,false);

                timePickerDialog.show();
            }
        });

    }

    public void tambahWarkop(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);


        warkop = new Warkop();
        warkop.setNamaWarkop(nama_warkopText.getText().toString());
        warkop.setNamaPemilik(nama_pemilikText.getText().toString());
        warkop.setCpWarkop(cp_warkopText.getText().toString());
        warkop.setWaktuBuka(waktu_bukaText.getText().toString()+" - "+waktu_tutupText.getText().toString());
        warkop.setAlamatWarkop(alamat_warkopText.getText().toString());
        warkop.setAlamatWarkopLatitude(String.valueOf(latitude));
        warkop.setAlamatWarkopLongitude(String.valueOf(longitude));
        warkop.setToken(regId);

        //ambil data dari tabel user
        warkop.setIdUser(user.getIdUser());

        //request connection
        endpoints = Connection.getEndpoints(this);
        endpoints.aadWarkop(warkop).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("status").equals("sukses")) {
                        String id_warkop = jsonObject.getString("id");
                        String id_user = user.getIdUser();
                        user.setIdWarkop(id_warkop);
                        editor.putString("id_warkop",id_warkop);

                        //Log.i("id_warkop",user.getIdWarkop());

                        String json = gson.toJson(user);

                        editor.putString("id_user",id_user);

                        editor.putString("user",json);
                        editor.commit();
                        startActivity(new Intent(TambahWarkopActivity.this, MainWarkopActivity.class));

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

    public void pickAlamatWarkop(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            startActivityForResult(intentBuilder.build(TambahWarkopActivity.this),5);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(TambahWarkopActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), TambahWarkopActivity.this, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.i("masuk onresult", "onresult");
        //Log.i("result   ", "request code "+requestCode+","+"result"+resultCode+"data"+data);
        super.onActivityResult(requestCode,resultCode,data);

        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == 5 && resultCode == RESULT_OK) {

            place_Picker = PlacePicker.getPlace(TambahWarkopActivity.this,data);

            stxtaddresplace = String.format("%s", place_Picker.getAddress().toString());
            latLng = place_Picker.getLatLng();
            latitude = (float) latLng.latitude;
            longitude = (float) latLng.longitude;
            alamat_warkopText.setText(""+ stxtaddresplace);

            }
        }
}