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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private EditText name_tai_khoan_dang_nhap, Password;
    private TextView ten_user, emai, dang_ky, lay_lai_mk;
    private Button dang_nhap;
    private LinearLayout layout_dang_nhap,layout_thong_tin ,dang_xuat, thong_tin_tai_khoan, doi_mat_khau, thong_tin_user;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private ImageView xoa_tai_khoan_dang_nhap, xoa_password, anh_tai_khoan;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toi, container, false);
        //trước khi đăng nhập
        name_tai_khoan_dang_nhap = view.findViewById(R.id.name_tai_khoan_dang_nhap);
        Password = view.findViewById(R.id.nhap_Password);
        dang_nhap = view.findViewById(R.id.butdangnhap);
        progressBar = view.findViewById(R.id.progressBar);
        lay_lai_mk = view.findViewById(R.id.lay_lai_mk);

        //sau khi đăng nhâp
        dang_ky = view.findViewById(R.id.dang_ky);
        anh_tai_khoan = view.findViewById(R.id.anh_tai_khoan);
        thong_tin_user = view.findViewById(R.id.thong_tin_user);
        ten_user = view.findViewById(R.id.ten_user);
        emai = view.findViewById(R.id.email);
        layout_dang_nhap = view.findViewById(R.id.layout_dang_nhap);
        doi_mat_khau = view.findViewById(R.id.doi_mat_khau);
        thong_tin_tai_khoan = view.findViewById(R.id.thong_tin_tai_khoan);
        layout_thong_tin = view.findViewById(R.id.layout_thong_tin);
        xoa_password = view.findViewById(R.id.xoa_password);
        xoa_tai_khoan_dang_nhap = view.findViewById(R.id.xoa_tai_khoan_dang_nhap);
        dang_xuat = view.findViewById(R.id.dang_xuat);

        // dùng để lưu dữ liệu android
        sharedPreferences = requireActivity().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        checklogin();


        name_tai_khoan_dang_nhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    xoa_tai_khoan_dang_nhap.setVisibility(View.VISIBLE);
                }else {
                    xoa_tai_khoan_dang_nhap.setVisibility(View.GONE);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    xoa_password.setVisibility(View.VISIBLE);
                }else {
                    xoa_password.setVisibility(View.GONE);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        //đăng xuất tài khoản
        dang_xuat.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            layout_dang_nhap.setVisibility(View.VISIBLE);
            layout_thong_tin.setVisibility(View.GONE);
        });
        // lấy lại mật khẩu hiện tại chưa có tác dụng gì chỉ có tác dụng mở link
        lay_lai_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://t.lixitet.top";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // hiệu ứng button khi nhấn đăng nhập và chạy dữ liệu vào formlogin để kiểm tra dữ liệu tài khoản và mật khẩu
        //và trả dữ liệu
        dang_nhap.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            dang_nhap.setVisibility(View.GONE);
            String username = name_tai_khoan_dang_nhap.getText().toString().trim();
            String password = Password.getText().toString().trim();
            if (username.isEmpty()){
                name_tai_khoan_dang_nhap.setError("Vui lòng nhập tài khoản");
                name_tai_khoan_dang_nhap.requestFocus();
                progressBar.setVisibility(View.GONE);
                dang_nhap.setVisibility(View.VISIBLE);
                return;
            }
            if (password.isEmpty()){
                Password.setError("Vui lòng nhập mật khẩu");
                Password.requestFocus();
                progressBar.setVisibility(View.GONE);
                dang_nhap.setVisibility(View.VISIBLE);
                return;
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    formlogin();
                }
            }, 1000);
        });
        // set chuyển trang khác
        dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), dangky.class);
                startActivity(intent);
            }
        });
        //set xóa dữ liệu text
        xoa_tai_khoan_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_tai_khoan_dang_nhap.setText("");
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

    //chạy formlogin gửi dữ liệu vào api trả về dữ liệu và lưu dữ liệu vào SharedPreferences
    private void formlogin() {
        String username = name_tai_khoan_dang_nhap.getText().toString().trim();
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
                    layout_dang_nhap.setVisibility(View.GONE);
                    layout_thong_tin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    dang_nhap.setVisibility(View.VISIBLE);
                    BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                    navView.setSelectedItemId(R.id.navigation_trang_chu);
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    dang_nhap.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<usernhan> call, Throwable t) {
                Toast.makeText(getContext(), "loi api", Toast.LENGTH_LONG).show();
            }
        });
    }
    // xuất dữ liệu ra màn hình lấy dữ liệu đã đực lưu vào SharedPreferences
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
            if (quyen==2){
                thong_tin_user.setVisibility(View.VISIBLE);
            }
            layout_dang_nhap.setVisibility(View.GONE);
            layout_thong_tin.setVisibility(View.VISIBLE);
            if (name == null || name.isEmpty() || name.equals("null")){
                ten_user.setText(username);
            }else {
                ten_user.setText(name);
            }
            emai.setText(email);
            Glide.with(getContext())
                    .load(anh_to)
                    .error(R.drawable.anh_truyen_moi)
                    .into(anh_tai_khoan);
        }else {
            layout_dang_nhap.setVisibility(View.VISIBLE);
            layout_thong_tin.setVisibility(View.GONE);

        }
        //set chuyển trang
        doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), doi_mk.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        //set chuyển trang
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