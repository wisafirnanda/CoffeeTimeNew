package com.coffeetime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coffeetime.R;
import com.coffeetime.model.Kopi;

import java.util.List;

public class ListKopiBubukAdapter extends RecyclerView.Adapter<ListKopiBubukAdapter.ListKopiBubukViewHolder> {
    private List<Kopi> dataList;
    private Context context;

    public ListKopiBubukAdapter(Context context, List<Kopi> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ListKopiBubukAdapter.ListKopiBubukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_kopi, parent, false);
        return new ListKopiBubukAdapter.ListKopiBubukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListKopiBubukAdapter.ListKopiBubukViewHolder holder, final int position) {
        holder.namakopi.setText(dataList.get(position).getNamaKopi());
        holder.jeniskopi.setText(dataList.get(position).getJenisKopi());
        holder.hargakopi.setText(dataList.get(position).getHargaKopi());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListKopiBubukViewHolder extends RecyclerView.ViewHolder {
        private TextView namakopi, jeniskopi, hargakopi;
        RelativeLayout detailKopiBubukLayout;

        public ListKopiBubukViewHolder (View itemView) {
            super(itemView);

            namakopi = itemView.findViewById(R.id.txnamakopi);
            jeniskopi = itemView.findViewById(R.id.txjeniskopi);
            hargakopi = itemView.findViewById(R.id.txhargakopi);

            /*detailKopiJadiLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Kopi kopi = dataList.get(getAdapterPosition());
                    Intent intent = new Intent(context, DetailWarkopActivity.class);
                    //intent.putExtra("kopi", kopi.getIdWarkop());
                    context.startActivity(intent);
                }
            });*/

        }
    }
}
