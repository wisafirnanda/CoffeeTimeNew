package com.coffeetime.warkop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coffeetime.LoginActivity;
import com.coffeetime.R;
import com.coffeetime.model.User;
import com.coffeetime.model.Warkop;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilWarkopFragment extends Fragment {

    TextView namaakun, namawarkop, namapemilik, cpwarkop, alamatwarkop, waktubuka;
    Button btnLogout;

    Endpoints endpoints;
    Warkop warkop;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_warkop, container, false);

        namaakun = view.findViewById(R.id.txnamaakun);
        namawarkop = view.findViewById(R.id.txnamawarkop);
        namapemilik = view.findViewById(R.id.txnamapemilik);
        cpwarkop = view.findViewById(R.id.txcpwarkop);
        alamatwarkop = view.findViewById(R.id.txalamatwarkop);
        waktubuka = view.findViewById(R.id.txwaktubuka);
        btnLogout = view.findViewById(R.id.btn_logout);

        sharedPreferences = getActivity().getSharedPreferences("coffee", 0);
        editor = sharedPreferences.edit();
        editor.apply();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("user","");

        user = gson.fromJson(json, new TypeToken<User>(){

        }.getType());

        tampildata();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }

    private void tampildata() {
        warkop = new Warkop();
        namaakun.setText(user.getNama());

        warkop.setIdWarkop(user.getIdWarkop());
        warkop.setIdUser(user.getIdUser());

        //data profil warkop
        endpoints = Connection.getEndpoints(getActivity());
        endpoints.getWarkop(warkop).enqueue(new Callback<Warkop>() {
            @Override
            public void onResponse(Call<Warkop> call, Response<Warkop> response) {
                if (response.body()!=null) {
                    warkop = response.body();

                    namawarkop.setText(warkop.getNamaWarkop());
                    namapemilik.setText(warkop.getNamaPemilik());
                    cpwarkop.setText(warkop.getCpWarkop());
                    waktubuka.setText(warkop.getWaktuBuka());
                    alamatwarkop.setText(warkop.getAlamatWarkop());
                }
            }

            @Override
            public void onFailure(Call<Warkop> call, Throwable t) {

            }
        });
    }
}
