package com.coffeetime.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.admin.AdminActivity;
import com.coffeetime.client.MainClientActivity;
import com.coffeetime.client.PesanActivity;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arief on 11-Sep-16.
 */
public class DetailDialog extends AlertDialog.Builder {

    Pemesanan pemesanan;
    Button konfirmasi, batal;
    TextView idText, customerText, paymentTypeText, receiptText, dateTimeText;

    Endpoints endpoints;

    public DetailDialog(final AdminActivity context, final Pemesanan pemesanan) {
        super(context);
        this.pemesanan = pemesanan;

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_trans_list_detail, null);

        konfirmasi = (Button) view.findViewById(R.id.print);
        batal = (Button) view.findViewById(R.id.send);

        idText = (TextView) view.findViewById(R.id.cuid);
        customerText = (TextView) view.findViewById(R.id.customer);
        paymentTypeText = (TextView) view.findViewById(R.id.payment_type);
        receiptText = (TextView) view.findViewById(R.id.receipt);
        dateTimeText = (TextView) view.findViewById(R.id.date_time);

        idText.setText(pemesanan.getIdPemesanan());
        customerText.setText(pemesanan.getNama_warkop());
        paymentTypeText.setText(pemesanan.getTipe_bayar());
        receiptText.setText("Rp. " + pemesanan.getTotalHarga());
        dateTimeText.setText(pemesanan.getWaktu());

        this.setView(view);
        this.setCancelable(false);
        final AlertDialog dialog = this.create();
        dialog.show();

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showProgress("Loading..");

                Pemesanan pemesanan1 = new Pemesanan();
                pemesanan1.setIdPemesanan(idText.getText().toString().trim());

                endpoints = Connection.getEndpoints(context);
                endpoints.konfirmasiPemesanan(pemesanan1).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("status").equals("sukses")) {
                                Toast.makeText(context, "Konfirmasi pesanan berhasil!", Toast.LENGTH_LONG).show();

                                context.hideProgress();
                                dialog.cancel();
                                context.getData();
                            }
                            else if(jsonObject.getString("status").equals("gagal"))
                            {
                                Toast.makeText(context, "Konfirmasi Pemesanan gagal, coba lagi!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        context.hideProgress();
                        Toast.makeText(context, "Gagal terkoneksi dengan server", Toast.LENGTH_LONG).show();
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
