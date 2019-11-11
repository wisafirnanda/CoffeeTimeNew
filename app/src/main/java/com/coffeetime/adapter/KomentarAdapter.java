package com.coffeetime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.model.Komentar;
import com.coffeetime.model.PesananWarkop;

import java.util.ArrayList;
import java.util.List;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.PesananViewHolder>  {

    private List<Komentar> dataList;

    public KomentarAdapter(List<Komentar> dataList) {
        this.dataList = dataList;
    }

    @Override
    public KomentarAdapter.PesananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_komentar, parent, false);
        return new KomentarAdapter.PesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KomentarAdapter.PesananViewHolder holder, int position) {
        //holder.id_kopi.setText(dataList.get(position).getId_kopi());
        holder.nama_user.setText(dataList.get(position).getNama_user());
        holder.isi_komen.setText(dataList.get(position).getIsi_komentar());
        holder.waktu_komen.setText(dataList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class PesananViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_user, isi_komen, waktu_komen;

        public PesananViewHolder(View itemView) {
            super(itemView);
            //id_kopi = itemView.findViewById(R.id.txt_nama_mahasiswa);
            nama_user = itemView.findViewById(R.id.nama_user);
            isi_komen = itemView.findViewById(R.id.isi_komentar);
            waktu_komen = itemView.findViewById(R.id.waktu_komen);
        }
    }

}
