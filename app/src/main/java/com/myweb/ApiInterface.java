package com.myweb;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //{"status":"sucessfully","id":"13","url":"adfront.in\/android_view\/index.php"}
    @FormUrlEncoded
    @POST("/android_view/login_api.php?")
    Call<Login> getUserlogin(@Field("username") String username, @Field("password") String password);
}
