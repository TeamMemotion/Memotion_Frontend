package com.example.memotion.route.post.routedetail;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostRouteDetailRetrofitInterface {
    @Multipart
    @POST("route/save")
    Call<PostRouteDetailResponse> postRouteDetail(@Part MultipartBody.Part multipartFile, @Part("request") PostRouteDetailRequest request);
}
