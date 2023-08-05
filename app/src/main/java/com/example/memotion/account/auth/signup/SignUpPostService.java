package com.example.memotion.account.auth.signup;

import static com.example.memotion.NetworkModule.getRetrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPostService {
    private SignUpPostResult signUpPostResult;

    public void setSignUpPostResult(SignUpPostResult signUpPostResult) {
        this.signUpPostResult = signUpPostResult;
    }

    public void signUp(SignUpPostRequest signUpPostRequest) {
        SignUpPostRetrofitInterface signUpService = getRetrofit().create(SignUpPostRetrofitInterface.class);
        signUpService.signUp(signUpPostRequest).enqueue(new Callback<SignUpPostResponse>() {
            @Override
            public void onResponse(Call<SignUpPostResponse> call, Response<SignUpPostResponse> response) {
                Log.d("SIGNUP-SUCCESS", response.toString());
                SignUpPostResponse resp = response.body();

                if (Integer.valueOf(resp.getCode()) != null) {
                    switch (resp.getCode()) {
                        case 1000:
                            signUpPostResult.signUpSuccess(resp.getCode(), resp.getResult());
                            break;
                        default:
                            signUpPostResult.signUpFailure(resp.getCode(), resp.getMessage());
                            break;
                    }
                } else {
                    Log.d("onfailure code: ", "응답 실패");
                }
            }

            @Override
            public void onFailure(Call<SignUpPostResponse> call, Throwable t) {
                Log.d("SIGNUP-FAILURE", t.getMessage());
            }
        });
    }
}
