package com.example.app_truyen_do_an.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.anh;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.ui.profile_truyen.anhchuong;

import java.util.List;

public class anhAdapter extends RecyclerView.Adapter<anhAdapter.anhViewHolder> {

    private List<anh> list;
    private Context context;
    public anhAdapter(List<anh> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public anhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anh,parent,false);
        return new anhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull anhAdapter.anhViewHolder holder, int position) {
        anh anh = list.get(position);
        Glide.with(context).load(anh.getAnh())
                .placeholder(R.drawable.baseline_downloading_24) // ảnh loading
                .error(R.drawable._39366679_448495584426028_1815033509118262005_n)         // ảnh lỗi
                .into(holder.anh);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class anhViewHolder extends RecyclerView.ViewHolder{
        ImageView anh;
        public anhViewHolder(View itemview){
            super(itemview);
            anh = itemview.findViewById(R.id.imageView3);
        }
    }
}
