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
    private EditText a1,a2,a3,a4;
    private Button bt1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangky);
        a1 = findViewById(R.id.dk_tk);
        a2 = findViewById(R.id.dk_mk);
        a3 = findViewById(R.id.dk_xn_mk);
        a4 = findViewById(R.id.dk_email);
        bt1 = findViewById(R.id.button_dk);
        bt1.setOnClickListener(v -> {
            String tk = a1.getText().toString().trim();
            String mk = a2.getText().toString().trim();
            String xnmk = a3.getText().toString().trim();
            String email = a4.getText().toString().trim();
            String specialCharPattern = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*";
            String emailxn = ".*[@.].*";

            if (tk.isEmpty()){
                a1.setError("Vui lòng nhập tài khoản");
                a1.requestFocus();
                return;
            }
            if (tk.length() <= 5 || tk.length() >= 25) {
                a1.setError("Vui lòng nhập tài khoản có độ dài từ 6 - 25 ký tự");
                a1.requestFocus();
                return;
            }
            if (tk.matches(specialCharPattern)) {
                a1.setError("Vui lòng nhập tài khoản không có ký tự đặc biệt");
                a1.requestFocus();
                return;
            }
            if (mk.isEmpty()) {
                a2.setError("Vui lòng nhập mật khẩu");
                a2.requestFocus();
                return;
            }
            if (mk.length() <= 5 || mk.length() >= 25) {
                a2.setError("Vui lòng nhập mật khẩu có độ dài từ 6 - 25 ký tự");
                a2.requestFocus();
                return;
            }
            if (mk.matches(specialCharPattern)) {
                a2.setError("Vui lòng nhập mật khẩu không có ký tự đặc biệt");
                a2.requestFocus();
                return;
            }
            if (!mk.equals(xnmk)) {
                a3.setError("Xác nhận mật khẩu không khớp");
                a3.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                a4.setError("Vui lòng nhập email");
                a4.requestFocus();
                return;
            }
            if (email.length() <= 5 || email.length() >= 25) {
                a4.setError("Vui lòng đúng định dạng");
                a4.requestFocus();
                return;
            }if (!email.matches(emailxn)) {
                a4.setError("Vui lòng đúng định dạng");
                a4.requestFocus();
                return;
            }
            dangkytk(tk,xnmk,email);

        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng ký tài khoản");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void dangkytk(String tk, String xnmk, String email) {
        ApiService apiService = RetrofitClient.getClient("https://t.lixitet.top/").create(ApiService.class);
        Call<usernhan> call = apiService.dangky(tk, xnmk, email);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                usernhan user = response.body();
                Toast.makeText(dangky.this, user.getMessage(), Toast.LENGTH_LONG).show();
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