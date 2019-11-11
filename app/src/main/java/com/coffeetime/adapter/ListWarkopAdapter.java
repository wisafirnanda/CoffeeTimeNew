package com.coffeetime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.client.DetailWarkopActivity;
import com.coffeetime.model.Warkop;

import java.util.List;

public class ListWarkopAdapter extends RecyclerView.Adapter<ListWarkopAdapter.ListWarkopViewHolder> {

    private List<Warkop> dataList;
    private Context context;

    public ListWarkopAdapter(Context context, List<Warkop> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ListWarkopAdapter.ListWarkopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_warkop, parent, false);
        return new ListWarkopAdapter.ListWarkopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListWarkopAdapter.ListWarkopViewHolder holder, final int position) {
        holder.nama_warkop.setText(dataList.get(position).getNamaWarkop());
        holder.alamat_warkop.setText(dataList.get(position).getAlamatWarkop());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListWarkopViewHolder extends RecyclerView.ViewHolder {
        private TextView nama_warkop, alamat_warkop;
        RelativeLayout detailWarkopLayout;


        public ListWarkopViewHolder(View itemView) {
            super(itemView);

            nama_warkop = itemView.findViewById(R.id.txnama_warkop);
            alamat_warkop = itemView.findViewById(R.id.txalamat_warkop);
            detailWarkopLayout = itemView.findViewById(R.id.warkopDetailLayout);

            detailWarkopLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Warkop warkop = dataList.get(getAdapterPosition());
                    Intent intent = new Intent(context, DetailWarkopActivity.class);
                    intent.putExtra("warkop", warkop);
                    context.startActivity(intent);
                }
            });
        }
    }
}

