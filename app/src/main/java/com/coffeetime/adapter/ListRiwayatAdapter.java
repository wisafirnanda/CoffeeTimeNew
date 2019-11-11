package com.coffeetime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.Riwayat;

import java.util.ArrayList;
import java.util.List;

public class ListRiwayatAdapter extends RecyclerView.Adapter<ListRiwayatAdapter.ListRiwayatViewHolder> {
    private List<Pemesanan> dataList;

    public ListRiwayatAdapter(List<Pemesanan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ListRiwayatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_riwayat, parent, false);
        return new ListRiwayatViewHolder (view);
    }

    @Override
    public void onBindViewHolder(ListRiwayatViewHolder holder, int position) {
        holder.id.setText(dataList.get(position).getIdPemesanan());
        holder.namakopi.setText(dataList.get(position).getNama_warkop());
        holder.status.setText(dataList.get(position).getStatus());
        holder.waktu.setText(dataList.get(position).getWaktu());
        holder.harga.setText("Rp. " + dataList.get(position).getTotalHarga());
        holder.tipe_bayar.setText(dataList.get(position).getTipe_bayar());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;

    }

    public class ListRiwayatViewHolder extends RecyclerView.ViewHolder{
        private TextView id, namakopi, status, waktu, harga, tipe_bayar;

        public ListRiwayatViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txid);
            namakopi = itemView.findViewById(R.id.txnamakopi);
            status = itemView.findViewById(R.id.txstatus);
            waktu = itemView.findViewById(R.id.txwaktu);
            harga = itemView.findViewById(R.id.txharga);
            tipe_bayar = itemView.findViewById(R.id.txbayar);
        }
    }
}
