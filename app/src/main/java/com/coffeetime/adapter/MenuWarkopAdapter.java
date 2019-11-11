package com.coffeetime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.model.Kopi;

import java.util.List;

public class MenuWarkopAdapter extends RecyclerView.Adapter<MenuWarkopAdapter.MenuKopiViewHolder> {

    private List<Kopi> dataList;

    public MenuWarkopAdapter(List<Kopi> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MenuKopiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_menu_warkop, parent, false);
        return new MenuKopiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuKopiViewHolder holder, int position) {
        holder.nama_kopi.setText(dataList.get(position).getNamaKopi());
        holder.jenis_kopi.setText(dataList.get(position).getJenisKopi());
        holder.harga_kopi.setText(dataList.get(position).getHargaKopi());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MenuKopiViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_kopi, jenis_kopi, harga_kopi;

        public MenuKopiViewHolder(View itemView) {
            super(itemView);
            nama_kopi = itemView.findViewById(R.id.txnamakopi);
            jenis_kopi = itemView.findViewById(R.id.txjeniskopi);
            harga_kopi = itemView.findViewById(R.id.txhargakopi);
        }
    }

}
