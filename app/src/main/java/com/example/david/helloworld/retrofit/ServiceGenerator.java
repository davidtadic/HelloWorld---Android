package com.example.david.helloworld.retrofit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.david.helloworld.activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

//    private static final String API_BASE_URL = "https://3a9d54b8.ngrok.io/api/";
//
//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    private static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(API_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create()).build();
//
//    private Interceptor interceptor = new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//            Request newRequest = chain.request().newBuilder().addHeader("User-Agent", "Retrofit-Sample-App").build();
//            return chain.proceed(newRequest);
//        }
//    };
//
//    private OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build();
//
//    public static <S> S createService(Class<S> serviceClass) {
////        Retrofit retrofit = builder.client(httpClient.build()).build();
////        return retrofit.create(serviceClass);
//        return ServiceGenerator.retrofit.create(serviceClass);
//
//    }
//
//    public static <S> S createServiceAuthorization(Class<S> serviceClass, final String authToken) {
//        if (authToken != null) {
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request original = chain.request();
//
//                            // Request customization: add request headers
//                            Request.Builder requestBuilder = original.newBuilder()
//                                    .header("Content-Type", "application/json")
//                                    .header("Authorization", authToken)
//                                    .method(original.method(), original.body());
//
//                            Request request = requestBuilder.build();
//                            return chain.proceed(request);
//                        }
//                    }).build();
//        }
//
//        OkHttpClient client = httpClient.build();
//        Retrofit retrofit = builder.client(client).build();
//        return retrofit.create(serviceClass);
//    }

    public static final String API_BASE_URL = "http://f22fdf65.ngrok.io/api/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceAuthorization(Class<S> serviceClass, final String authToken) {
        if (authToken != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Authorization", authToken)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static void handleError(retrofit2.Response response, Activity activityClass) {
        if (response.errorBody() != null) {
            try {
                JSONObject errorObject = new JSONObject(response.errorBody().string());
                String message = errorObject.getString("message");
                Toast.makeText(activityClass, message, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activityClass, response.message(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activityClass, response.message(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activityClass, response.message(), Toast.LENGTH_LONG).show();
        }
    }
}
