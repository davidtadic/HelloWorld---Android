package com.example.david.helloworld.retrofit;

import com.example.david.helloworld.models.user.UserLoginModel;
import com.example.david.helloworld.models.user.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by david on 25.1.2018..
 */

public interface APIEndpoints {

    @POST("User/Login/")
    Call<String> login(@Body UserLoginModel userLogin);

    @POST("User/Register/")
    Call<UserModel> register(@Body UserModel userModel);

    @POST("User/UpdateUserProfile/")
    Call<String> updateUserProfile(@Body UserModel userModel);

}
