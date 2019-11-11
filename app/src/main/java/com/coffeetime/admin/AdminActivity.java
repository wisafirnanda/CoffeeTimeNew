package com.coffeetime.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.LoginActivity;
import com.coffeetime.R;
import com.coffeetime.adapter.ListRiwayatAdapter;
import com.coffeetime.helper.DetailDialog;
import com.coffeetime.helper.RecyclerViewOnItemClick;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ListRiwayatAdapter adapter;
    private List<Pemesanan> riwayatList;

    private ProgressDialog progressDialog;

    Endpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Halaman Admin");

        progressDialog = new ProgressDialog(this);

        swipeRefreshLayout = findViewById(R.id.admin_swipe);
        recyclerView = findViewById(R.id.riwayat_admin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();

        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClick(AdminActivity.this, new RecyclerViewOnItemClick.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                showDialogDetailTrans(view);
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);//Menu ResourceFile
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:

                startActivity(new Intent(AdminActivity.this, LoginActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getData()
    {
        swipeRefreshLayout.setRefreshing(true);
        endpoints = Connection.getEndpoints(AdminActivity.this);
        endpoints.getPemesananAdmin().enqueue(new Callback<List<Pemesanan>>() {
            @Override
            public void onResponse(Call<List<Pemesanan>> call, Response<List<Pemesanan>> response) {
                riwayatList = new ArrayList<>(response.body());
                adapter = new ListRiwayatAdapter(riwayatList);
                recyclerView.setAdapter(adapter);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Pemesanan>> call, Throwable t) {

            }
        });
    }

    public void showDialogDetailTrans(View view){
        RelativeLayout viewRoot = (RelativeLayout) view;
        CardView cardView = (CardView) viewRoot.getChildAt(0);
        LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
        RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(1);

        TextView id = (TextView) relativeLayout.getChildAt(0);

        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setIdPemesanan(id.getText().toString().trim());

        showProgress("Loading...");
        endpoints = Connection.getEndpoints(AdminActivity.this);
        endpoints.getPemesananDetail(pemesanan).enqueue(new Callback<Pemesanan>() {
            @Override
            public void onResponse(Call<Pemesanan> call, Response<Pemesanan> response) {
                hideProgress();
                Pemesanan pemesanan1 = response.body();

                new DetailDialog(AdminActivity.this, pemesanan1);
            }

            @Override
            public void onFailure(Call<Pemesanan> call, Throwable t) {
                hideProgress();
                Toast.makeText(AdminActivity.this, "Gagal terkoneksi dengan server", Toast.LENGTH_LONG).show();
            }
        });
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
