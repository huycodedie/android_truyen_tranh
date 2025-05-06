package com.example.app_truyen_do_an.Adapter;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.the_loai;
import com.example.app_truyen_do_an.ui.tim_kiem.tim_the_loai;

import java.util.ArrayList;
import java.util.List;

public class TheloaitimAdapter extends RecyclerView.Adapter<TheloaitimAdapter.TheloaiViewHolder> {
    private List<the_loai> theLoaiList;
    private Context context;
    List<the_loai> selectedList = new ArrayList<>();

    public TheloaitimAdapter(List<the_loai> theLoaiList, Context context){
        this.context = context;
        this.theLoaiList = theLoaiList;
    }
    @NonNull
    @Override
    public TheloaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.the_loai_botton,parent,false);
        return new TheloaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheloaiViewHolder holder, int position) {
        the_loai the_loai = theLoaiList.get(position);
        holder.text_the_loai.setText(the_loai.getThe_loai());
        holder.text_the_loai.setOnClickListener(v -> {
            the_loai item = theLoaiList.get(position);
            if (selectedList.contains(item)) {
                selectedList.remove(item);holder.text_the_loai.setBackgroundResource(R.drawable.background_bo_goc_the_loai);
            } else {
                selectedList.add(item);
                holder.text_the_loai.setBackgroundResource(R.drawable.background_bo_goc_the_loai_2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return theLoaiList.size();
    }
    public List<the_loai> getSelectedItems() {
        return selectedList;
    }
    public static class TheloaiViewHolder extends RecyclerView.ViewHolder{
        TextView text_the_loai;
        public TheloaiViewHolder(@NonNull View itemView){
            super(itemView);
            text_the_loai = itemView.findViewById(R.id.button_the_loai);
        }
    }
}
