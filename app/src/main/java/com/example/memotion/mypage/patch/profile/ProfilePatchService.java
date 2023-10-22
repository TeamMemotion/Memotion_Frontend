package com.example.memotion.mypage.patch.profile;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePatchService {

    private ProfilePatchResult profilePatchResult;

    public void setProfilePatchResult(ProfilePatchResult profilePatchResult) {
        this.profilePatchResult = profilePatchResult;
    }

    public void patchProfile(ProfilePatchRequest profilePatchRequest, MultipartBody.Part multipartFile) {
        ProfilePatchRetrofitInterface patchProfileService = getClient().create(ProfilePatchRetrofitInterface.class);

        patchProfileService.patchSearch(profilePatchRequest, multipartFile).enqueue(new Callback<ProfilePatchResponse>() {
            @Override
            public void onResponse(Call<ProfilePatchResponse> call, Response<ProfilePatchResponse> response) {
                Log.d("PATCH-PROFILE-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        profilePatchResult.patchProfileSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage());

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2002:
                            case 4002:
                                profilePatchResult.patchProfileFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePatchResponse> call, Throwable t) {
                Log.d("PATCH-PROFILE-FAILURE", t.getMessage());
            }
        });
    }
}
