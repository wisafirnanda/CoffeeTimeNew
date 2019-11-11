package com.coffeetime.warkop;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.adapter.MenuWarkopAdapter;
import com.coffeetime.model.Kopi;
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

public class MenuFragment extends Fragment {

    TextView namakopi, jeniskopi, hargakopi;
    private RecyclerView recyclerView;
    private MenuWarkopAdapter adapter;
    private List<Kopi> kopiArrayList;

    private FloatingActionButton tambah_menu;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText nama_kopi, harga_kopi;
    Spinner jenis_kopi;

    Endpoints endpoints;
    Kopi kopi;
    Warkop warkop;

    //Tes
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_warkop, container, false);

        tambah_menu = view.findViewById(R.id.tambah_menu);
        recyclerView = view.findViewById(R.id.recyclerviewmenuwarkop);

        //adapter = new MenuWarkopAdapter(kopiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);

        sharedPreferences = getActivity().getSharedPreferences("coffee",0);
        editor = sharedPreferences.edit();
        editor.apply();

        gson = new Gson();
        String json = sharedPreferences.getString("user","");

        warkop = gson.fromJson(json, new TypeToken<Warkop>(){

        }.getType());

        tambah_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });

//SERVICE
        loopingStart();

        return view;
    }

    public void loopingStart() {
        final boolean keepRunning1 = true;
        Thread t = new Thread(){
            @Override
            public void run(){

                while(keepRunning1){

                    // Make the thread wait half a second. If you want...
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Default Signature Fail", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    // here you check the value of getActivity() and break up if needed
                    if(getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                             tampildata();
//                           Toast.makeText(getContext(),"BERHASIL",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        t.start();
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_menu, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Tambah Menu");

        nama_kopi = dialogView.findViewById(R.id.nama_kopi);
        harga_kopi = dialogView.findViewById(R.id.harga_kopi);
        jenis_kopi = dialogView.findViewById(R.id.listJenisKopi);


        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                kopi = new Kopi();
                kopi.setNamaKopi(nama_kopi.getText().toString());
                kopi.setHargaKopi(harga_kopi.getText().toString());
                kopi.setJenisKopi(jenis_kopi.getSelectedItem().toString());

                kopi.setIdWarkop(warkop.getIdWarkop());

                //request connection
                endpoints = Connection.getEndpoints(getActivity());
                endpoints.aadKopi(kopi).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("status").equals("sukses")) {
                                String id_kopi = jsonObject.getString("id");
                                String id_warkop = warkop.getIdWarkop();
                                warkop.setIdKopi(id_kopi);
                                editor.putString("id_kopi",id_kopi);

                                String json = gson.toJson(warkop);

                                editor.putString("id_warkop",id_warkop);

                                editor.putString("warkop",json);
                                editor.commit();
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
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void tampildata() {
        kopi = new Kopi();
        kopi.setIdKopi(warkop.getIdKopi());
        kopi.setIdWarkop(warkop.getIdWarkop());

        //data kopi
        endpoints = Connection.getEndpoints(getActivity());
        endpoints.getKopi(kopi).enqueue(new Callback<List<Kopi>>() {
            @Override
            public void onResponse(Call<List<Kopi>> call, Response<List<Kopi>> response) {
                if (response.body() != null){
                    kopiArrayList = new ArrayList<>(response.body());
                    //Log.i("ceksize",""+kopiArrayList);
                    adapter = new MenuWarkopAdapter(kopiArrayList);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Kopi>> call, Throwable t) {

            }
        });
    }



}