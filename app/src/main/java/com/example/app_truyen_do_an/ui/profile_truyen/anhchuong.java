package com.example.app_truyen_do_an.ui.profile_truyen;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.Adapter.anhAdapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.model.anh;
import com.example.app_truyen_do_an.model.truyen;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class anhchuong extends AppCompatActivity {
    private truyen chuonga;
    private String  truoc,sau;
    private String so_chuong;
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
        so_chuong = getIntent().getStringExtra("chuong");
        id_truyen = getIntent().getStringExtra("id_truyen");
        bt2 = findViewById(R.id.btn_right);
        bt1 = findViewById(R.id.btn_left);

        recyclerView = findViewById(R.id.recview_anh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (so_chuong != null  && id_truyen != null){
            getanh(so_chuong,id_truyen);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("chap: "+so_chuong);
        actionBar.setDisplayHomeAsUpEnabled(true);

        layttchuong(id_truyen,so_chuong);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void layttchuong(String id, String so_chuong) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getChiTietTruyen1(id);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() != null){
                    chuonga = response.body().get(0);
                    xulychuong(so_chuong);

                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {
                Toast.makeText(anhchuong.this, "Lỗi tải dữ liệu id", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Chuong xulychuong(String so_chuong) {
        Chuong chuongTruoc = null;
        Chuong chuongSau = null;
        try {
            float soHienTai = Float.parseFloat(so_chuong);
            float maxThoaMan = Float.NEGATIVE_INFINITY;

            for (Chuong chuong : chuonga.getChuong()) {
                float soDangXet = Float.parseFloat(chuong.getso_Chuong());

                if (soDangXet < soHienTai && soDangXet > maxThoaMan) {
                    maxThoaMan = soDangXet;
                    chuongTruoc = chuong;
                    truoc = chuongTruoc.getso_Chuong();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            float soHienTai = Float.parseFloat(so_chuong);
            float minThoaMan = Float.POSITIVE_INFINITY;

            for (Chuong chuong : chuonga.getChuong()) {
                float soDangXet = Float.parseFloat(chuong.getso_Chuong());

                if (soDangXet > soHienTai && soDangXet < minThoaMan) {
                    minThoaMan = soDangXet;
                    chuongSau = chuong;
                    sau = chuongSau.getso_Chuong();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (chuongTruoc!=null) {
            bt1.setVisibility(VISIBLE);
        }
        if (chuongSau!=null) {
            bt2.setVisibility(VISIBLE);
        }
        bt1.setOnClickListener(v -> {
            Intent intent = new Intent(this, anhchuong.class);
            intent.putExtra("id_truyen", id_truyen);
            intent.putExtra("chuong",truoc);
            startActivity(intent);
        });
        bt2.setOnClickListener(v -> {
            Intent intent = new Intent(this, anhchuong.class);
            intent.putExtra("id_truyen", id_truyen);
            intent.putExtra("chuong",sau);
            startActivity(intent);
        });
        return chuongTruoc;

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