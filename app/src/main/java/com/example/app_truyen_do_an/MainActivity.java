package com.example.app_truyen_do_an;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.app_truyen_do_an.api.ApiService;
import com.example.app_truyen_do_an.api.RetrofitClient;
import com.example.app_truyen_do_an.model.usergui;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.app_truyen_do_an.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();
    ApiService apiService;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_trang_chu, R.id.navigation_da_xem, R.id.navigation_toi)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Runnable runnable = new Runnable() {
          //  @Override
          //  public void run() {
          //      Intent intent = new Intent(MainActivity.this, thong_tin_truyen.class);
          //      startActivity(intent);
          //  }
        //};
       // handler.postDelayed(runnable,1000);



    }

}