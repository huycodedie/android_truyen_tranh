package com.example.app_truyen_do_an.ui.profile_truyen;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_truyen_do_an.Adapter.viewthongtinAdapter;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.model.Truyenviewmodel;
import com.example.app_truyen_do_an.model.truyen;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class thong_tin_truyen extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String idtruyen;
    private String name_truyen;

    private Truyenviewmodel viewmodel;
    private Button dau, cuoi;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private final String[] tabTitles = {"giới thiệu","char","bình luận"};
    private final int[] tabIcons = {R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_truyen);

        //view chuyển dữ liệu qua các fragment
        viewmodel = new ViewModelProvider(this).get(Truyenviewmodel.class);
        dau = findViewById(R.id.doc_tu_dau);
        cuoi = findViewById(R.id.doc_tiep);

        idtruyen = getIntent().getStringExtra("id_truyen");
        name_truyen = getIntent().getStringExtra("name_truyen");

        sharedPreferences = getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int iduser = sharedPreferences.getInt("id_user",-1);
        if (idtruyen != null && iduser != -1){
            getTruyenchitiet(idtruyen,iduser);
        } else if (idtruyen != null) {
            getTruyenchitiet1(idtruyen);
        }
        //thanh activatibar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name_truyen);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Truyenviewmodel viewmodel = new ViewModelProvider(this).get(Truyenviewmodel.class);

        dau.setOnClickListener(v -> {
            String soChuongDau = String.valueOf(Integer.parseInt(viewmodel.gettruyenchitiet().getValue().getChuong().get(0).getso_Chuong()));

            Intent intent = new Intent(v.getContext(), anhchuong.class);
            intent.putExtra("id_truyen", idtruyen);
            intent.putExtra("chuong", soChuongDau);
            startActivity(intent);
        });
        cuoi.setOnClickListener(v -> {
            if (iduser == -1){
                Toast.makeText(v.getContext(), "vui lòng đăng nhập để lưu thông tin đã đọc", Toast.LENGTH_LONG).show();
                return;
            }
            doctiep(iduser,idtruyen);

        });

        //
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new viewthongtinAdapter(this));


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            // Set Custom View cho từng Tab
            View view = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            ImageView tabIcon = view.findViewById(R.id.tab_icon);
            TextView tabText = view.findViewById(R.id.tab_text);

            tabIcon.setImageResource(tabIcons[position]);
            tabText.setText(tabTitles[position]);

            tab.setCustomView(view);
        }).attach();
    }
    private void doctiep(int iduser, String idtruyen) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Chuong>> call = apiService.getlaychuongdadoc(iduser,idtruyen);
        call.enqueue(new Callback<List<Chuong>>() {
            @Override
            public void onResponse(Call<List<Chuong>> call, Response<List<Chuong>> response) {
                Intent intent = new Intent(thong_tin_truyen.this, anhchuong.class);
                intent.putExtra("id_truyen", idtruyen);
                intent.putExtra("chuong", response.body().get(0).getso_Chuong());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Chuong>> call, Throwable t) {

            }
        });

    }

    private void getTruyenchitiet(String id, int iduser) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getChiTietTruyen(id,iduser);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() != null){
                    truyen truyen = response.body().get(0);

                    viewmodel.setTruyenchitiet(truyen);

                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {
                Toast.makeText(thong_tin_truyen.this, "Lỗi tải dữ liệu id" + idtruyen, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getTruyenchitiet1(String id) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getChiTietTruyen1(id);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() != null){
                    truyen truyen = response.body().get(0);

                    viewmodel.setTruyenchitiet(truyen);

                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {
                Toast.makeText(thong_tin_truyen.this, "Lỗi tải dữ liệu id" + idtruyen, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // nút trở lại của thanh bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}