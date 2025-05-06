package com.example.app_truyen_do_an.ui.toi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.usergui;
import com.example.app_truyen_do_an.model.usernhan;
import com.example.app_truyen_do_an.ui.tim_kiem.tim_the_loai;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class toi extends Fragment {
    private EditText name_user, Password;
    private TextView ten_user, emai,a1, a2, lay_lai_mk;
    private Button bt1;
    private LinearLayout lay_dang_nhap,thong_tin ,linearLayout, thong_tin_tai_khoan, doi_mat_khau, thong_tin_user;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private ImageView xoa_user, xoa_password, anh_toi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toi, container, false);
        name_user = view.findViewById(R.id.name_user);
        Password = view.findViewById(R.id.Password);
        bt1 = view.findViewById(R.id.butdangnhap);
        progressBar = view.findViewById(R.id.progressBar);
        a1 = view.findViewById(R.id.dang_nhap_dk_1);
        a2 = view.findViewById(R.id.dang_nhap_dk_2);
        anh_toi = view.findViewById(R.id.anh_toi);
        thong_tin_user = view.findViewById(R.id.thong_tin_user);
        ten_user = view.findViewById(R.id.ten_user);
        lay_lai_mk = view.findViewById(R.id.lay_lai_mk);
        emai = view.findViewById(R.id.email);
        lay_dang_nhap = view.findViewById(R.id.dang_nhap);
        doi_mat_khau = view.findViewById(R.id.doi_mat_khau);
        thong_tin_tai_khoan = view.findViewById(R.id.thong_tin_tai_khoan);
        thong_tin = view.findViewById(R.id.thong_tin);
        xoa_password = view.findViewById(R.id.xoa_password);
        xoa_user = view.findViewById(R.id.xoa_user);
        linearLayout = view.findViewById(R.id.dang_xuat);
        sharedPreferences = requireActivity().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        checklogin();

        linearLayout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            lay_dang_nhap.setVisibility(View.VISIBLE);
            thong_tin.setVisibility(View.GONE);
        });
        lay_lai_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://t.lixitet.top";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        bt1.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            bt1.setVisibility(View.GONE);
            String username = name_user.getText().toString().trim();
            String password = Password.getText().toString().trim();
            if (username.isEmpty()){
                name_user.setError("Vui lòng nhập tài khoản");
                name_user.requestFocus();
                progressBar.setVisibility(View.GONE);
                bt1.setVisibility(View.VISIBLE);
                return;
            }
            if (password.isEmpty()){
                Password.setError("Vui lòng nhập mật khẩu");
                Password.requestFocus();
                progressBar.setVisibility(View.GONE);
                bt1.setVisibility(View.VISIBLE);
                return;
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    formlogin();
                }
            }, 1000);
        });
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), dangky.class);
                startActivity(intent);
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), dangky.class);
                startActivity(intent);
            }
        });
        xoa_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_user.setText("");
            }
        });
        xoa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password.setText("");
            }
        });
        return view;
    }
    private void formlogin() {
        String username = name_user.getText().toString().trim();
        String password = Password.getText().toString().trim();
        ApiService apiService = RetrofitClient.getApiService();
        usergui request = new usergui(username,password);
        Call<usernhan> call = apiService.login(request);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess() == true){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id_user", response.body().getId());
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", response.body().getName_user());
                    editor.putString("name", response.body().getName());
                    editor.putString("email", response.body().getEmail());
                    editor.putString("anh", response.body().getAnh());
                    editor.putString("sdt", response.body().getSdt());
                    editor.putString("ngay_tao", response.body().getNgay_tao());
                    editor.putInt("quyen",response.body().getQuyen());
                    editor.apply();
                    lay_dang_nhap.setVisibility(View.GONE);
                    thong_tin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    bt1.setVisibility(View.VISIBLE);
                    BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                    navView.setSelectedItemId(R.id.navigation_trang_chu);
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    bt1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<usernhan> call, Throwable t) {
                Toast.makeText(getContext(), "loi api", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checklogin() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        String username = sharedPreferences.getString("username", "");
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email","");
        String anh_to = sharedPreferences.getString("anh","");
        int quyen = sharedPreferences.getInt("quyen",-1);
        int id_user = sharedPreferences.getInt("id_user",-1);
        String sdt = sharedPreferences.getString("sdt","");
        String ngay_tao = sharedPreferences.getString("ngay_tao","");
        if (isLoggedIn){
            if (quyen==3){
                thong_tin_user.setVisibility(View.VISIBLE);
            }
            lay_dang_nhap.setVisibility(View.GONE);
            thong_tin.setVisibility(View.VISIBLE);
            if (name == null || name.isEmpty() || name.equals("null")){
                ten_user.setText(username);
            }else {
                ten_user.setText(name);
            }
            emai.setText(email);
            Glide.with(getContext())
                    .load(anh_to)
                    .error(R.drawable.anh_truyen_moi)
                    .into(anh_toi);
        }else {
            lay_dang_nhap.setVisibility(View.VISIBLE);
            thong_tin.setVisibility(View.GONE);

        }
        doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), doi_mk.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        thong_tin_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Thong_tin_tai_khoan.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("anh",anh_to);
                intent.putExtra("sdt",sdt);
                intent.putExtra("ngay_tao",ngay_tao);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

    }



}