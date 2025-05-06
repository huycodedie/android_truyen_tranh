package com.example.app_truyen_do_an.ui.tim_kiem;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_truyen_do_an.Adapter.TheloaiAdapter;
import com.example.app_truyen_do_an.Adapter.TheloaitimAdapter;
import com.example.app_truyen_do_an.Adapter.timtheloaiadapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.the_loai;
import com.example.app_truyen_do_an.model.truyen;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tim_the_loai extends AppCompatActivity {
    private timtheloaiadapter timtheloaiadapter;
    private TheloaitimAdapter theloaitimAdapter;
    private RecyclerView recyclerView, recyclerView2;
    private String id_tl;
    private ImageView t1,t2,t3;
    private TextView tx1;
    private int show;
    private Button bt1;
    private String name_tl;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_the_loai);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar_tim_tl);
        setSupportActionBar(toolbar);
        t1 = findViewById(R.id.icon_loc);
        t2 = findViewById(R.id.icon_xap_sep_1);
        t3 = findViewById(R.id.icon_xap_sep_2);
        tx1 = findViewById(R.id.text_bar_tim_tl);
        id_tl = getIntent().getStringExtra("id_tl");
        show = getIntent().getIntExtra("show",0);
        name_tl = getIntent().getStringExtra("name_tl");
        recyclerView = findViewById(R.id.view_tim_kiem_the_loai);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.fragment_chon_thong_du_lieu, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bt1 = bottomSheetView.findViewById(R.id.btn_ok);
        recyclerView2 = bottomSheetView.findViewById(R.id.recyview_the_loai);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 4));
        ArrayList<the_loai> selectedList = (ArrayList<the_loai>) getIntent().getSerializableExtra("selected_items");

        bt1.setOnClickListener(v -> {
            List<the_loai> selected = theloaitimAdapter.getSelectedItems();
            Intent intent = new Intent(this, tim_the_loai.class);
            intent.putExtra("selected_items", new ArrayList<>(selected));
            startActivity(intent);
        });

        ActionBar actionBar = getSupportActionBar();

        if(id_tl != null){
            getTimKiemTheLoai1(id_tl);
            tx1.setText("Thể loại :"+name_tl );
        } else if (show == 1) {
            getTimKiemTheLoai1(null);
            tx1.setText("Hiển thị");
            bottomSheetDialog.show();
        }else if (selectedList != null && !selectedList.isEmpty()) {
            List<String> idList = new ArrayList<>();
            StringBuilder nameBuilder = new StringBuilder();

            for (the_loai item : selectedList) {
                idList.add(item.getId_tl());
                nameBuilder.append(item.getThe_loai()).append(", ");
            }

            // Xóa dấu ',' cuối cùng nếu cần
            if (nameBuilder.length() > 0) {
                nameBuilder.setLength(nameBuilder.length() - 2);
            }

            getTimKiemTheLoai(idList);  // Giả sử bạn sửa hàm này để nhận List ID
            tx1.setText("Thể loại: " + nameBuilder.toString());
        } else {
            getTimKiemTheLoai(null);
            tx1.setText("Hiển thị");
        }

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        t1.setOnClickListener(v -> {
            bottomSheetDialog.show();
        });
        laytheloai();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getTimKiemTheLoai1(String id) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getTimKiemTheLoai1(id);
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

    private void laytheloai() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<the_loai>>call = apiService.gettheloai();
        call.enqueue(new Callback<List<the_loai>>() {
            @Override
            public void onResponse(Call<List<the_loai>> call, Response<List<the_loai>> response) {
                List<the_loai> tl = response.body();
                theloaitimAdapter  = new TheloaitimAdapter(tl, tim_the_loai.this);
                recyclerView2.setAdapter(theloaitimAdapter);
            }

            @Override
            public void onFailure(Call<List<the_loai>> call, Throwable t) {

            }
        });
    }

    private void getTimKiemTheLoai(List<String> id) {
        ApiService apiService = RetrofitClient.getApiService();
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