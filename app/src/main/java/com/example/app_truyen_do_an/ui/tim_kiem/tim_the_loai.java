package com.example.app_truyen_do_an.ui.tim_kiem;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.Adapter.timtheloaiadapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.truyen;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tim_the_loai extends AppCompatActivity {
    private timtheloaiadapter timtheloaiadapter;
    private RecyclerView recyclerView;
    private String id_tl;
    private String name_tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_the_loai);
        id_tl = getIntent().getStringExtra("id_tl");
        name_tl = getIntent().getStringExtra("name_tl");
        recyclerView = findViewById(R.id.view_tim_kiem_the_loai);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        ActionBar actionBar = getSupportActionBar();
        if(id_tl != null){
            getTimKiemTheLoai(id_tl);
            actionBar.setTitle("Thể loại: "+name_tl);

        }else {
            getTimKiemTheLoai(null);
            actionBar.setTitle("Hiển thị");
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getTimKiemTheLoai(String id) {
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
        Call<List<truyen>> call = apiService.getTimKiemTheLoai(id);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<truyen> list = response.body();
                    timtheloaiadapter = new timtheloaiadapter(list,tim_the_loai.this);
                    recyclerView.setAdapter(timtheloaiadapter);
                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {
                Toast.makeText(tim_the_loai.this, "lỗi dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}