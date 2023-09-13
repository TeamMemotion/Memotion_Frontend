package com.example.memotion;

import android.app.Application;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule extends Application {
    private static String BASE_URL = "http://43.200.86.191:9000/";

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static class ErrorResponse {
        int code;
        String message;

        static ErrorResponse of(int code, String message) {
            ErrorResponse response = new ErrorResponse();
            response.code = code;
            response.message = message;
            return response;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static ErrorResponse errorParsing(String errorBodyString) {
        // JSON 문자열을 JsonObject로 파싱
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(errorBodyString).getAsJsonObject();

        // JsonObject에서 "code" 속성 값을 가져옴
        int errorCode = jsonObject.get("code").getAsInt();
        String errorMessage = jsonObject.get("message").getAsString();

        return ErrorResponse.of(errorCode, errorMessage);
    }
}
