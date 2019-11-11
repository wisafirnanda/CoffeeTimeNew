package com.coffeetime.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.API.BaseApiService;
import com.coffeetime.R;
import com.coffeetime.client.PesanActivity;
import com.coffeetime.model.Orderan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListOrderanAdapter extends RecyclerView.Adapter<ListOrderanAdapter.OrderanViewHolder> {

    Context mContext;
    BaseApiService mApiService;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    private ArrayList<Orderan> orderanList;

    public ListOrderanAdapter(ArrayList<Orderan> dataList) {
        this.orderanList = dataList;
    }

    @NonNull
    @Override
    public ListOrderanAdapter.OrderanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_orderan, parent,false);

        return new OrderanViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ListOrderanAdapter.OrderanViewHolder holder, int position) {

        holder.tvNamaWarkop.setText(orderanList.get(position).getNama_kopi());
        holder.tvJenis.setText(orderanList.get(position).getJenis_kopi());
        holder.tvQty.setText(orderanList.get(position).getQty());
        holder.tvTotal.setText("Rp. " + orderanList.get(position).getTotal_harga());

        if(orderanList.get(position).getStatus_orderan().equalsIgnoreCase("0"))
        {
            holder.status.setText("Proses..");
        }
        else if(orderanList.get(position).getStatus_orderan().equalsIgnoreCase("1"))
        {
            holder.status.setText("Batal..");
        }

        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PesanActivity.class);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(),"BAYAR!!",Toast.LENGTH_SHORT).show();
            }
        });

        try {
            if (holder.countDownTimer != null) {
                holder.countDownTimer.cancel();
            }

            String myDate = orderanList.get(position).getWaktu();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = null;
            date = sdf.parse(myDate);

            long tanggal = date.getTime();

            Date date1 = new Date();
            long sekarang = date1.getTime();

            long diff = sekarang - tanggal;

            final long minutes = diff / (60 * 1000);
            diff -= minutes * (60 * 1000);
            final long seconds = diff / 1000;

            holder.countDownTimer = new CountDownTimer(diff, 1000){

                @Override
                public void onTick(long l) {
                    holder.waktu.setText(String.format("%02d", minutes / 1000) + ":" + String.format("%02d", seconds / 1000));
                }

                @Override
                public void onFinish() {
                    holder.waktu.setText("0");
                }
            }.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return (orderanList != null) ? orderanList.size() : 0;

    }

    class OrderanViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaWarkop,tvJenis,tvQty, tvTotal, status, waktu;
        Button btnBayar;

        CountDownTimer countDownTimer;

        OrderanViewHolder(final View view) {
            super(view);

            tvNamaWarkop = view.findViewById(R.id.nama_kopiOr);
            tvJenis = view.findViewById(R.id.jenis_kopiOr);
            tvQty = view.findViewById(R.id.jumlah_kopiOr);
            tvTotal = view.findViewById(R.id.total_hargaOr);
            status = view.findViewById(R.id.status_text);
            waktu = view.findViewById(R.id.waktu_bayar);

            btnBayar = view.findViewById(R.id.btnBayar);

        }
    }
}