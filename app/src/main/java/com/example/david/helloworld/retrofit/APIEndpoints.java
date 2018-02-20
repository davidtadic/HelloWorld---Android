package com.example.david.helloworld.retrofit;

import com.example.david.helloworld.models.game.PractiseModel;
import com.example.david.helloworld.models.game.PractiseRequestModel;
import com.example.david.helloworld.models.game.QuestionModel;
import com.example.david.helloworld.models.user.MailModel;
import com.example.david.helloworld.models.user.UserLoginModel;
import com.example.david.helloworld.models.user.UserModel;

import java.util.ArrayList;

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

    @POST("User/SendFeedback/")
    Call<Boolean> sendFeedback(@Body MailModel mailModel);

    @POST("Game/GetQuestionsForPractise/")
    Call<ArrayList<QuestionModel>> getQuestionsForPractise(@Body PractiseRequestModel requestModel);

    @POST("Game/InsertTrainingInfo/")
    Call<Void> insertTrainingInfo(@Body PractiseModel practiseModel);
}
