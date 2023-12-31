package com.example.memotion;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.memotion.account.token.TokenResponse;
import com.example.memotion.account.token.TokenResult;
import com.example.memotion.account.token.TokenService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String TAG = "RetrofitClient";
    private static String BASE_URL = "http://43.200.86.191:9000/";
    private static Retrofit retrofit;
    private static Context context = MainActivity.context;

    // SharedPreference에서 accessToken 추출
    public static String getAccessToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        Log.d(TAG, "액세스 : " + sharedPreferences.getString("accessToken", ""));
        return sharedPreferences.getString("accessToken", "");
    }

    // SharedPreference에서 accessToken 추출
    public static String getRefreshToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sharedPreferences.getString("refreshToken", "");
    }

    // getClient() 메서드 내에서 호출할 수 있도록 (기존 방식은 호출하는 메소드가 public static이라 setTokenService에 context 주입 불가)
    private static class TokenServiceHandler implements TokenResult {
        // 토큰 재발급 성공 -> 재발급 받은 토큰으로 SharedPreference에 저장된 값 수정
        @Override
        public void regenerateTokenSuccess(int code, TokenResponse.Result result) {
            Log.d(TAG, "토큰 재발급 성공");
            Log.d(TAG, "재발급 받은 accessToken: " + result.getAccessToken());
            Log.d(TAG, "재발급 받은 refreshToken: " + result.getRefreshToken());

            SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("accessToken", result.getAccessToken());
            editor.putString("refreshToken", result.getRefreshToken());
            editor.apply();
        }

        @Override
        public void regenerateTokenFailure(int code, String message) {
            Log.d(TAG, "토큰 재발급 실패");
        }
    }
    
    // header의 Bearer 토큰을 Interceptor로 처리한 Retrofit을 반환
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // header 없이 보낼 url
                    // 로그아웃은 interceptor 사용하지 말고 직접 refreshToken 넣어주기
                    if (original.url().encodedPath().equalsIgnoreCase("member/signup")
                            || original.url().encodedPath().equalsIgnoreCase("member/login")
                            || original.url().encodedPath().equalsIgnoreCase("member/logout")
                            || original.url().encodedPath().equalsIgnoreCase("member/regenerate-token")) {
                        return chain.proceed(original);

                    } else {
                        Request newRequest = original.newBuilder()
                                .header("Authorization", "Bearer " + getAccessToken())
                                .build();
                        Response response = chain.proceed(newRequest);

                        // 토큰 관련 에러 처리
                        if (response.code() == 401) {
                            String responseBody = response.body().string();
                            try {
                                JSONObject errorJson = new JSONObject(responseBody);
                                String errorMessage = errorJson.optString("message");

                                if(errorMessage.equals("잘못된 요청입니다."))
                                    Log.d(TAG, "Bearer 토큰 없이 요청을 보내고 있습니다.");
                                else if(errorMessage.equals("유효하지 않거나 만료된 토큰입니다.")) {
                                    Log.d(TAG, "Bearer 토큰이 만료되었습니다. 재발급이 필요합니다.");

                                    // refreshToken -> accessToken 재발급 + SharedPreference 갱신
                                    regenerateToken(chain);

                                    Thread.sleep(2000);
                                    newRequest = chain.request().newBuilder()
                                            .header("Authorization", "Bearer " + getAccessToken())
                                            .build();
                                    Log.d(TAG, "New Request: " + newRequest.toString());
                                    response = chain.proceed(newRequest);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return response;
                    }
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    private static void regenerateToken(Interceptor.Chain chain) {
        TokenServiceHandler handler = new TokenServiceHandler();
        TokenService tokenService = new TokenService();
        tokenService.setTokenResult(handler);
        tokenService.refreshToken();
    }

    public static class ErrorResponse {
        int code;
        String message;

        static RetrofitClient.ErrorResponse of(int code, String message) {
            RetrofitClient.ErrorResponse response = new RetrofitClient.ErrorResponse();
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

    public static RetrofitClient.ErrorResponse errorParsing(String errorBodyString) {
        // JSON 문자열을 JsonObject로 파싱
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(errorBodyString).getAsJsonObject();

        String errorMessage = jsonObject.get("message") != null && !jsonObject.get("message").isJsonNull() ? jsonObject.get("message").getAsString() : null;
        int errorCode = jsonObject.get("code") !=null && !jsonObject.get("code").isJsonNull() ? jsonObject.get("code").getAsInt() : -1;

        return RetrofitClient.ErrorResponse.of(errorCode, errorMessage);
    }
}
