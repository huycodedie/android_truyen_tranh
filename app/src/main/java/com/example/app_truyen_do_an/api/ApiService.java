package com.example.app_truyen_do_an.api;

import com.example.app_truyen_do_an.model.anh;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.model.usergui;
import com.example.app_truyen_do_an.model.usernhan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("user.php")
    Call<List<usergui>> getusers();
    @GET("truyen.php")
    Call<List<truyen>> gettruyen();
    @GET("anhslide.php")
    Call<List<truyen>> getanhslide();
    @GET("truyen.php")
    Call<List<truyen>> getChiTietTruyen(@Query("id_truyen") String id,@Query("id_user") int iduser);
    @GET("timkiem.php")
    Call<List<truyen>> getTimKiemTheLoai(@Query("id_tl") String id);
    @POST("user.php")
    Call<usernhan> login(@Body usergui request);
    @POST("dangky.php")
    @FormUrlEncoded
    Call<usernhan> dangky(@Field("name_user") String tk,@Field("password_hash") String xnmk, @Field("email") String email);
    @POST("layluu.php")
    @FormUrlEncoded
    Call<List<truyen>> getluu(@Field("id_user") int iduser);

    @POST("luu.php")
    @FormUrlEncoded
    Call<Void> savert(@Field("id_user") int id_user, @Field("id_truyen") String idtruyen);

    @POST("themluu.php")
    @FormUrlEncoded
    Call<Void> themluu(@Field("id_user") int id_user, @Field("id_truyen") String idtruyen, @Field("so") int so);

    @GET("truyen.php")
    Call<List<truyen>> getChiTietTruyen1(@Query("id_truyen") String id);
    @GET("timkiemten.php")
    Call<List<truyen>> getTimKiemtentruyen(@Query("name_truyen") String nhapten);
    @GET("getanh.php")
    Call<List<anh>> getanh(@Query("chuong") String chuong, @Query("id_user") int id_user, @Query("id_truyen") String idtruyen);
}
