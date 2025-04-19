package com.example.app_truyen_do_an.model;

import java.util.List;

public class Chuong {
    private String id_chuong;
    private String so_chuong;
    private String tieu_de;
    private String thoi_gian;
    private List<anh> anh;

    public Chuong(String id_chuong, String so_chuong, String tieu_de, String thoi_gian){
        this.id_chuong = id_chuong;
        this.so_chuong = so_chuong;
        this.tieu_de = tieu_de;
        this.thoi_gian = thoi_gian;
    }

    public String getId_chuong() {
        return id_chuong;
    }

    public String getso_Chuong() {
        return so_chuong;
    }

    public String getTieu_de() {
        return tieu_de;
    }

    public String getThoi_gian_chuong() {
        return thoi_gian;
    }

    public List<anh> getAnh() {
        return anh;
    }
}
