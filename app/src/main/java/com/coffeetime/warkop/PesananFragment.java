package com.coffeetime.warkop;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffeetime.R;
import com.coffeetime.adapter.ListRiwayatAdapter;
import com.coffeetime.adapter.PesananWarkopAdapter;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.PesananWarkop;
import com.coffeetime.model.User;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListRiwayatAdapter adapter;
    private List<Pemesanan> riwayatList;

    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;

    Endpoints endpoints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan_warkop, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("coffee",0);
        editor = sharedPreferences.edit();
        editor.apply();

        gson = new Gson();
        String json = sharedPreferences.getString("user","");

        user = gson.fromJson(json, new TypeToken<User>(){

        }.getType());

        tampildata();

        return view;
    }

    void tampildata(){
        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setIdWarkop(user.getIdWarkop());

        endpoints = Connection.getEndpoints(getActivity().getApplicationContext());
        endpoints.getPemesananJual(pemesanan).enqueue(new Callback<List<Pemesanan>>() {
            @Override
            public void onResponse(Call<List<Pemesanan>> call, Response<List<Pemesanan>> response) {
                riwayatList = new ArrayList<>(response.body());
                adapter = new ListRiwayatAdapter(riwayatList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Pemesanan>> call, Throwable t) {

            }
        });
    }
}
