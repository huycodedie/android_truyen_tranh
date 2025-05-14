package com.example.app_truyen_do_an.ui.toi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.usernhan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class doi_mk extends AppCompatActivity {
    private EditText mk_cu,mk_moi,nhap_lai_mk;
    private int id_user;
    private String name;
    private Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doi_mk);
        id_user = getIntent().getIntExtra("id_user",-1);
        name = getIntent().getStringExtra("name");
        mk_cu= findViewById(R.id.mk_cu);
        mk_moi = findViewById(R.id.mk_moi);
        nhap_lai_mk = findViewById(R.id.nhap_lai_mk);
        bt1 =findViewById(R.id.xn_doi_mk);

        bt1.setOnClickListener(v -> {
            String mk_cu_1 = mk_cu.getText().toString().trim();
            String mk_moi_1 = mk_moi.getText().toString().trim();
            String xn_mk_1 = nhap_lai_mk.getText().toString().trim();
            if (mk_cu_1.isEmpty()){
                mk_cu.setError("Vui lòng nhập mật khẩu cũ");
                mk_cu.requestFocus();
                return;
            }if (mk_cu_1.length()<=5 || mk_cu_1.length()>=25){
                mk_cu.setError("Vui lòng nhập mật khẩu có độ dài từ 6 - 25 ký tự");
                mk_cu.requestFocus();
                return;
            }if (mk_moi_1.isEmpty()){
                mk_moi.setError("Vui lòng nhập mật khẩu mới");
                mk_moi.requestFocus();
                return;
            }if (mk_moi_1.length()<=5 || mk_moi_1.length()>=25){
                mk_moi.setError("Vui lòng nhập mật khẩu có độ dài từ 6 - 25 ký tự");
                mk_moi.requestFocus();
                return;
            }if (!mk_moi_1.equals(xn_mk_1)) {
                nhap_lai_mk.setError("Xác nhận mật khẩu không khớp");
                nhap_lai_mk.requestFocus();
                return;
            }if (mk_cu_1.equals(mk_moi_1)) {
                mk_moi.setError("Mật khẩu cũ và mới không được giống nhau");
                mk_moi.requestFocus();
                return;
            }
            doimkmoi(mk_cu_1,xn_mk_1,id_user);
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đổi mật khẩu"+ id_user+name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void doimkmoi(String mk_cu, String xn_mk, int id_user) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<usernhan> call = apiService.doi_mat_khau(mk_cu,xn_mk,id_user);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                usernhan user = response.body();
                Toast.makeText(doi_mk.this, user.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<usernhan> call, Throwable t) {

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