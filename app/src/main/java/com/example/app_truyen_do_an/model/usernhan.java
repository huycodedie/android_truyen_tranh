package com.example.app_truyen_do_an.model;

import retrofit2.http.PUT;

public class usernhan {
    private  String message;
    private boolean success;
    private int id;
    private String anh;
    private String name;
    private String name_user;
    private String email;
    private String sdt;
    private String ngay_tao;
    private int quyen;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() { return success; }

    public int getId() {
        return id;
    }

    public String getAnh() {
        return anh;
    }

    public int getQuyen() {
        return quyen;
    }

    public String getNgay_tao() {
        return ngay_tao;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSdt() {
        return sdt;
    }

    public String getName_user() {
        return name_user;
    }
}
