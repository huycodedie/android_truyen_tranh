package com.example.app_truyen_do_an.ui.da_xem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.app_truyen_do_an.Adapter.LuuAdapter;
import com.example.app_truyen_do_an.MainActivity;
import com.example.app_truyen_do_an.R;
import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.ui.tim_kiem.tim_ten_truyen;
import com.example.app_truyen_do_an.ui.toi.toi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class da_xem extends Fragment {
    private Button bt1;
    private ImageView tim_kiem;
    private SharedPreferences sharedPreferences;
    private LuuAdapter luuAdapter;
    private ApiService apiService;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout, linearLayout2;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_xem, container, false);
        recyclerView = view.findViewById(R.id.view_luu);
        linearLayout = view.findViewById(R.id.view_erro);
        linearLayout2 = view.findViewById(R.id.view_erro_2);
        bt1 = view.findViewById(R.id.chua_co_tk);
        tim_kiem = view.findViewById(R.id.tim_kiem);
        Toolbar toolbar = view.findViewById(R.id.toolbar_daxem);

        tim_kiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), tim_ten_truyen.class);
                startActivity(intent);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_toi);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        sharedPreferences = getActivity().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        int iduser = sharedPreferences.getInt("id_user",-1);
        if (iduser != -1){
            truyendaxem(iduser);
        }else {
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
        }
        return view;
    }

    private void truyendaxem(int iduser) {
        apiService = RetrofitClient.getApiService();
        Call<List<truyen>> call = apiService.getluu(iduser);
        call.enqueue(new Callback<List<truyen>>() {
            @Override
            public void onResponse(Call<List<truyen>> call, Response<List<truyen>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<truyen> danhsachluu = response.body();
                    if (danhsachluu.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);

                    } else {
                        luuAdapter = new LuuAdapter(danhsachluu, getContext());
                        recyclerView.setAdapter(luuAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<truyen>> call, Throwable t) {
                Toast.makeText(getContext(), "lôi api", Toast.LENGTH_SHORT).show();
            }
        });
    }
}