package com.example.app_truyen_do_an.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.ui.profile_truyen.anhchuong;

import java.util.List;

public class ChuongAdapter extends RecyclerView.Adapter<ChuongAdapter.ChuongViewHolder> {
    private List<Chuong> chuongList;
    private String id_truyen;

    public ChuongAdapter(List<Chuong> chuongList, String id_truyen) {
        this.chuongList = chuongList;
        this.id_truyen = id_truyen;
    }

    @NonNull
    @Override
    public ChuongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chuong_truyen, parent, false);
        return new ChuongViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChuongViewHolder holder, int position) {
        Chuong chuong = chuongList.get(position);
        String text = "Chương " + chuong.getso_Chuong();
        if (chuong.getTieu_de() != null && !chuong.getTieu_de().isEmpty()) {
            text += ": " + chuong.getTieu_de();
        }
        holder.so_chuong_tt.setText(text);
        holder.ngay_dang_tt.setText(chuong.getThoi_gian_chuong());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), anhchuong.class);
            intent.putExtra("id_chuong",chuong.getId_chuong());
            intent.putExtra("chuong", chuong.getso_Chuong());
            intent.putExtra("id_truyen", id_truyen);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chuongList.size();
    }

    public static class ChuongViewHolder extends RecyclerView.ViewHolder {
        TextView so_chuong_tt, ngay_dang_tt;

        public ChuongViewHolder(@NonNull View itemView) {
            super(itemView);
            so_chuong_tt = itemView.findViewById(R.id.so_chuong_tt);
            ngay_dang_tt = itemView.findViewById(R.id.ngay_dang_tt);
        }
    }
}

