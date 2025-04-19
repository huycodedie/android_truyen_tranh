package com.example.app_truyen_do_an.ui.tim_kiem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

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

public class tim_ten_truyen extends AppCompatActivity {
    private EditText nhap_ten;
    private ImageView xoa;
    private RecyclerView recyclerView;
    private timtheloaiadapter timtheloaiadapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_ten_truyen);
        nhap_ten = findViewById(R.id.nhap_ten);
        xoa = findViewById(R.id.xoa);

        recyclerView= findViewById(R.id.view_ten);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhap_ten.setText("");
            }
        });
        nhap_ten.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                gettimtentruyen();
                return true;
            }
            return false;
        });
        gettimtentruyen();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tìm truyện");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void gettimtentruyen() {
        String nhapten = nhap_ten.getText().toString().trim();
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
        Call<List<truyen>> call = apiService.getTimKiemtentruyen(nhapten);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                List<truyen> list = response.body();
                timtheloaiadapter = new timtheloaiadapter(list,tim_ten_truyen.this);
                recyclerView.setAdapter(timtheloaiadapter);
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {

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