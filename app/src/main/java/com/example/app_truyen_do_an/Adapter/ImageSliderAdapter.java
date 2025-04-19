package com.example.app_truyen_do_an.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.ui.profile_truyen.thong_tin_truyen;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {
    private List<truyen> list;
    private Context context;

    public ImageSliderAdapter(List<truyen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_slider, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        truyen truyen = list.get(position);
        Glide.with(context)
                .load(truyen.getAnh())
                .into(holder.imageView);
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

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSlider);
        }
    }
}




