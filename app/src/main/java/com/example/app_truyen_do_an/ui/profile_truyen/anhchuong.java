package com.example.app_truyen_do_an.ui.profile_truyen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.Adapter.anhAdapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.anh;
import com.example.app_truyen_do_an.model.truyen;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class anhchuong extends AppCompatActivity {
    private String id_chuong, so_chuong;
    private String id_truyen;
    private anhAdapter anhAdapter;
    private RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anh);
        id_chuong = getIntent().getStringExtra("id_chuong");
        so_chuong = getIntent().getStringExtra("chuong");
        id_truyen = getIntent().getStringExtra("id_truyen");
        recyclerView = findViewById(R.id.recview_anh);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        if (so_chuong != null && id_chuong != null && id_truyen != null){
            getanh(so_chuong,id_chuong,id_truyen);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("chap: "+so_chuong+id_chuong+id_truyen);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void getanh(String so_chuong, String id_chuong, String idtruyen) {
        SharedPreferences sharedPreferences = getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int id_user = sharedPreferences.getInt("id_user", -1);
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
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
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}