package com.example.app_truyen_do_an.model;

import java.util.List;

public class truyen {
    private String id_truyen;
    private String name_truyen;
    private String name_tac_gia;
    private String name_tac_gia_t;
    private String anh;
    private String anh_tac_gia;
    private String luot_xem;
    private int trang_thai;
    private String gioi_thieu;
    private String ngay_dang;
    private String tong_chuong;
    private List<Chuong> chuong;
    private int luu;
    private String tong_truyen_theo_doi;
    private List<the_loai> the_loai;
    public truyen(String id_truyen, String name_truyen,
                  String name_tac_gia, String anh,String luot_xem, int trang_thai, String gioi_thieu,String tong_chuong, String ngay_dang, List<String> the_loai, int luu){
        this.id_truyen = id_truyen;
        this.name_truyen = name_truyen;
        this.name_tac_gia = name_tac_gia;
        this.anh = anh;
        this.luot_xem = luot_xem;
        this.trang_thai = trang_thai;
        this.gioi_thieu = gioi_thieu;
        this.ngay_dang = ngay_dang;
        this.tong_chuong = tong_chuong;

    }

    public String getId_truyen() {
        return id_truyen;
    }

    public String getTong_truyen_theo_doi() {
        return tong_truyen_theo_doi;
    }

    public String getName_truyen() {
        return name_truyen;
    }

    public String getName_tac_gia() {
        return name_tac_gia;
    }

    public String getAnh() {
        return anh;
    }

    public String getAnh_tac_gia() {
        return anh_tac_gia;
    }

    public String getName_tac_gia_t() {
        return name_tac_gia_t;
    }

    public String getLuot_xem() {
        return luot_xem;
    }

    public String getTong_chuong() {
        return tong_chuong;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public String getGioi_thieu() {
        return gioi_thieu;
    }

    public String getNgay_dang() {
        return ngay_dang;
    }

    public List<Chuong> getChuong(){
        return chuong;
    }
    public void setChuong(List<Chuong> chuongList) {
        this.chuong = chuongList;
    }

    public List<the_loai> getThe_loai() {
        return the_loai;
    }

    public int getLuu() {
        return luu;
    }
}
