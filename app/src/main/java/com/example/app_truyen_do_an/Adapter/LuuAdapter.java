package com.example.app_truyen_do_an.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.ui.profile_truyen.thong_tin_truyen;

import java.util.List;

public class LuuAdapter extends RecyclerView.Adapter<LuuAdapter.LuuViewHolder> {
    private List<truyen> list;
    private Context context;
    public LuuAdapter(List<truyen> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LuuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.truyen_moi, parent, false);
        return new LuuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LuuAdapter.LuuViewHolder holder, int position) {
        truyen truyen = list.get(position);
        holder.ten_truyen.setText(truyen.getName_truyen());
        if (truyen.getChuong() != null && !truyen.getChuong().isEmpty()) {
            holder.chuong_truyen.setText("Chương " + truyen.getChuong().get(0).getso_Chuong());
        } else {
            holder.chuong_truyen.setText("Chưa có chương");
        }
        Glide.with(context).load(truyen.getAnh())
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable._39366679_448495584426028_1815033509118262005_n)
                .into(holder.anh_truyen);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, thong_tin_truyen.class);
            intent.putExtra("id_truyen",truyen.getId_truyen());
            intent.putExtra("name_truyen",truyen.getName_truyen());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LuuViewHolder extends RecyclerView.ViewHolder {
        TextView ten_truyen, chuong_truyen;
        ImageView anh_truyen;
        public LuuViewHolder(View itemview) {
            super(itemview);
            ten_truyen = itemView.findViewById(R.id.ten_truyen);
            chuong_truyen = itemView.findViewById(R.id.chuong_truyen);
            anh_truyen = itemView.findViewById(R.id.anh_truyen);
        }
    }
}
