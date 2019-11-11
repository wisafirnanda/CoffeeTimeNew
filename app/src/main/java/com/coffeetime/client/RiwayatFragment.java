package com.coffeetime.client;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.adapter.ListRiwayatAdapter;
import com.coffeetime.helper.ConfirmDialog;
import com.coffeetime.helper.RecyclerViewOnItemClick;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.Riwayat;
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

public class RiwayatFragment extends Fragment {
    TextView namakopi, jeniskopi, hargakopi;
    private RecyclerView recyclerView;
    private ListRiwayatAdapter adapter;
    private List<Pemesanan> riwayatList;

    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;

    Endpoints endpoints;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat_client, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewriwayat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(getActivity());

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("coffee",0);
        editor = sharedPreferences.edit();
        editor.apply();

        gson = new Gson();
        String json = sharedPreferences.getString("user","");

        user = gson.fromJson(json, new TypeToken<User>(){

        }.getType());

        tampildata();

        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClick(getActivity().getApplicationContext(), new RecyclerViewOnItemClick.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                RelativeLayout viewRoot = (RelativeLayout) view;
                CardView cardView = (CardView) viewRoot.getChildAt(0);
                LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
                RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(1);

                TextView id = (TextView) relativeLayout.getChildAt(0);
                TextView status = (TextView) relativeLayout.getChildAt(2);

                String statusText = status.getText().toString().trim();

                Pemesanan pemesanan = new Pemesanan();
                pemesanan.setIdPemesanan(id.getText().toString().trim());

                if(statusText.equalsIgnoreCase("LUNAS"))
                {
                    new ConfirmDialog(RiwayatFragment.this, pemesanan);
                }
            }
        }));

        return view;
    }

    public void tampildata() {
        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setIdUser(user.getIdUser());

        endpoints = Connection.getEndpoints(getActivity().getApplicationContext());
        endpoints.getPemesanan(pemesanan).enqueue(new Callback<List<Pemesanan>>() {
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