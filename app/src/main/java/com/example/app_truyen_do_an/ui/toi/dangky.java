package com.example.app_truyen_do_an.ui.toi;

import static java.security.AccessController.getContext;

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
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.model.usernhan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dangky extends AppCompatActivity {
    private EditText dk_tk,dk_mk,dk_xn_mk,dk_email;
    private Button button_dk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangky);
        dk_tk = findViewById(R.id.dk_tk);
        dk_mk = findViewById(R.id.dk_mk);
        dk_xn_mk = findViewById(R.id.dk_xn_mk);
        dk_email = findViewById(R.id.dk_email);
        button_dk = findViewById(R.id.button_dk);
        button_dk.setOnClickListener(v -> {
            String tk = dk_tk.getText().toString().trim();
            String mk = dk_mk.getText().toString().trim();
            String xnmk = dk_xn_mk.getText().toString().trim();
            String email = dk_email.getText().toString().trim();
            String specialCharPattern = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*";
            String emailxn = ".*[@.].*";

            if (tk.isEmpty()){
                dk_tk.setError("Vui lòng nhập tài khoản");
                dk_tk.requestFocus();
                return;
            }
            if (tk.length() <= 5 || tk.length() >= 25) {
                dk_tk.setError("Vui lòng nhập tài khoản có độ dài từ 6 - 25 ký tự");
                dk_tk.requestFocus();
                return;
            }
            if (tk.matches(specialCharPattern)) {
                dk_tk.setError("Vui lòng nhập tài khoản không có ký tự đặc biệt");
                dk_tk.requestFocus();
                return;
            }
            if (mk.isEmpty()) {
                dk_mk.setError("Vui lòng nhập mật khẩu");
                dk_mk.requestFocus();
                return;
            }
            if (mk.length() <= 5 || mk.length() >= 25) {
                dk_mk.setError("Vui lòng nhập mật khẩu có độ dài từ 6 - 25 ký tự");
                dk_mk.requestFocus();
                return;
            }
            if (mk.matches(specialCharPattern)) {
                dk_mk.setError("Vui lòng nhập mật khẩu không có ký tự đặc biệt");
                dk_mk.requestFocus();
                return;
            }
            if (!mk.equals(xnmk)) {
                dk_xn_mk.setError("Xác nhận mật khẩu không khớp");
                dk_xn_mk.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                dk_email.setError("Vui lòng nhập email");
                dk_email.requestFocus();
                return;
            }
            if (email.length() <= 5 || email.length() >= 25) {
                dk_email.setError("Vui lòng đúng định dạng");
                dk_email.requestFocus();
                return;
            }if (!email.matches(emailxn)) {
                dk_email.setError("Vui lòng đúng định dạng");
                dk_email.requestFocus();
                return;
            }
            dangkytk(tk,xnmk,email);

        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng ký tài khoản");
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    private void dangkytk(String tk, String xnmk, String email) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<usernhan> call = apiService.dangky(tk, xnmk, email);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                usernhan user = response.body();
                Toast.makeText(dangky.this, user.getMessage(), Toast.LENGTH_LONG).show();
                if (user != null) {

                    Toast.makeText(dangky.this,user.getMessage(), Toast.LENGTH_LONG).show();

                    if ("Đăng ký thành công".equals(user.getMessage())) {
                        onBackPressed();
                    }
                } else {
                    Toast.makeText(dangky.this, user.getMessage(), Toast.LENGTH_LONG).show();
                }
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