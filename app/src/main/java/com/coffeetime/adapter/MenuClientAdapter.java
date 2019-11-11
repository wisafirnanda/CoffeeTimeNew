package com.coffeetime.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.model.Kopi;

import java.util.List;

public class MenuClientAdapter extends RecyclerView.Adapter<MenuClientAdapter.MenuClientViewHolder> {

    private List<Kopi> dataList;
    private OnQuantityChangedListener listener;

    public MenuClientAdapter(List<Kopi> dataList) {
        this.dataList = dataList;
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public MenuClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_menu_client, parent, false);
        return new MenuClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuClientAdapter.MenuClientViewHolder holder, final int position) {
        holder.nama_kopi.setText(dataList.get(position).getNamaKopi());
        holder.jenis_kopi.setText(dataList.get(position).getJenisKopi());
        holder.harga_kopi.setText(dataList.get(position).getHargaKopi());
        holder.txquantity.setText(dataList.get(position).getQty());

        holder.id = dataList.get(position).getIdKopi();

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int harga = Integer.parseInt(holder.harga_kopi.getText().toString());
                int quantity = Integer.parseInt(holder.txquantity.getText().toString());
                //int total = harga * quantity;
                //harga_total.setText(total+"");

                //int total = harga * quantity;
                quantity += 1;
                holder.txquantity.setText(String.valueOf(quantity));
                dataList.get(position).setQty(String.valueOf(quantity));

                listener.onQuantityChanged();
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int harga = Integer.parseInt(holder.harga_kopi.getText().toString());
                int quantity = Integer.parseInt(holder.txquantity.getText().toString());
                //int total = harga * quantity;

                //harga_total.setText(total+"");

                quantity = quantity - 1;
                if (quantity < 0) {
                    quantity = 0;
                }
                holder.txquantity.setText(String.valueOf(quantity));
                dataList.get(position).setQty(String.valueOf(quantity));

                listener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public interface OnQuantityChangedListener {
        void onQuantityChanged();
    }

    public class MenuClientViewHolder extends RecyclerView.ViewHolder {
        private TextView nama_kopi, jenis_kopi, harga_kopi, txquantity;
        private ImageView decrement, increment;
        private String id;

        public MenuClientViewHolder(View itemView) {
            super(itemView);
            nama_kopi = itemView.findViewById(R.id.txnama_kopi);
            jenis_kopi = itemView.findViewById(R.id.txjenis_kopi);
            harga_kopi = itemView.findViewById(R.id.txharga_kopi);

            txquantity = itemView.findViewById(R.id.tx_quantity);
            decrement = itemView.findViewById(R.id.btn_decrement);
            increment = itemView.findViewById(R.id.btn_increment);
        }
    }
}
