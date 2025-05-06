package com.example.app_truyen_do_an.ui.toi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.usernhan;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Thong_tin_tai_khoan extends AppCompatActivity {
    private ImageView anh_thong_tin;
    private int id_user;
    private Button bt1;
    private String s1,s2,s3,s4,s5;
    private EditText ten_tk,sdt_tk,email_tk;
    private TextView ngay_tao_tk;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        anh_thong_tin = findViewById(R.id.anh_thong_tin);
        bt1 = findViewById(R.id.sua_thong_tin);
        ten_tk = findViewById(R.id.ten_tk);
        sdt_tk = findViewById(R.id.sdt_tk);
        email_tk = findViewById(R.id.email_tk);
        ngay_tao_tk = findViewById(R.id.ngay_tao_tk);
        id_user = getIntent().getIntExtra("id_user",-1);
        s1 = getIntent().getStringExtra("anh");
        s2 = getIntent().getStringExtra("name");
        s3 = getIntent().getStringExtra("email");
        s4 = getIntent().getStringExtra("ngay_tao");
        s5 = getIntent().getStringExtra("sdt");

        Glide.with(Thong_tin_tai_khoan.this)
                .load(s1)
                .error(R.drawable.anh_truyen_moi)
                .into(anh_thong_tin);
        ten_tk.setText(s2);
        sdt_tk.setText(s5);
        email_tk.setText(s3);
        ngay_tao_tk.setText(s4);
        Button btn_doi_anh = findViewById(R.id.btn_doi_anh);
        btn_doi_anh.setOnClickListener(v -> {
            mothuvienanh();
        });
        bt1.setOnClickListener(v -> {
            String tk = ten_tk.getText().toString().trim();
            String sdt = sdt_tk.getText().toString().trim();
            String email = email_tk.getText().toString().trim();
            suathongtin(tk,sdt,email,id_user);
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin tài khoản"+ id_user);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void suathongtin(String tk, String sdt, String email, int id_user) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<usernhan> call = apiService.suathongtin(tk, sdt, email,id_user);
        call.enqueue(new Callback<usernhan>() {
            @Override
            public void onResponse(Call<usernhan> call, Response<usernhan> response) {
                usernhan user = response.body();
                Toast.makeText(Thong_tin_tai_khoan.this, user.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<usernhan> call, Throwable t) {

            }
        });
    }

    private void mothuvienanh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    private void uploadimage(Uri imageUri, int id_user) {
        File file = new File(getRealPathFromURI(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseBody> call = apiService.uploadImage(body,id_user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            ImageView imageView = findViewById(R.id.anh_thong_tin);
            imageView.setImageURI(imageUri);

            uploadimage(imageUri,id_user);
        }

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