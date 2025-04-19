package com.example.app_truyen_do_an.Adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.the_loai;
import com.example.app_truyen_do_an.ui.tim_kiem.tim_the_loai;

import java.util.List;

public class TheloaiAdapter extends RecyclerView.Adapter<TheloaiAdapter.TheloaiViewHolder> {
    private List<the_loai> theLoaiList;
    private Context context;

    public TheloaiAdapter(List<the_loai> theLoaiList, Context context){
        this.context = context;
        this.theLoaiList = theLoaiList;
    }
    @NonNull
    @Override
    public TheloaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.the_loai,parent,false);
        return new TheloaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheloaiViewHolder holder, int position) {
        the_loai the_loai = theLoaiList.get(position);
        holder.text_the_loai.setText(the_loai.getThe_loai());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, tim_the_loai.class);
            intent.putExtra("id_tl",the_loai.getId_tl());
            intent.putExtra("name_tl",the_loai.getThe_loai());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return theLoaiList.size();
    }
    public static class TheloaiViewHolder extends RecyclerView.ViewHolder{
        TextView text_the_loai;
        public TheloaiViewHolder(@NonNull View itemView){
            super(itemView);
            text_the_loai = itemView.findViewById(R.id.text_the_loai);
        }
    }
}
