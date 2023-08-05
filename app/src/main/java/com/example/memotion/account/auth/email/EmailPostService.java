package com.example.memotion.account.auth.email;

import static com.example.memotion.NetworkModule.getRetrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailPostService {
    private String result;
    private EmailPostResult emailPostResult;

    public void setEmailPostResult(EmailPostResult emailPostResult) {
        this.emailPostResult = emailPostResult;
    }

    public void emailAuthentication(EmailPostRequest emailPostRequest) {
        EmailPostRetrofitInterface emailService = getRetrofit().create(EmailPostRetrofitInterface.class);
        emailService.emailAuthentication(emailPostRequest).enqueue(new Callback<EmailPostResponse>() {
            @Override
            public void onResponse(Call<EmailPostResponse> call, Response<EmailPostResponse> response) {
                Log.d("EMAIL-AUTHEN-SUCCESS", response.toString());
                EmailPostResponse resp = response.body();
                Log.d("response_body_email:", response.body().getMessage());
                Log.d("response_body_email:", String.valueOf(response.body().getCode()));
                Log.d("===============", "================");
                Log.d("email:", String.valueOf(response.isSuccessful()));
                Log.d("email:", String.valueOf(response.code()));
                Log.d("email:", response.message());


                if(Integer.valueOf(resp.getCode()) != null) {
                    switch (resp.getCode()) {
                        case 1000:
                            emailPostResult.emailSuccess(resp.getCode(), resp.getResult());
                            break;
                        default:
                            emailPostResult.emailFailure(resp.getCode(), resp.getMessage());
                            break;
                    }
                } else {
                    Log.d("onfailure code: ", "응답 실패");
                }
            }

            @Override
            public void onFailure(Call<EmailPostResponse> call, Throwable t) {
                Log.d("EMAIL-AUTHEN-FAILURE", t.getMessage());
            }
        });
    }
}