package com.example.app_truyen_do_an.ui.trang_chu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.app_truyen_do_an.Adapter.ImageSliderAdapter;
import com.example.app_truyen_do_an.Adapter.TruyenAdapter;
import com.example.app_truyen_do_an.MainActivity;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.ui.profile_truyen.thong_tin_truyen;
import com.example.app_truyen_do_an.ui.tim_kiem.tim_ten_truyen;
import com.example.app_truyen_do_an.ui.tim_kiem.tim_the_loai;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class trang_chu extends Fragment {
    private RecyclerView recyclerView,recyclerView_manhwa;
    private TruyenAdapter truyenAdapter,truyen_ManhwaAdapter;
    private List<truyen> truyenList, truyen_manhwaList;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;
    private int i = 0;
    private NestedScrollView layout_view;
    private LinearLayout linearLayout1,tim_the_loai,layout_bao_loi;
    private ImageView tim_k, truyen_moi;
    private SwipeRefreshLayout swipeRefreshLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,@NonNull
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        recyclerView = view.findViewById(R.id.truyen_moi);
        tim_the_loai = view.findViewById(R.id.tim_the_loai);
        linearLayout1 = view.findViewById(R.id.layout_truyen_moi);
        layout_view = view.findViewById(R.id.layput_view);
        layout_bao_loi = view.findViewById(R.id.layout_bao_loi);
        swipeRefreshLayout = view.findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTruyenData();
                imageslider();
                truyenmanhwa();
            }
        });
        //truyện mới
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        truyenList = new ArrayList<>();
        truyenAdapter = new TruyenAdapter(getContext(), truyenList);
        recyclerView.setAdapter(truyenAdapter);

        //truyện manhwa
        recyclerView_manhwa = view.findViewById(R.id.truyen_Manhwa);
        recyclerView_manhwa.setLayoutManager(new GridLayoutManager(getContext(),3));
        truyen_manhwaList = new ArrayList<>();
        truyen_ManhwaAdapter = new TruyenAdapter(getContext(),truyen_manhwaList);
        recyclerView_manhwa.setAdapter(truyen_ManhwaAdapter);

        viewPager2 = view.findViewById(R.id.anh_dau);
        tim_k = view.findViewById(R.id.tim_kiem);
        truyen_moi = view.findViewById(R.id.truyen_moi_tt);


        tim_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), tim_ten_truyen.class);
                startActivity(intent);
            }
        });
        truyen_moi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), tim_the_loai.class);
                startActivity(intent);
            }
        });
        tim_the_loai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), tim_the_loai.class);
                intent.putExtra("show",1);
                startActivity(intent);
            }
        });
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), tim_the_loai.class);
                startActivity(intent);
            }
        });
        //test chuyeenr trang

        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getAdapter() != null) {
                    i = viewPager2.getCurrentItem();
                    int totalItem = viewPager2.getAdapter().getItemCount();
                    if (i < totalItem - 1) {
                        i++;
                    } else {
                        i = 0;
                    }
                    viewPager2.setCurrentItem(i, true);
                    sliderHandler.postDelayed(this, 10000);
                }
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 10000);
        // Inflate the layout for this fragment
        fetchTruyenData();
        imageslider();
        truyenmanhwa();
        return view;
    }

    private void truyenmanhwa() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getTimKiemTheLoai1(String.valueOf(24));
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if(response.isSuccessful() && response.body() != null){
                    truyen_manhwaList.clear();
                    List<truyen> allTruyen = response.body();
                    List<truyen> limitedTruyen = allTruyen.size() > 3 ? allTruyen.subList(0, 9) : allTruyen;
                    truyen_manhwaList.addAll(limitedTruyen);
                    truyen_ManhwaAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {

            }
        });
    }

    private void imageslider() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getanhslide();
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() !=  null){
                    List<truyen> list = response.body();
                    ImageSliderAdapter adapter = new ImageSliderAdapter(list, getContext());
                    viewPager2.setAdapter(adapter);
                    layout_bao_loi.setVisibility(View.GONE);
                    layout_view.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }else {
                    layout_bao_loi.setVisibility(View.VISIBLE);
                    layout_view.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {

            }
        });
    }

    private void fetchTruyenData() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.gettruyen();
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if(response.isSuccessful() && response.body() != null){
                    truyenList.clear();
                    List<truyen> allTruyen = response.body();
                    List<truyen> limitedTruyen = allTruyen.size() > 3 ? allTruyen.subList(0, 9) : allTruyen;
                    truyenList.addAll(limitedTruyen);
                    truyenAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {

            }
        });
    }
}