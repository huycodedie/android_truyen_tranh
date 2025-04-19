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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private LinearLayout lay_dang_nhap,thong_tin ,linearLayout;
    private SharedPreferences sharedPreferences;

    private ImageView xoa_user, xoa_password;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toi, container, false);
        name_user = view.findViewById(R.id.name_user);
        Password = view.findViewById(R.id.Password);
        bt1 = view.findViewById(R.id.butdangnhap);
        a1 = view.findViewById(R.id.dang_nhap_dk_1);
        a2 = view.findViewById(R.id.dang_nhap_dk_2);
        ten_user = view.findViewById(R.id.ten_user);
        lay_lai_mk = view.findViewById(R.id.lay_lai_mk);
        emai = view.findViewById(R.id.email);
        lay_dang_nhap = view.findViewById(R.id.dang_nhap);
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
            String username = name_user.getText().toString().trim();
            String password = Password.getText().toString().trim();
            if (username.isEmpty()){
                name_user.setError("Vui lòng nhập tài khoản");
                name_user.requestFocus();
                return;
            }
            if (password.isEmpty()){
                Password.setError("Vui lòng nhập mật khẩu");
                Password.requestFocus();
                return;
            }
            formlogin();
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
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
        usergui request = new usergui(username,password);
        Call<usernhan> call = apiService.login(request);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess() == true){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id_user", response.body().getId());
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", response.body().getName());
                    editor.putString("email", response.body().getEmail());
                    editor.apply();

                    lay_dang_nhap.setVisibility(View.GONE);
                    thong_tin.setVisibility(View.VISIBLE);

                    BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                    navView.setSelectedItemId(R.id.navigation_trang_chu);

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
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
        if (isLoggedIn){
            String username = sharedPreferences.getString("username", "");
            String email = sharedPreferences.getString("email","");
            lay_dang_nhap.setVisibility(View.GONE);
            thong_tin.setVisibility(View.VISIBLE);
            ten_user.setText(username);
            emai.setText(email);
        }else {
            lay_dang_nhap.setVisibility(View.VISIBLE);
            thong_tin.setVisibility(View.GONE);

        }
    }



}