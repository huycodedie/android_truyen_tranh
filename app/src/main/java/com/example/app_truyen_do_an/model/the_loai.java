package com.example.app_truyen_do_an.model;

public class the_loai {
    private String id_tl;
    private String the_loai;
    public the_loai(String id_tl,String the_loai){
        this.id_tl = id_tl;
        this.the_loai = the_loai;
    }
    public String getId_tl(){
        return id_tl;
    }
    public String getThe_loai(){
        return the_loai;
    }
}
