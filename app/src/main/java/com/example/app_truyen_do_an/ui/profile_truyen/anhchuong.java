package com.example.app_truyen_do_an.ui.profile_truyen;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.Adapter.anhAdapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.model.Truyenviewmodel;
import com.example.app_truyen_do_an.model.anh;
import com.example.app_truyen_do_an.model.truyen;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class anhchuong extends AppCompatActivity {
    private truyen chuonga;
    private String id_chuong, so_chuong, lui, tien;
    private String id_truyen;
    private ImageButton bt1, bt2;
    private anhAdapter anhAdapter;
    private RecyclerView recyclerView;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anh);
        id_chuong = getIntent().getStringExtra("id_chuong");
        so_chuong = getIntent().getStringExtra("chuong");
        id_truyen = getIntent().getStringExtra("id_truyen");
        bt2 = findViewById(R.id.btn_right);
        bt1 = findViewById(R.id.btn_left);

        lui = String.valueOf(Integer.parseInt(so_chuong) - 1) ;
        tien = String.valueOf(Integer.parseInt(so_chuong) + 1) ;
        bt2.setOnClickListener(v -> {

        });
        recyclerView = findViewById(R.id.recview_anh);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        if (so_chuong != null  && id_truyen != null){
            getanh(so_chuong,id_truyen);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("chap: "+so_chuong);
        actionBar.setDisplayHomeAsUpEnabled(true);

        layttchuong(id_truyen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void layttchuong(String id) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getChiTietTruyen1(id);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() != null){
                    chuonga = response.body().get(0);
                    xulychuong();

                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {
                Toast.makeText(anhchuong.this, "Lỗi tải dữ liệu id", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void xulychuong() {
        boolean coChuongleft = false;
        boolean coChuongright = false;
        for (Chuong chuonga : chuonga.getChuong()) {
            if (chuonga.getso_Chuong().equals(lui)) {
                coChuongleft = true;
                break;
            }
        }
        for (Chuong chuonga : chuonga.getChuong()) {
            if (chuonga.getso_Chuong().equals(tien)) {
                coChuongright = true;
                break;
            }
        }
        if (coChuongleft) {
            bt1.setVisibility(VISIBLE);
        }
        if (coChuongright) {
            bt2.setVisibility(VISIBLE);
        }
        bt2.setOnClickListener(v -> {
            Intent intent = new Intent(this, anhchuong.class);
            intent.putExtra("id_truyen", id_truyen);
            intent.putExtra("chuong", tien);
            startActivity(intent);
        });
        bt1.setOnClickListener(v -> {
            Intent intent = new Intent(this, anhchuong.class);
            intent.putExtra("id_truyen", id_truyen);
            intent.putExtra("chuong", lui);
            startActivity(intent);
        });
    }

    private void getanh(String so_chuong, String idtruyen) {
        SharedPreferences sharedPreferences = getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int id_user = sharedPreferences.getInt("id_user", -1);
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<anh>> call = apiService.getanh(so_chuong,id_user,idtruyen);
        call.enqueue(new Callback<List<anh>>() {
            @Override
            public void onResponse(Call<List<anh>> call, Response<List<anh>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<anh> list = response.body();
                    anhAdapter = new anhAdapter(list, anhchuong.this);
                    recyclerView.setAdapter(anhAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<anh>> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(anhchuong.this, thong_tin_truyen.class);
            intent.putExtra("id_truyen", id_truyen);
            intent.putExtra("name_truyen", chuonga.getName_truyen());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}