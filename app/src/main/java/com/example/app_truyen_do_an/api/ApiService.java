package com.example.app_truyen_do_an.api;

import com.example.app_truyen_do_an.model.Chuong;
import com.example.app_truyen_do_an.model.anh;
import com.example.app_truyen_do_an.model.the_loai;
import com.example.app_truyen_do_an.model.truyen;
import com.example.app_truyen_do_an.model.usergui;
import com.example.app_truyen_do_an.model.usernhan;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @GET("truyen/truyen.php")
    Call<List<truyen>> gettruyen();
    @GET("truyen/anhslide.php")
    Call<List<truyen>> getanhslide();
    @GET("truyen/truyen.php")
    Call<List<truyen>> getChiTietTruyen(@Query("id_truyen") String id,@Query("id_user") int iduser);
    @GET("tim_kiem/timkiem.php")
    Call<List<truyen>> getTimKiemTheLoai1(@Query("id_tl") String id);
    @GET("tim_kiem/timkiem.php")
    Call<List<truyen>> getTimKiemTheLoai(@Query("id_tl") List<String> id);
    @POST("user/user.php")
    Call<usernhan> login(@Body usergui request);
    @POST("user/dangky.php")
    @FormUrlEncoded
    Call<usernhan> dangky(@Field("name_user") String tk,@Field("password_hash") String xnmk, @Field("email") String email);
    @POST("su_ly_tac_vu/layluu.php")
    @FormUrlEncoded
    Call<List<truyen>> getluu(@Field("id_user") int iduser);
    @POST("su_ly_tac_vu/luu.php")
    @FormUrlEncoded
    Call<Void> savert(@Field("id_user") int id_user, @Field("id_truyen") String idtruyen);
    @POST("su_ly_tac_vu/themluu.php")
    @FormUrlEncoded
    Call<Void> themluu(@Field("id_user") int id_user, @Field("id_truyen") String idtruyen, @Field("so") int so);
    @GET("truyen/truyen.php")
    Call<List<truyen>> getChiTietTruyen1(@Query("id_truyen") String id);
    @GET("tim_kiem/timkiemten.php")
    Call<List<truyen>> getTimKiemtentruyen(@Query("name_truyen") String nhapten);
    @GET("su_ly_tac_vu/getanh.php")
    Call<List<anh>> getanh(@Query("chuong") String chuong, @Query("id_user") int id_user, @Query("id_truyen") String idtruyen);
    @POST("truyen/laychuongdadoc.php")
    @FormUrlEncoded
    Call<List<Chuong>> getlaychuongdadoc(@Field("id_user") int id_user, @Field("id_truyen") String idtruyen);
    @POST("user/sua_thong_tin.php")
    @FormUrlEncoded
    Call<usernhan> suathongtin(@Field("name") String tk,@Field("sdt") String sdt, @Field("email") String email,@Field("id_user") int id_user);
    @POST("user/doi_mat_khau.php")
    @FormUrlEncoded
    Call<usernhan> doi_mat_khau(@Field("mat_khau_cu") String mk_cu,@Field("mat_khau_moi") String xn_mk,@Field("id_user") int id_user);
    @Multipart
    @POST("su_ly_tac_vu/uploadanh.php") // hoặc đường dẫn upload ảnh
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image, @Part("id_user") int id_user);

    @GET("truyen/the_loai.php")
    Call<List<the_loai>> gettheloai();
}
