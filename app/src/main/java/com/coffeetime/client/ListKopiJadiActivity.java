package com.coffeetime.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coffeetime.R;
import com.coffeetime.adapter.ListKopiJadiAdapter;
import com.coffeetime.model.Kopi;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKopiJadiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListKopiJadiAdapter adapter;
    private List<Kopi> kopiList;

    Endpoints endpoints;
    Kopi kopi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kopi_jadi);

        tampildata();

        recyclerView = findViewById(R.id.recyclerviewkopijadi);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListKopiJadiActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    void tampildata() {
        endpoints = Connection.getEndpoints(ListKopiJadiActivity.this);
        endpoints.getKopiJadi().enqueue(new Callback<List<Kopi>>() {
            @Override
            public void onResponse(Call<List<Kopi>> call, Response<List<Kopi>> response) {
                kopiList = new ArrayList<>(response.body());
                adapter = new ListKopiJadiAdapter(ListKopiJadiActivity.this, kopiList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Kopi>> call, Throwable t) {

            }
        });
    }
}
