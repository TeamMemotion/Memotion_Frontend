package com.example.memotion.mypage.patch.profile;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.Part;

public interface ProfilePatchRetrofitInterface {
    @PATCH("member/profile")
    Call<ProfilePatchResponse> patchSearch(@Body ProfilePatchRequest profilePatchRequest, @Part MultipartBody.Part multipartFile);
}
