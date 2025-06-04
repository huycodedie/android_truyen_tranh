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

import java.util.List;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        tong_chuong = view.findViewById(R.id.tong_chuong);
        Truyenviewmodel viewModel = new ViewModelProvider(requireActivity()).get(Truyenviewmodel.class);
        viewModel.gettruyenchitiet().observe(getViewLifecycleOwner(), truyen -> {
            if (truyen != null && isValidChuongList(truyen.getChuong())) {
                chuongAdapter = new ChuongAdapter(truyen.getChuong(),truyen.getId_truyen(),truyen.getAnh(),getContext());
                recyclerView.setAdapter(chuongAdapter);
            }
            tong_chuong.setText("Tổng số "+ truyen.getTong_chuong()+" chương");
        });
        return view;
    }
    private boolean isValidChuongList(List<Chuong> list) {
        if (list == null || list.isEmpty()) return false;

        for (Chuong c : list) {
            if (c.getId_chuong() != null && !c.getId_chuong().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}