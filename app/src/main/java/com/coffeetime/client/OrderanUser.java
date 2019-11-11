package com.coffeetime.client;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.coffeetime.API.BaseApiService;
import com.coffeetime.API.OrderanList;
import com.coffeetime.API.UtilsApi;
import com.coffeetime.R;
import com.coffeetime.adapter.ListOrderanAdapter;
import com.coffeetime.model.Orderan;
import com.coffeetime.warkop.MainWarkopActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderanUser extends AppCompatActivity {

    private BaseApiService mApiService;

    private ListOrderanAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderan_user);

        mApiService = UtilsApi.getAPIService();

        Call<OrderanList> call = mApiService.getOrderanData(100);

        Log.i("Panggil URL", call.request().url()+"");

        call.enqueue(new Callback<OrderanList>() {
            @Override
            public void onResponse(Call<OrderanList> call, Response<OrderanList> response) {
                generateOrderanList(response.body().getOrderanListArrayList());
            }

            @Override
            public void onFailure(Call<OrderanList> call, Throwable t) {
                Toast.makeText(OrderanUser.this,"Terjadi kesalahan...  Coba lagi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateOrderanList(ArrayList<Orderan> orderanListArrayList) {

        recyclerView = findViewById(R.id.rc_orderan);

        adapter = new ListOrderanAdapter(orderanListArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderanUser.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(OrderanUser.this, MainClientActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case(android.R.id.home):
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
