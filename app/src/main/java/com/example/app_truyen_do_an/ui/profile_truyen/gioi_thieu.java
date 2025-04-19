package com.example.app_truyen_do_an.ui.profile_truyen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_truyen_do_an.Adapter.TheloaiAdapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.Truyenviewmodel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class gioi_thieu extends Fragment {
    private TextView gioi_thieu_tt, name_tac_gia_tt, ten_truyen_tt, luot_xem, tien_do;
    private ImageView anh_tt, theo_doi;
    private RecyclerView recyclerView;
    private int so1 = 1, so0 = 0;
    private TheloaiAdapter theloaiAdapter;
    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gioi_thieu,container, false);

        tien_do = view.findViewById(R.id.tien_do);
        ten_truyen_tt = view.findViewById(R.id.ten_truyen_tt);
        name_tac_gia_tt = view.findViewById(R.id.name_tac_gia_tt);
        gioi_thieu_tt = view.findViewById(R.id.gioi_thieu_tt);
        anh_tt = view.findViewById(R.id.anh_tt);
        theo_doi = view.findViewById(R.id.theo_doi_truyen);
        luot_xem = view.findViewById(R.id.luot_xem);
        recyclerView = view.findViewById(R.id.view_the_loai);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        Truyenviewmodel viewmodel = new ViewModelProvider(requireActivity()).get(Truyenviewmodel.class);

        viewmodel.gettruyenchitiet().observe(getViewLifecycleOwner(), truyen -> {
            ten_truyen_tt.setText(truyen.getName_truyen());
            name_tac_gia_tt.setText(truyen.getName_tac_gia());
            gioi_thieu_tt.setText(truyen.getGioi_thieu());
            luot_xem.setText(truyen.getLuot_xem());
            Glide.with(getContext())
                    .load(truyen.getAnh())
                    .into(anh_tt);
            if (truyen != null && truyen.getThe_loai() != null){
                theloaiAdapter = new TheloaiAdapter(truyen.getThe_loai(),getContext());
                recyclerView.setAdapter(theloaiAdapter);
            }
            if(truyen.getTrang_thai() == 1){
                tien_do.setText("Đang tiến hành");
            }else {
                tien_do.setText("Đã hoàn thành");
            }
            if (truyen.getLuu() == 0){
                theo_doi.setImageTintList(ContextCompat.getColorStateList(requireContext(),R.color.gray));
            }else {
                theo_doi.setImageTintList(ContextCompat.getColorStateList(requireContext(),R.color.orange));
            }
            theo_doi.setOnClickListener(v ->{

                ColorStateList currentTint = theo_doi.getImageTintList();
                ColorStateList gray = ContextCompat.getColorStateList(requireContext(), R.color.gray);

                if (currentTint != null && currentTint.equals(gray)) {

                    luutruyen(truyen.getId_truyen(),so1);
                } else {
                    theo_doi.setImageTintList(gray);
                    luutruyen(truyen.getId_truyen(),so0);
                }

            });
            savetruyen(truyen.getId_truyen());
        });
        return view;
    }

    private void luutruyen(String idTruyen, int so) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int id_user = sharedPreferences.getInt("id_user", -1);
        if (id_user == -1) {
            Toast.makeText(getContext(), "vui lòng đăng nhập để theo dõi truyện", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
        Call<Void>call = apiService.themluu(id_user,idTruyen,so);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(so == 1 ){
                    Toast.makeText(getContext(), "theo dõi truyện thành công", Toast.LENGTH_SHORT).show();
                    theo_doi.setImageTintList(ContextCompat.getColorStateList(requireContext(), R.color.orange));
                }else {
                    Toast.makeText(getContext(), "đã bỏ theo dõi truyện", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void savetruyen(String idTruyen) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int id_user = sharedPreferences.getInt("id_user", -1);
        if (id_user == -1) return;
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
        Call<Void>call =apiService.savert(id_user,idTruyen);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}