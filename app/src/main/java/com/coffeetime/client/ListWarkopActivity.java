package com.coffeetime.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.adapter.ListWarkopAdapter;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListWarkopActivity extends AppCompatActivity {

    Bundle bundle;

    private RecyclerView recyclerView;
    private ListWarkopAdapter adapter;
    private List<Warkop> warkopList;

    Endpoints endpoints;
    Warkop warkop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_warkop);

        bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            getSupportActionBar().setTitle(bundle.getString("title"));
            tampildata(bundle.getString("code"));
        }

        recyclerView = findViewById(R.id.recyclerview);
        //adapter = new ListWarkopAdapter(warkopArrayList);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListWarkopActivity.this, RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListWarkopActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);
    }

    void tampildata(String code) {
        endpoints = Connection.getEndpoints(ListWarkopActivity.this);

        if(code.equalsIgnoreCase("fav"))
        {
            endpoints.getWarkopFavorite().enqueue(new Callback<List<Warkop>>() {
                @Override
                public void onResponse(Call<List<Warkop>> call, Response<List<Warkop>> response) {
                    warkopList = new ArrayList<>(response.body());
                    adapter = new ListWarkopAdapter(ListWarkopActivity.this,warkopList);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Warkop>> call, Throwable t) {

                }
            });
        }
        else if (code.equalsIgnoreCase("new"))
        {
            endpoints.getWarkops().enqueue(new Callback<List<Warkop>>() {
                @Override
                public void onResponse(Call<List<Warkop>> call, Response<List<Warkop>> response) {
                    warkopList = new ArrayList<>(response.body());
                    adapter = new ListWarkopAdapter(ListWarkopActivity.this,warkopList);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Warkop>> call, Throwable t) {

                }
            });
        }
        else if (code.equalsIgnoreCase("sell"))
        {
            endpoints.getWarkopLaris().enqueue(new Callback<List<Warkop>>() {
                @Override
                public void onResponse(Call<List<Warkop>> call, Response<List<Warkop>> response) {
                    warkopList = new ArrayList<>(response.body());
                    adapter = new ListWarkopAdapter(ListWarkopActivity.this,warkopList);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Warkop>> call, Throwable t) {

                }
            });
        }
    }
}
