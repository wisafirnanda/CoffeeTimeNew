package com.coffeetime.helper;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.client.DetailWarkopActivity;
import com.coffeetime.client.RiwayatFragment;
import com.coffeetime.model.Komentar;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.Status;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arief on 11-Sep-16.
 */
public class ConfirmDialog extends AlertDialog.Builder {

    Pemesanan pemesanan;
    Button kirim, batal;
    TextView id_pemesanan, id_user;

    Endpoints endpoints;

    public ConfirmDialog(final RiwayatFragment context, final Pemesanan pemesanan) {
        super(context.getActivity());
        this.pemesanan = pemesanan;

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_confirm, null);

        kirim = (Button) view.findViewById(R.id.send_comment);
        batal = (Button) view.findViewById(R.id.cancel_comment);

        id_pemesanan = view.findViewById(R.id.id_pemesanan);
        id_user = view.findViewById(R.id.id_user);

        this.setView(view);
        this.setCancelable(false);
        final AlertDialog dialog = this.create();
        dialog.show();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showProgress("Loading..");

                final Pemesanan confirm = new Pemesanan();
                confirm.setIdPemesanan(pemesanan.getIdPemesanan());
                confirm.setIdUser(pemesanan.getIdUser());

                endpoints = Connection.getEndpoints(context.getActivity());
                endpoints.konfirmasiPengiriman(confirm).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("status").equals("sukses")) {
                                Toast.makeText(context.getActivity(), "Konfirmasi pesanan berhasil!", Toast.LENGTH_LONG).show();

                                context.hideProgress();
                                context.tampildata();
                                dialog.cancel();
                            }
                            else if(jsonObject.getString("status").equals("gagal"))
                            {
                                Toast.makeText(context.getActivity(), "Konfirmasi Pemesanan gagal, coba lagi!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        context.hideProgress();
                        Toast.makeText(context.getActivity(), "Gagal terkoneksi dengan server", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}
