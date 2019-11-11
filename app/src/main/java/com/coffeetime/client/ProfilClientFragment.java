package com.coffeetime.client;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.model.User;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilClientFragment extends Fragment {

    TextView namaText, emailText, phoneText;

    Endpoints endpoints;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_client, container, false);

        namaText = view.findViewById(R.id.nama);
        emailText = view.findViewById(R.id.email);
        phoneText = view.findViewById(R.id.no_hp);

        sharedPreferences = getActivity().getSharedPreferences("coffee", 0);
        editor = sharedPreferences.edit();
        editor.apply();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("user","");

        user = gson.fromJson(json, new TypeToken<User>(){

        } .getType());

        tampildata();

        return view;
    }

    private void tampildata(){
        namaText.setText(user.getNama());
        emailText.setText(user.getEmail());
        phoneText.setText(user.getNoHp());

        //request connection
        endpoints = Connection.getEndpoints(getActivity());

    }
}
