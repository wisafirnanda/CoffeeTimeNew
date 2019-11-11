package com.coffeetime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.client.DetailBijiKopi;
import com.coffeetime.client.DetailWarkopActivity;
import com.coffeetime.model.Kopi;
import com.coffeetime.model.Warkop;

import java.util.List;

public class ListKopiBijiAdapter extends RecyclerView.Adapter<ListKopiBijiAdapter.ListKopiBijiViewHolder> {

    private List<Kopi> dataList;
    private Context context;

    public ListKopiBijiAdapter(Context context, List<Kopi> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ListKopiBijiAdapter.ListKopiBijiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_kopi, parent, false);

        return new ListKopiBijiAdapter.ListKopiBijiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListKopiBijiAdapter.ListKopiBijiViewHolder holder, final int position) {
        holder.namakopi.setText(dataList.get(position).getNamaKopi());
        holder.jeniskopi.setText(dataList.get(position).getJenisKopi());
        holder.hargakopi.setText(dataList.get(position).getHargaKopi());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListKopiBijiViewHolder extends RecyclerView.ViewHolder {
        private TextView namakopi, jeniskopi, hargakopi;
        RelativeLayout detailKopiBijiLayout;

        public ListKopiBijiViewHolder (View itemView) {
            super(itemView);

            namakopi = itemView.findViewById(R.id.txnamakopi);
            jeniskopi = itemView.findViewById(R.id.txjeniskopi);
            hargakopi = itemView.findViewById(R.id.txhargakopi);
            detailKopiBijiLayout = itemView.findViewById(R.id.bijiDetailLayout);

            detailKopiBijiLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Kopi kopi = dataList.get(getAdapterPosition());

                    Intent intent = new Intent(context, DetailBijiKopi.class);

                    intent.putExtra("kopi", kopi);
                    context.startActivity(intent);
                }
            });

        }
    }
}
