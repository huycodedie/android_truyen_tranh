package com.example.app_truyen_do_an.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_truyen_do_an.ui.profile_truyen.binh_luan;
import com.example.app_truyen_do_an.ui.profile_truyen.charter;
import com.example.app_truyen_do_an.ui.profile_truyen.gioi_thieu;

public class viewthongtinAdapter extends FragmentStateAdapter {
    public viewthongtinAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new gioi_thieu();
            case 1:
                return new charter();
            case 2:
                return new binh_luan();
            default:
                return new gioi_thieu();
        }
    }
    @Override
    public int getItemCount(){
        return 3;
    }
}
