package com.example.app_truyen_do_an.ui.profile_truyen;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.example.app_truyen_do_an.model.usernhan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class gioi_thieu extends Fragment {
    private TextView gioi_thieu_tt, name_tac_gia_tt, ten_truyen_tt, luot_xem, tien_do, tong_theo_doi, tong_chuong;
    private ImageView anh_truyen_tt, anh_tac_gia, theo_doi,admin_xoa;
    private RecyclerView recyclerView;
    private int so1 = 1, so0 = 0;
    private SharedPreferences sharedPreferences;
    private TheloaiAdapter theloaiAdapter;
    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gioi_thieu,container, false);
        admin_xoa = view.findViewById(R.id.xoa_tt);
        tien_do = view.findViewById(R.id.tien_do);
        ten_truyen_tt = view.findViewById(R.id.ten_truyen_tt);
        name_tac_gia_tt = view.findViewById(R.id.name_tac_gia_tt);
        gioi_thieu_tt = view.findViewById(R.id.gioi_thieu_tt);
        anh_truyen_tt = view.findViewById(R.id.anh_truyen_tt);
        anh_tac_gia = view.findViewById(R.id.anh_tac_gia);
        theo_doi = view.findViewById(R.id.theo_doi_truyen);
        luot_xem = view.findViewById(R.id.luot_xem);
        tong_theo_doi = view.findViewById(R.id.tong_theo_doi);
        tong_chuong = view.findViewById(R.id.tong_chuong);
        recyclerView = view.findViewById(R.id.view_the_loai);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        sharedPreferences = requireActivity().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int quyen = sharedPreferences.getInt("quyen",-1);
        if (quyen == 2){
            admin_xoa.setVisibility(View.VISIBLE);
        }
        Truyenviewmodel viewmodel = new ViewModelProvider(requireActivity()).get(Truyenviewmodel.class);
        viewmodel.gettruyenchitiet().observe(getViewLifecycleOwner(), truyen -> {
            ten_truyen_tt.setText(truyen.getName_truyen());
            if (truyen.getName_tac_gia_t() != null){
                name_tac_gia_tt.setText(truyen.getName_tac_gia_t());
            }else {
                name_tac_gia_tt.setText(truyen.getName_tac_gia());
            }
            gioi_thieu_tt.setText(truyen.getGioi_thieu());
            luot_xem.setText(truyen.getLuot_xem());
            tong_theo_doi.setText(truyen.getTong_truyen_theo_doi());
            tong_chuong.setText(truyen.getTong_chuong());
            Glide.with(getContext())
                    .load(truyen.getAnh())
                    .into(anh_truyen_tt);
            Glide.with(getContext())
                    .load(truyen.getAnh_tac_gia())
                    .into(anh_tac_gia);
            if (truyen != null && truyen.getThe_loai() != null){
                theloaiAdapter = new TheloaiAdapter(truyen.getThe_loai(),getContext());
                recyclerView.setAdapter(theloaiAdapter);
            }
            if(truyen.getTrang_thai() == 1){
                tien_do.setText("Đang tiến hành");
            } else if (truyen.getTrang_thai()==2) {
                tien_do.setText("Đã hoàn thành");
            }else {
                tien_do.setText("Sắp ra mắt");
            }
            if (truyen.getLuu() == 0){
                theo_doi.setImageTintList(ContextCompat.getColorStateList(requireContext(),R.color.gray));
            }else {
                theo_doi.setImageTintList(ContextCompat.getColorStateList(requireContext(),R.color.orange));
            }
            admin_xoa.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa truyện này không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            xoa_truyen(truyen.getId_truyen()); // Gọi hàm xóa nếu xác nhận
                        })
                        .setNegativeButton("Hủy", null) // Không làm gì nếu hủy
                        .show();
            });

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

    private void xoa_truyen(String idTruyen) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<usernhan>call = apiService.xoa_truyen(idTruyen);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<usernhan> call, Throwable t) {

            }
        });
    }

    private void luutruyen(String idTruyen, int so) {
        int id_user = sharedPreferences.getInt("id_user", -1);
        if (id_user == -1) {
            Toast.makeText(getContext(), "vui lòng đăng nhập để theo dõi truyện", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService apiService = RetrofitClient.getApiService();
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
        ApiService apiService = RetrofitClient.getApiService();
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