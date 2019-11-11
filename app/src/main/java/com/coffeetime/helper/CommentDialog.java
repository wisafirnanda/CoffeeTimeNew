package com.coffeetime.helper;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetime.R;
import com.coffeetime.admin.AdminActivity;
import com.coffeetime.client.DetailWarkopActivity;
import com.coffeetime.model.Komentar;
import com.coffeetime.model.Pemesanan;
import com.coffeetime.model.Status;
import com.coffeetime.networkmanager.Connection;
import com.coffeetime.networkmanager.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arief on 11-Sep-16.
 */
public class CommentDialog extends AlertDialog.Builder {

    Komentar komentar;
    Button kirim, batal;
    EditText isiKomentar;
    TextView id_warkop, id_user;

    Endpoints endpoints;

    public CommentDialog(final DetailWarkopActivity context, final Komentar komentar) {
        super(context);
        this.komentar = komentar;

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_comment, null);

        kirim = (Button) view.findViewById(R.id.send_comment);
        batal = (Button) view.findViewById(R.id.cancel_comment);

        isiKomentar = view.findViewById(R.id.comment);
        id_warkop = view.findViewById(R.id.id_warkop);
        id_user = view.findViewById(R.id.id_user);

        this.setView(view);
        this.setCancelable(false);
        final AlertDialog dialog = this.create();
        dialog.show();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isiKomentar.getText().toString().trim().equalsIgnoreCase(""))
                {
                    Toast.makeText(context, "Masukkan komentar Anda!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    context.showProgress("Loading..");

                    Komentar SetComment = new Komentar();
                    SetComment.setId_warkop(komentar.getId_warkop());
                    SetComment.setId_user(komentar.getId_user());
                    SetComment.setIsi_komentar(isiKomentar.getText().toString().trim());

                    endpoints = Connection.getEndpoints(context);
                    endpoints.sendComment(SetComment).enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if(response.code() == 200)
                            {
                                Status status = response.body();

                                if(status.getStatus().equalsIgnoreCase("sukses"))
                                {
                                    dialog.cancel();
                                    context.getComment();
                                }

                                context.hideProgress();

                                Toast.makeText(context, status.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            context.hideProgress();
                            Toast.makeText(context, "Gagal terkoneksi dengan server", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isiKomentar.setText("");

                dialog.cancel();
            }
        });
    }
}
