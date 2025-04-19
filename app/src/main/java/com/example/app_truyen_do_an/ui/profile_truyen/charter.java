package com.example.app_truyen_do_an.ui.profile_truyen;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_truyen_do_an.Adapter.ChuongAdapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.model.Truyenviewmodel;

public class charter extends Fragment {
    private RecyclerView recyclerView;
    private ChuongAdapter chuongAdapter;
    private TextView tong_chuong;

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charter, container, false);
        recyclerView = view.findViewById(R.id.view_so_chuong);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tong_chuong = view.findViewById(R.id.tong_chuong);
        Truyenviewmodel viewModel = new ViewModelProvider(requireActivity()).get(Truyenviewmodel.class);
        viewModel.gettruyenchitiet().observe(getViewLifecycleOwner(), truyen -> {
            if (truyen != null && truyen.getChuong() != null) {
                chuongAdapter = new ChuongAdapter(truyen.getChuong(),truyen.getId_truyen());
                recyclerView.setAdapter(chuongAdapter);
                tong_chuong.setText("Tổng số "+ truyen.getTong_chuong()+" chương");
            }
        });
        return view;
    }
}